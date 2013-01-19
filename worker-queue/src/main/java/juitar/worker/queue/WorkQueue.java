package juitar.worker.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class WorkQueue {

    private final BlockingQueue<Work> queue = new LinkedBlockingQueue<>();

    public final void submit(Work work) {
        queue.add(work);
    }

    public final Work take() throws InterruptedException {
        return queue.take();
    }
}
