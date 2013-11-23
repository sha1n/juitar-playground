package org.juitar.workerq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sha1n
 * Date: 1/19/13
 */
class WorkerQueueServiceImpl implements WorkerQueueService {

    private static class WorkerThreadFactory implements ThreadFactory {

        private final String id;
        private final AtomicInteger workerCounter = new AtomicInteger(0);
        private final ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();

        public WorkerThreadFactory(String id) {
            this.id = id == null ? "n/a" : id;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = defaultThreadFactory.newThread(r);
            thread.setName("{" + id + "}-QWorker-" + workerCounter.getAndIncrement());
            thread.setDaemon(true);

            return thread;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerQueueServiceImpl.class);

    private ExecutorService executorService;
    private final WorkerFactory workerFactory;
    private final WorkQueue queue;
    private final AtomicBoolean started = new AtomicBoolean(false);

    public WorkerQueueServiceImpl(final WorkQueue queue, final WorkerFactory workerFactory) {
        this.queue = queue;
        this.workerFactory = workerFactory;
    }

    @Override
    public final void start(final int workers) {
        if (started.get()) {
            throw new IllegalStateException("Service is already started.");
        }

        executorService = Executors.newFixedThreadPool(workers, new WorkerThreadFactory(queue.getId()));
        for (int i = 0; i < workers; i++) {
            executorService.execute(new ServiceBoundWorker(workerFactory.createWorker(), queue, started));
        }
        started.set(true);
    }

    @Override
    public final void stop(long timeout, TimeUnit timeUnit) {
        if (!started.get()) {
            throw new IllegalStateException("Service is not running.");
        }
        if (executorService != null) {
            executorService.shutdown();

            try {
                executorService.awaitTermination(timeout, timeUnit);
            } catch (InterruptedException e) {
                LOGGER.warn("Executor service shutdown timeout. Attempting brut force shutdown.", e);
            } finally {
                if (!executorService.isTerminated()) {
                    executorService.shutdownNow();
                }
            }
        }
        started.set(false);
    }
}
