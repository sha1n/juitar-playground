package juitar.worker.queue;

/**
 * @author sha1n
 * Date: 1/25/13
 */
public interface WorkQueue {
    String getId();

    void submit(Work work);

    Work take() throws InterruptedException;
}
