package juitar.worker.jdbc;

import org.juitar.workerq.Work;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sha1n
 * Date: 2/23/13
 */
public class TxWorkerManager {

    private final Queue<Work> txQueue = new ConcurrentLinkedQueue<>();
    private final AtomicInteger nextManagerIndex = new AtomicInteger(0);

    private ScheduledExecutorService commitScheduler;
    private DataSource dataSource;
    private int commitInterval = 100;
    private int workers = 1;
    private TxWorkerImpl[] txWorkers;


    @PostConstruct
    public void init() {
        this.commitScheduler = Executors.newScheduledThreadPool(workers);
        this.txWorkers = new TxWorkerImpl[workers];
        for (int i = 0; i < workers; i++) {
            TxWorkerImpl worker = new TxWorkerImpl();
            worker.setDataSource(dataSource);
            worker.setCommitInterval(commitInterval);
            worker.setTxQueue(txQueue);
            worker.setIndex(i);

            txWorkers[i] = worker;
            commitScheduler.scheduleWithFixedDelay(worker, 1000, commitInterval, TimeUnit.MILLISECONDS);
        }
    }

    public final void setTxWorkers(int workers) {
        this.workers = workers;
    }

    @Required
    public final void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public final void setCommitInterval(int commitInterval) {
        this.commitInterval = commitInterval;
    }

    public TxWorker getTransactionWorker() {
        nextManagerIndex.compareAndSet(txWorkers.length, 0);
        return txWorkers[nextManagerIndex.getAndIncrement()];

    }
}
