package juitar.worker.queue;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class TestDrive {

    public static void main(String... args) throws InterruptedException {

        WorkQueue queue = new WorkQueue();
        WorkerService service = new WorkerService(queue);
        service.start(2);

        for (int i = 0; i < 100; i++) {
            queue.submit(new Work(UUID.randomUUID().toString(), "payload", new ResultChannelImpl()));
        }

        service.stop(3, TimeUnit.SECONDS);
    }
}
