package juitar.web.rest.resource;

import juitar.monitoring.api.Monitored;
import juitar.worker.queue.*;
import junitar.server.netty.jersey.AsyncWorkerResponse;
import junitar.server.netty.jersey.AsyncWorkerResponseBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

/**
 * @author sha1n
 * Date: 1/22/13
 */
@Path("/async")
public class AsyncWorkerResource {

    private static final WorkQueue queue = new WorkQueue();
    private static final WorkerQueueServiceRegistryImpl workerQueueServiceReg = new WorkerQueueServiceRegistryImpl();

    static {
        workerQueueServiceReg.registerQueueService(queue, new WorkerFactory() {
            @Override
            public Worker createWorker() {
                return new Worker() {
                    @Override
                    public Result doWork(Work work) {
                        Result result = new Result(work.getId());
                        result.setResultData("Async generated...");
                        return result;
                    }
                };
            }
        });

        WorkerQueueService workerQueueService = workerQueueServiceReg.getWorkerQueueService(queue);
        workerQueueService.start(4);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Monitored(threshold = 3)
    public AsyncWorkerResponse get() {
        Work work = new Work(UUID.randomUUID().toString(), "Work Item", new ResultChannel() {
            @Override
            public void onSuccess(Result result) {
//                System.out.println("Result received: " + result.getResultData());
            }

            @Override
            public void onFailure(Result result, Exception e) {
                e.printStackTrace();
                System.out.println("Execution failed: " + result);
            }
        });

        AsyncWorkerResponseBuilder<String> asyncWorkerResponseBuilder = new AsyncWorkerResponseBuilder<>(queue, work);
        asyncWorkerResponseBuilder.entity("Entity");

        AsyncWorkerResponse asyncWorkerResponse = asyncWorkerResponseBuilder.build();


        return asyncWorkerResponse;
    }

}
