Worker Queue
============
Conceptual design of a WorkerQueueService with a configurable number worker threads.

Code sample
-----------


    // Create a work queue
    WorkQueue queue = new WorkQueue();

    // Create a worker queue service
    WorkerQueueService queueService = new WorkerQueueService(queue, new WorkerFactoryImpl());
    // Start the service
    queueService.start(2);

    // Submit some work
    queue.submit(new Work(UUID.randomUUID().toString(), "payload1", new ResultChannelImpl()));
    queue.submit(new Work(UUID.randomUUID().toString(), "payload2", new ResultChannelImpl()));
    queue.submit(new Work(UUID.randomUUID().toString(), "payload3", new ResultChannelImpl()));

    // Stop the service
    queueService.stop(3, TimeUnit.SECONDS);
