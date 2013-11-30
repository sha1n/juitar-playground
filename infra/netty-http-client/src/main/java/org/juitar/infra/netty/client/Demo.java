package org.juitar.infra.netty.client;


import java.net.URISyntaxException;

/**
 * @author sha1n
 * Date: 11/24/13
 */
public class Demo {

    public static void main(String... args) throws URISyntaxException, InterruptedException {

        HttpClient httpClient = new HttpClient(10, "localhost", 8080);

        try {
            for (int i = 0; i < 100; i++) {
                httpClient.get("/api/status", new ResponseHandler() {
                    @Override
                    public void handleResponse(HttpResponse httpResponse) {
                        System.out.println("Response received");
                    }
                }).commit();
            }


            Thread.sleep(3 * 1000);
        } finally {
            httpClient.close();
        }


    }
}
