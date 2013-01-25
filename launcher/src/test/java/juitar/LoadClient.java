package juitar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author sha1n
 * Date: 1/24/13
 */
public class LoadClient {

    public static void main(String... args) throws InterruptedException {

        if (args.length != 4) {
            usage();
        }

        int threads = 0;
        int requests = 0;
        try {
            switch (args[0]) {
                case "t":
                    threads = Integer.parseInt(args[1]);
                    requests = Integer.parseInt(args[3]);
                    break;
                case "r":
                    requests = Integer.parseInt(args[1]);
                    threads = Integer.parseInt(args[3]);
                    break;
                default:
                    usage();
                    break;
            }
        } catch (NumberFormatException e) {
            usage();
        }

        System.out.println("Running " + threads + " threads each issues " + requests + " requests");
        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            executorService.execute(new PutTask(requests, "http://localhost:8080/async/sql"));
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

    private static void usage() {
        System.out.println("USAGE: t <threads> r <requests-per-thread>");
    }

}
