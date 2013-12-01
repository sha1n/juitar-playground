package org.juitar.infra.netty.client;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author sha1n
 * Date: 11/24/13
 */
public class Demo {

    public static void main(String... args) throws URISyntaxException, InterruptedException, IOException, ExecutionException {

        HttpClient httpClient = new HttpClient(10, "localhost", 8080);

        try {
            httpClient.get("/api/status", new ResponseHandler() {
                @Override
                public void handleResponse(HttpResponse httpResponse) {
                    System.out.println("Response received: " + httpResponse.getStatusLine());
                    System.out.println("Body: " + httpResponse.getBodyAsString());
                }
            }).commit();


            Thread.sleep(TimeUnit.MINUTES.toMillis(1));
        } finally {
            httpClient.close();
        }

    }
}
