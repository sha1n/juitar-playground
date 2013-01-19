package juitar.worker.queue;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class TwoPhaseQueueSystemExample {

    public static void main(String... args) throws InterruptedException {

        WorkQueue phase1Queue = new WorkQueue();
        WorkerQueueService phase1QueueService = new WorkerQueueService(phase1Queue, new WorkerFactoryImpl());
        phase1QueueService.start(1);

        final WorkQueue phase2Queue = new WorkQueue();
        WorkerQueueService phase2QueueService = new WorkerQueueService(phase2Queue, new WorkerFactoryImpl());
        phase2QueueService.start(1);


        for (int i = 0; i < 2; i++) {
            phase1Queue.submit(new Work(UUID.randomUUID().toString(), "payload", new ResultChannel() {
                @Override
                public void send(Result result) {
                    phase2Queue.submit(new Work(UUID.randomUUID().toString(), "payload", new ResultChannelImpl()));
                }
            }));
        }

        Thread.sleep(1000);

        phase1QueueService.stop(3, TimeUnit.SECONDS);
        phase2QueueService.stop(3, TimeUnit.SECONDS);
    }
}
