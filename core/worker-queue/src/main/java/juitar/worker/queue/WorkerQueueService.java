package juitar.worker.queue;

import java.util.concurrent.TimeUnit;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public interface WorkerQueueService {
    void start(int workers);

    void stop(long timeout, TimeUnit timeUnit);
}
