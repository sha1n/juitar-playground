package juitar.web.rest.resource;

import juitar.context.ContextAccess;
import juitar.monitoring.spi.config.MonitoredCategory;
import junitar.server.netty.jersey.AsyncWorkerResponse;
import junitar.server.netty.jersey.AsyncWorkerResponseBuilder;
import org.juitar.monitoring.api.Monitored;
import org.juitar.workerq.*;
import org.springframework.context.ApplicationContext;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

/**
 * @author sha1n
 * Date: 1/22/13
 */
@Path("/async")
public class AsyncWorkerResource {

    private static final WorkQueue QUEUE = new BlockingWorkQueue();
    private static final WorkerQueueServiceRegistryImpl workerQueueServiceReg = new WorkerQueueServiceRegistryImpl();

    static {
        workerQueueServiceReg.registerQueueService(QUEUE, new WorkerFactory() {
            @Override
            public Worker createWorker() {
                return new Worker() {
                    @Override
                    public void doWork(Work work) {
                        Result result = new Result(work.getId());
                        result.setResultData("Async generated...");
                        work.getCompletionCallback().onSuccess(result);
                    }
                };
            }
        });

        WorkerQueueService workerQueueService = workerQueueServiceReg.getWorkerQueueService(QUEUE);
        workerQueueService.start(4);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Monitored(threshold = 3, category = MonitoredCategory.REST)
    public String get() {
        return "test";
    }

    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/sql")
//    @Monitored(threshold = 3)
    public AsyncWorkerResponse submit(String sql) {

        ApplicationContext applicationContext = ContextAccess.getApplicationContext();
        WorkQueue jdbcBatchQueue = (WorkQueue) applicationContext.getBean("jdbcBatchQueue");

        Work work = new Work(UUID.randomUUID().toString(),
                new String[]{sql},
                new CompletionCallback() {
                    @Override
                    public void onSuccess(Result result) {
                        System.out.println("Result received: " + result.getResultData());
                    }

                    @Override
                    public void onFailure(Result result, Exception e, CompletionStatus status) {
                        e.printStackTrace();
                        System.out.println("Execution failed: " + result);
                    }
                });

        AsyncWorkerResponseBuilder<String> asyncWorkerResponseBuilder = new AsyncWorkerResponseBuilder<>(
                jdbcBatchQueue,
                work);

        asyncWorkerResponseBuilder.entity("Entity");

        return asyncWorkerResponseBuilder.build();
    }

}
