package juitar.worker.queue;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class WorkerImpl implements Worker {

    private final WorkQueue queue;
    private final AtomicBoolean serviceStarted;

    public WorkerImpl(final WorkQueue queue, final AtomicBoolean serviceStarted) {
        this.queue = queue;
        this.serviceStarted = serviceStarted;
    }

    @Override
    public void run() {
        while (serviceStarted.get()) {
            try {
                Work work = queue.take();

                Result result = new Result(work.getId());
                result.setResultData("data-" + work.getId());

                work.getResultChannel().send(result);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
