package juitar.worker.jdbc;

import org.juitar.workerq.Worker;
import org.juitar.workerq.WorkerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public class JdbcBatchWorkerFactory implements WorkerFactory {

    private TransactionManager txManager;

    @Required
    public final void setTxManager(TransactionManager txManager) {
        this.txManager = txManager;
    }

    @Override
    public final Worker createWorker() {
        return new JdbcBatchWorker(txManager);
    }
}
