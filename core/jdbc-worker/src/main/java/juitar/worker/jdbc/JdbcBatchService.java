package juitar.worker.jdbc;

import juitar.monitoring.spi.config.MonitoredCategory;
import juitar.monitoring.spi.config.MonitoredOperation;
import org.juitar.monitoring.api.Monitored;
import org.juitar.workerq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public class JdbcBatchService {

    private final WorkQueue queue;

    @Autowired
    private WorkerQueueServiceRegistry workerServiceRegistry;

    @Autowired
    @Qualifier("batchWorker")
    private WorkerFactory workerFactory;

    private WorkerQueueService workerQueueService;
    private int workers = Runtime.getRuntime().availableProcessors();

    @Autowired
    public JdbcBatchService(WorkQueue queue) {
        this.queue = queue;
    }

    @PostConstruct
    public final void init() {
        workerQueueService = workerServiceRegistry.registerQueueService(queue, workerFactory);
        workerQueueService.start(workers);
    }

    public final void setWorkers(int workers) {
        this.workers = workers;
    }

    @Monitored(threshold = 10, category = MonitoredCategory.DAL, operation = MonitoredOperation.COMPUTATION)
    public final void executeUpdate(String updateStatement, CompletionCallback completionCallback) {
        queue.add(new Work(queue.getId(), new String[]{updateStatement}, completionCallback));
    }

}
