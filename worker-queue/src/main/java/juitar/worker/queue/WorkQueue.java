package juitar.worker.queue;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class WorkQueue {

    private final BlockingQueue<Work> queue = new LinkedBlockingQueue<>();
    private final String id;

    public WorkQueue() {
        this.id = UUID.randomUUID().toString();
    }

    public final String getId() {
        return id;
    }

    public final void submit(Work work) {
        queue.add(work);
    }

    public final Work take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public final String toString() {
        return "WorkQueue{" +
                "queue=" + queue +
                ", id='" + id + '\'' +
                '}';
    }

}
