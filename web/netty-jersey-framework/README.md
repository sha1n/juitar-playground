netty-jersey-framework
======================

This module provides basic Jersey integration on top of Netty and experimental support for asynchronous Response implementation,
for use with an asynchronous worker queue design.

Sample @GET method using the AsyncWorkerResponse
------------------------------------------------
The entity itself is still not retrieved from the worker... it's written by the resource method out of the worker context.

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public AsyncWorkerResponse get() {

        Work work = new Work(UUID.randomUUID().toString(), "Work Item", new ResultChannel() {
            @Override
            public void onSuccess(Result result) {
                System.out.println("Result received: " + result.getResultData());
            }

            @Override
            public void onFailure(Result result, Exception e) {
                e.printStackTrace();
                System.out.println("Execution failed: " + result);
            }
        });

        AsyncWorkerResponseBuilder<String> asyncWorkerResponseBuilder = new AsyncWorkerResponseBuilder<>(QUEUE, work);
        asyncWorkerResponseBuilder.entity("Entity"); // Optional entity to write to the response

        AsyncWorkerResponse asyncWorkerResponse = asyncWorkerResponseBuilder.build();


        return asyncWorkerResponse;
    }
