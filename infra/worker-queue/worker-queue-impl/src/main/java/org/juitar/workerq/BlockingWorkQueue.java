package org.juitar.workerq;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class BlockingWorkQueue implements WorkQueue {

    private static class DefaultBlockingQueueAdapter implements BlockingQueueAdapter {

        private BlockingQueue<Work> delegate;

        @Override
        public void setDelegate(BlockingQueue<Work> delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean add(Work work) {
            return delegate.add(work);
        }

        @Override
        public Work take() throws InterruptedException {
            return delegate.take();
        }
    }

    private static final DefaultBlockingQueueAdapter DEFAULT_BLOCKING_QUEUE_ADAPTER = new DefaultBlockingQueueAdapter();

    private final BlockingQueueAdapter queueAdapter;
    private final String id;

    public BlockingWorkQueue() {
        this(new LinkedBlockingQueue<Work>());
    }

    public BlockingWorkQueue(BlockingQueue<Work> blockingQueue) {
        this(UUID.randomUUID().toString(), blockingQueue, DEFAULT_BLOCKING_QUEUE_ADAPTER);
    }

    public BlockingWorkQueue(BlockingQueue<Work> blockingQueue, BlockingQueueAdapter queueAdapter) {
        this(UUID.randomUUID().toString(), blockingQueue, queueAdapter);
    }

    protected BlockingWorkQueue(String id, BlockingQueue<Work> blockingQueue, BlockingQueueAdapter queueAdapter) {
        if (id == null) {
            throw new IllegalArgumentException("queue id cannot be null");
        }
        if (blockingQueue == null) {
            throw new IllegalArgumentException("blockingQueue cannot be null");
        }
        if (queueAdapter == null) {
            throw new IllegalArgumentException("queueAdapter cannot be null");
        }
        this.id = id;
        this.queueAdapter = queueAdapter;
        this.queueAdapter.setDelegate(blockingQueue);
    }


    @Override
    public final String getId() {
        return id;
    }

    @Override
    public final boolean add(Work work) {
        return queueAdapter.add(work);
    }

    @Override
    public final Work take() throws InterruptedException {
        return queueAdapter.take();
    }

    @Override
    public final String toString() {
        return "BlockingWorkQueue{" +
                "queue=" + queueAdapter +
                ", id='" + id + '\'' +
                '}';
    }

}
