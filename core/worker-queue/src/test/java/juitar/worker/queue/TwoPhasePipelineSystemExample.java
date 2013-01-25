package juitar.worker.queue;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class TwoPhasePipelineSystemExample {

    public static void main(String... args) throws InterruptedException {

        final TwoPhasePipelineSystemExample twoPhasePipelineSystemExample = new TwoPhasePipelineSystemExample();

        twoPhasePipelineSystemExample.start();


        for (int i = 0; i < 2; i++) {
            twoPhasePipelineSystemExample.phase1Queue.submit(new Work(UUID.randomUUID().toString(), "payload", new ResultChannel() {
                @Override
                public void onSuccess(Result result) {
                    twoPhasePipelineSystemExample.phase2Queue.submit(new Work(UUID.randomUUID().toString(), "payload", new ResultChannelImpl()));
                }

                @Override
                public void onFailure(Result result, Exception e) {
                    throw new RuntimeException(e);
                }
            }));
        }

        Thread.sleep(1000);

        twoPhasePipelineSystemExample.stop();
    }

    private WorkerQueueServiceRegistryImpl serviceRegistry = new WorkerQueueServiceRegistryImpl();
    private final WorkQueue phase1Queue = new WorkQueueImpl();
    private final WorkQueue phase2Queue = new WorkQueueImpl();

    public void start() {
        WorkerQueueService phase1QueueService = serviceRegistry.registerQueueService(phase1Queue, new WorkerFactoryImpl());
        phase1QueueService.start(1);

        WorkerQueueService phase2QueueService = serviceRegistry.registerQueueService(phase2Queue, new WorkerFactoryImpl());
        phase2QueueService.start(1);
    }

    public void stop() {
        stop(phase1Queue, phase2Queue);
    }

    private void stop(WorkQueue... queues) {
        for (WorkQueue queue : queues) {
            serviceRegistry.getWorkerQueueService(queue).stop(3, TimeUnit.SECONDS);
        }
    }
}
