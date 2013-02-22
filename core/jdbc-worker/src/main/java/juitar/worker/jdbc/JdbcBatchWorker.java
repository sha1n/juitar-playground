package juitar.worker.jdbc;

import juitar.monitoring.spi.config.MonitoredCategory;
import juitar.monitoring.spi.config.MonitoredOperation;
import org.juitar.monitoring.api.Monitored;
import org.juitar.workerq.Work;
import org.juitar.workerq.Worker;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public class JdbcBatchWorker implements Worker {

    private TxWorker txManager;

    public JdbcBatchWorker(TxWorker txManager) {
        this.txManager = txManager;
    }

    @Monitored(threshold = 10, category = MonitoredCategory.DAL, operation = MonitoredOperation.DATABASE_ACCESS)
    @Override
    public void doWork(Work work) {
        // Run business logic here.

        // Pass to transaction manager to handle the physical transaction
        txManager.addTxWork(work);
    }

}
