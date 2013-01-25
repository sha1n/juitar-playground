package juitar.worker.queue;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class WorkQueueImpl implements WorkQueue {

    private final BlockingQueue<Work> queue = new LinkedBlockingQueue<>();
    private final String id;

    public WorkQueueImpl() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public final String getId() {
        return id;
    }

    @Override
    public final void submit(Work work) {
        queue.add(work);
    }

    @Override
    public final Work take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public final String toString() {
        return "WorkQueueImpl{" +
                "queue=" + queue +
                ", id='" + id + '\'' +
                '}';
    }

}
