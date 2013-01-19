package juitar.worker.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class WorkerQueueService {

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

    private ExecutorService executorService;
    private final WorkQueue queue;
    private AtomicBoolean started;

    public WorkerQueueService(WorkQueue queue) {
        this.queue = queue;
    }

    public void start(final int workers) {
        executorService = Executors.newFixedThreadPool(workers);
        started = new AtomicBoolean(true);
        for (int i = 0; i < workers; i++) {
            executorService.execute(new WorkerImpl(queue, started));
        }
    }

    public void stop(long timeout, TimeUnit timeUnit) throws InterruptedException {
        started.set(false);
        if (executorService != null) {
            executorService.shutdown();
            executorService.awaitTermination(timeout, timeUnit);
            if (!executorService.isTerminated()) {
                executorService.shutdownNow();
            }
        }
    }
}
