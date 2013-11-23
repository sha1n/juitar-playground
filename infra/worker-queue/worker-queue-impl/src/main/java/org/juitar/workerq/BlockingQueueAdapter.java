package org.juitar.workerq;

import java.util.concurrent.BlockingQueue;

/**
 * This is an adapter interface to be used with {@link BlockingWorkQueue} to change the default interaction with the
 * underlying {@link BlockingQueue}. The default behavior is to call {@link BlockingQueue#add} and
 * {@link java.util.concurrent.BlockingQueue#take}, however one might decide to use a more sophisticated implementation
 * over a {@link java.util.concurrent.LinkedTransferQueue} and call {@link java.util.concurrent.LinkedTransferQueue#transfer}
 * or make dynamic decisions based on an external state.
 *
 * @author sha1n
 * Date: 2/9/13
 */
public interface BlockingQueueAdapter {

    /**
     * Callback method to set the underlying {@link BlockingQueue}. It is guaranteed that the blocking queue instance
     * have the same semantics as the one passed to the hosting {@link BlockingWorkQueue}.
     *
     * @param delegate a {@link BlockingQueue} instance.
     */
    void setDelegate(BlockingQueue<Work> delegate);

    /**
     * @see BlockingWorkQueue#add(Work)
     */
    boolean add(Work work);

    /**
     * @see org.juitar.workerq.BlockingWorkQueue#take()
     */
    Work take() throws InterruptedException;

}
