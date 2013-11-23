package org.juitar.workerq;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public interface WorkerQueueServiceRegistry {
    WorkerQueueService getWorkerQueueService(WorkQueue queue);

    WorkerQueueService registerQueueService(WorkQueue queue, WorkerFactory workerFactory);
}
