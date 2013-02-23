package juitar.worker.jdbc;

import org.juitar.workerq.Worker;
import org.juitar.workerq.WorkerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public class UpdateWorkerFactory implements WorkerFactory {

    private WorkAuthorizer authorizer;
    private WorkValidator validator;
    private WorkPreProcessor preProcessor;
    private TxWorkerManager txWorkerManager;

    @Required
    public final void setTxWorkerManager(TxWorkerManager txWorkerManager) {
        this.txWorkerManager = txWorkerManager;
    }

    @Required
    public final void setAuthorizer(WorkAuthorizer authorizer) {
        this.authorizer = authorizer;
    }

    @Required
    public final void setValidator(WorkValidator validator) {
        this.validator = validator;
    }

    @Required
    public final void setPreProcessor(WorkPreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }


    @Override
    public final Worker createWorker() {
        return new UpdateWorker(txWorkerManager.getTransactionWorker(), authorizer, validator, preProcessor);
    }
}
