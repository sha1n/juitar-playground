package org.juitar.workerq;

import org.easymock.EasyMock;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author sha1n
 * Date: 2/12/13
 */
public class WorkerQueueServiceImplTest {

    @Test
    public void testLifeCycle() {

        final int threadCount = 2;
        WorkQueue workQueueMock = EasyMock.createNiceMock(WorkQueue.class);
        WorkerFactory workerFactoryMock = EasyMock.createMock(WorkerFactory.class);
        Worker workerMock = EasyMock.createMock(Worker.class);
        EasyMock.expect(workerFactoryMock.createWorker()).andReturn(workerMock).times(threadCount);

        EasyMock.replay(workQueueMock, workerFactoryMock, workerMock);

        WorkerQueueServiceImpl workerQueueService = new WorkerQueueServiceImpl(workQueueMock, workerFactoryMock);

        workerQueueService.start(threadCount);
        workerQueueService.stop(1, TimeUnit.SECONDS);

        EasyMock.verify(workQueueMock, workerFactoryMock, workerMock);
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalLifeCycleStateStop() {

        final int threadCount = 2;
        WorkQueue workQueueMock = EasyMock.createNiceMock(WorkQueue.class);
        WorkerFactory workerFactoryMock = EasyMock.createMock(WorkerFactory.class);
        Worker workerMock = EasyMock.createMock(Worker.class);
        EasyMock.expect(workerFactoryMock.createWorker()).andReturn(workerMock).times(threadCount);

        EasyMock.replay(workQueueMock, workerFactoryMock, workerMock);

        WorkerQueueServiceImpl workerQueueService = new WorkerQueueServiceImpl(workQueueMock, workerFactoryMock);

        workerQueueService.stop(1, TimeUnit.SECONDS);
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalLifeCycleStateStart() {

        final int threadCount = 2;
        WorkQueue workQueueMock = EasyMock.createNiceMock(WorkQueue.class);
        WorkerFactory workerFactoryMock = EasyMock.createMock(WorkerFactory.class);
        Worker workerMock = EasyMock.createMock(Worker.class);
        EasyMock.expect(workerFactoryMock.createWorker()).andReturn(workerMock).times(threadCount);

        EasyMock.replay(workQueueMock, workerFactoryMock, workerMock);

        WorkerQueueServiceImpl workerQueueService = new WorkerQueueServiceImpl(workQueueMock, workerFactoryMock);

        try {
            workerQueueService.start(threadCount);

            // This should throw an exception
            workerQueueService.start(threadCount);
        } finally {
            workerQueueService.stop(1, TimeUnit.SECONDS);
        }
    }

}
