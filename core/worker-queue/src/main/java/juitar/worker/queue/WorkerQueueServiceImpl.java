package juitar.worker.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author sha1n
 * Date: 1/19/13
 */
class WorkerQueueServiceImpl implements WorkerQueueService {

    private static class WorkerThreadFactory implements ThreadFactory {

        private ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = defaultThreadFactory.newThread(r);
            thread.setName("Worker");
            thread.setDaemon(true);

            return thread;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerQueueServiceImpl.class);

    private ExecutorService executorService;
    private final WorkerFactory workerFactory;
    private final WorkQueue queue;
    private AtomicBoolean started;

    public WorkerQueueServiceImpl(WorkQueue queue, WorkerFactory workerFactory) {
        this.queue = queue;
        this.workerFactory = workerFactory;
    }

    @Override
    public final void start(final int workers) {
        executorService = Executors.newFixedThreadPool(workers, new WorkerThreadFactory());
        started = new AtomicBoolean(true);
        for (int i = 0; i < workers; i++) {
            executorService.execute(new ServiceBoundWorker(workerFactory.createWorker(), queue, started));
        }
    }

    @Override
    public final void stop(long timeout, TimeUnit timeUnit) {
        started.set(false);
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
    }
}
