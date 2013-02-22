package juitar.worker.jdbc;

import org.juitar.monitoring.api.Monitored;
import org.juitar.workerq.CompletionStatus;
import org.juitar.workerq.Result;
import org.juitar.workerq.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author sha1n
 * Date: 2/22/13
 */
class DatabaseTransactionManager implements Runnable, TransactionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseTransactionManager.class);

    private final ScheduledExecutorService commitScheduler = Executors.newScheduledThreadPool(1);
    private final Queue<Work> txQueue = new ConcurrentLinkedDeque<>();

    private DataSource dataSource;
    private Connection connection;
    private int commitInterval = 100;

    @PostConstruct
    public final void start() {
        commitScheduler.scheduleWithFixedDelay(this, 1000, commitInterval, TimeUnit.MILLISECONDS);
        obtainConnection();
    }

    @Required
    public final void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public final void setCommitInterval(int commitInterval) {
        this.commitInterval = commitInterval;
    }


    @Override
    public final void run() {
        obtainConnection();
        processQueue();
    }

    @Override
    public final void addTxWork(Work txWork) {
        txQueue.add(txWork);
    }


    private void obtainConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                this.connection = dataSource.getConnection();
                this.connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to open database connection. Will retry next schedule.", e);
        }
    }


    @Monitored(threshold = 1000)
    private void processQueue() {
        long txStart = System.currentTimeMillis();
        AccumulativeTransaction tx = new AccumulativeTransaction(connection);
        LOGGER.debug("Database Transaction started.");
        try (Statement s = connection.createStatement();) {
            while (!txQueue.isEmpty()) {
                Work work = null;
                try {
                    work = txQueue.poll();
                    tx.addBatch(s, work);
                } catch (Exception e) {
                    LOGGER.error("Worker caught an exception", e);
                    if (work != null) {
                        work.getCompletionCallback().onFailure(new Result(work.getId()), e, CompletionStatus.ERROR);
                    }
                } catch (Throwable e) {
                    LOGGER.error("SEVERE: Worker caught non-java.lang.Exception type.", e);
                }

                if (System.currentTimeMillis() - txStart > (commitInterval)) {
                    LOGGER.info("Going to commit with non-empty queue.");
                    break;
                }

            }
        } catch (SQLException e) {
            LOGGER.error("Failed to process database operation.", e);
        } finally {
            LOGGER.debug("Database Transaction going to commit.");
            tx.commit(); // TODO should implement a robust retry and discrete result status population.
        }
    }

    private static class AccumulativeTransaction {
        private Connection connection;
        private List<Work> workList = new ArrayList<>();

        private AccumulativeTransaction(Connection connection) {
            this.connection = connection;
        }


        /**
         * This is a stupid implementation since the results are useless and one failure fails all with no tracking whatsoever...
         */
        public boolean addBatch(Statement s, Work work) throws SQLException {
            boolean success = false;
            if (work != null) {
                String[] payload = (String[]) work.getPayload();

                for (String sql : payload) {
                    s.addBatch(sql);
                }

                workList.add(work);

                success = true;
            }
            return success;
        }


        public void commit() {
            try {
                if (!workList.isEmpty()) {
                    connection.commit(); // TODO should implement a robust retry and discrete result status population.
                    reportTxSuccess();
                    LOGGER.info("Committed " + workList.size() + " logical transactions");
                }
            } catch (SQLException e) {
                reportTxFailure(e);
            }

        }

        private void reportTxSuccess() {
            for (Work work : workList) {
                Result result = new Result(work.getId());
                result.setResultData("Transaction Committed Successfully!");
                work.getCompletionCallback().onSuccess(result); // TODO not safe
            }
        }

        private void reportTxFailure(Exception exception) {
            LOGGER.error("Commit failed.");
            for (Work work : workList) {
                Result result = new Result(work.getId());
                result.setResultData("Transaction Failed!");
                work.getCompletionCallback().onFailure(result, exception, CompletionStatus.ERROR); // TODO not safe
            }
        }

    }

}
