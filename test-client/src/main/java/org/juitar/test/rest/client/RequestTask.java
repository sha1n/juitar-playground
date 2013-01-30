package org.juitar.test.rest.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.net.URI;

/**
 * @author sha1n
 * Date: 1/25/13
 */
abstract class RequestTask implements Runnable {

    private final int requests;
    private final URI uri;

    RequestTask(int requests, URI uri) {
        this.requests = requests;
        this.uri = uri;
    }

    @Override
    public final void run() {
        try {

            Client client = Client.create();

            for (int i = 0; i < requests; i++) {
                ClientResponse response = issueRequest(client.resource(uri));
                int status = response.getStatus();
                if (status >= 300) {
                    throw new RuntimeException("Failed : HTTP error code : " + status);
                }
                if (status < 300 && status > 200) {
                    System.out.println("HTTP Code: " + status + ", " + response.getClientResponseStatus().getReasonPhrase());
                } else {

                    String output = response.getEntity(String.class);

                    System.out.println("Output from Server .... \n");
                    System.out.println(output);
                    System.out.flush();
                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    protected abstract ClientResponse issueRequest(WebResource webResource);
}
