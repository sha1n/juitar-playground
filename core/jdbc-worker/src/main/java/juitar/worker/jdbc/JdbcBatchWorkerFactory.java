package juitar.worker.jdbc;

import org.juitar.workerq.Worker;
import org.juitar.workerq.WorkerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public class JdbcBatchWorkerFactory implements WorkerFactory {

    private TxWorkerManager txWorkerManager;

    @Required
    public final void setTxWorkerManager(TxWorkerManager txWorkerManager) {
        this.txWorkerManager = txWorkerManager;
    }

    @Override
    public final Worker createWorker() {
        return new JdbcBatchWorker(txWorkerManager.getTransactionWorker());
    }
}
