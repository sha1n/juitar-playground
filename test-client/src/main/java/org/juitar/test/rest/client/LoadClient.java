package org.juitar.test.rest.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author sha1n
 * Date: 1/24/13
 */
public class LoadClient {

    public static void main(String... args) throws InterruptedException {

        final Args arguments = ArgsParseUtils.parse(args);
        final RequestTaskFactory taskFactory = new RequestTaskFactory();

        System.out.println("Running " + arguments.getThreads() + " threads each issues " + arguments.getRequests() + " requests");
        ExecutorService executorService = Executors.newFixedThreadPool(arguments.getThreads());
        CompletionService<Void> completionService = new ExecutorCompletionService<>(executorService);

        long startStamp = System.currentTimeMillis();

        List<Future<Void>> futures = new ArrayList<>(arguments.getThreads());
        for (int i = 0; i < arguments.getThreads(); i++) {
            futures.add(completionService.submit(taskFactory.createTask(arguments), null));
        }

        int i = arguments.getThreads();
        while ((0 < i--) && (completionService.take() != null)) {
            System.out.println(i);
        }

        System.out.println();
        System.out.println("Test finished in " + (System.currentTimeMillis() - startStamp) + "ms");
        System.out.flush();

        // Shutdown the executor service.
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

    }


}
