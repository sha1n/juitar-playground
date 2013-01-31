package juitar.worker.jdbc;

import juitar.monitoring.spi.config.MonitoredCategory;
import juitar.monitoring.spi.config.MonitoredOperation;
import juitar.worker.queue.Result;
import juitar.worker.queue.Work;
import juitar.worker.queue.Worker;
import org.juitar.monitoring.api.Monitored;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public class JdbcBatchWorker implements Worker {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcBatchWorker.class);
    private final ScheduledExecutorService commitScheduler = Executors.newScheduledThreadPool(1);
    private final CommitTask commitTask = new CommitTask();


    public JdbcBatchWorker(DataSource jdbcTemplate) {
        this.commitTask.setDataSource(jdbcTemplate);
        this.commitScheduler.scheduleWithFixedDelay(commitTask, 1000, 500, TimeUnit.MILLISECONDS);
    }

    @Monitored(threshold = 10, category = MonitoredCategory.DAL, operation = MonitoredOperation.DATABASE_ACCESS)
    @Override
    public void doWork(Work work) {
        commitTask.commitQueue.add(work);
    }


    private static final class CommitTask implements Runnable {

        final Queue<Work> commitQueue = new ConcurrentLinkedDeque<>();
        private DataSource dataSource;
        private Connection connection;

        public final void setDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            obtainConnection();
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

        @Override
        public void run() {
            obtainConnection();
            Statement s;
            try {
                s = connection.createStatement();
                processQueue(s);
            } catch (SQLException e) {
                LOGGER.error("Failed to create statement", e);
            }


        }

        private void processQueue(Statement s) {
            while (!commitQueue.isEmpty()) {
                Work work = null;
                try {
                    work = commitQueue.poll();
                    addBatch(s, work);
                } catch (Exception e) {
                    LOGGER.error("Worker caught an exception", e);
                    if (work != null) {
                        work.getResultChannel().onFailure(new Result(work.getId()), e);
                    }
                } catch (Throwable e) {
                    LOGGER.error("SEVERE: Worker caught non-java.lang.Exception type.", e);
                } finally {
                    try {
                        connection.commit(); // TODO should implement a robust retry and discrete result status population.
                    } catch (SQLException e) {
                        LOGGER.error("Commit failed.");
                    }
                }

            }
        }

        /**
         * This is a stupid implementation since the results are useless and one failure fails all with no tracking whatsoever...
         */
        private void addBatch(Statement s, Work work) throws SQLException {
            if (work != null) {
                String[] payload = (String[]) work.getPayload();

                for (String sql : payload) {
                    s.addBatch(sql);
                }

                Result result = new Result(work.getId());
                result.setResultData("batch added");
                work.getResultChannel().onSuccess(result);
            }
        }
    }
}
