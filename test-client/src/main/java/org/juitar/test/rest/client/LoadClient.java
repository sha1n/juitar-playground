package org.juitar.test.rest.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        for (int i = 0; i < arguments.getThreads(); i++) {
            executorService.execute(taskFactory.createTask(arguments));
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }


}
