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
public class UpdateWorker implements Worker {

    private final WorkAuthorizer authorizer;
    private final WorkValidator validator;
    private final WorkPreProcessor preProcessor;
    private final TxWorker txManager;

    public UpdateWorker(TxWorker txManager, WorkAuthorizer authorizer, WorkValidator validator, WorkPreProcessor preProcessor) {
        this.txManager = txManager;
        this.authorizer = authorizer;
        this.validator = validator;
        this.preProcessor = preProcessor;
    }

    @Monitored(threshold = 10, category = MonitoredCategory.DAL, operation = MonitoredOperation.DATABASE_ACCESS)
    @Override
    public void doWork(Work work) {
        authorizer.authorize(work);
        validator.validate(work);

        // Pass to transaction manager to handle the physical transaction
        txManager.addTxWork(preProcessor.process(work));
    }

}
