package org.juitar.workerq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public class WorkerQueueServiceRegistryImpl implements WorkerQueueServiceRegistry {

    private Map<String, WorkerQueueService> services = new ConcurrentHashMap<>();

    @Override
    public WorkerQueueService getWorkerQueueService(WorkQueue queue) {
        return services.get(queue.getId());
    }

    @Override
    public WorkerQueueService registerQueueService(WorkQueue queue, WorkerFactory workerFactory) {
        WorkerQueueService workerQueueService = getWorkerQueueService(queue);
        if (workerQueueService != null) {
            throw new IllegalArgumentException("A service is already registered with worker queue " + queue.toString());
        }

        workerQueueService = new WorkerQueueServiceImpl(queue, workerFactory);
        services.put(queue.getId(), workerQueueService);

        return workerQueueService;
    }

}
