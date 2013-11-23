package org.juitar.workerq;

/**
 * @author sha1n
 * Date: 1/25/13
 */
public interface WorkQueue {

    /**
     * Returns the unique ID of this queue.
     *
     * @return the id of this queue.
     */
    String getId();

    /**
     * Adds work to this queue.
     *
     * @param work the {@link Work} to add to the queue.
     * @return <tt>true</tt> as specified by {@link java.util.Collection#add})
     */
    boolean add(Work work);

    /**
     * Retrieves and removed the head of this queue.
     *
     * @return the next {@link Work} to be processed.
     * @throws InterruptedException if this queue is blocking and the taking threads is interrupted while
     * waiting for work to become available.
     */
    Work take() throws InterruptedException;
}
