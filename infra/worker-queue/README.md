Worker Queue
============
Conceptual design of a WorkerQueueService with a configurable number worker threads.

Code sample
-----------


    // Create a work queue
    WorkQueue queue = new WorkQueue();

    // Create a worker queue service
    WorkerQueueService queueService = new WorkerQueueService(queue, new WorkerFactoryImpl());
    // Start the service with two worker threads
    queueService.start(2);

    // Submit some work
    queue.add(new Work(UUID.randomUUID().toString(), "payload1", new CompletionCallbackImpl()));
    queue.add(new Work(UUID.randomUUID().toString(), "payload2", new CompletionCallbackImpl()));
    queue.add(new Work(UUID.randomUUID().toString(), "payload3", new CompletionCallbackImpl()));

    // Stop the service
    queueService.stop(1, TimeUnit.SECONDS);
