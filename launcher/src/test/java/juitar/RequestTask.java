package juitar;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author sha1n
 * Date: 1/25/13
 */
public abstract class RequestTask implements Runnable {

    protected final int requests;
    protected final String url;

    public RequestTask(int requests, String url) {
        this.requests = requests;
        this.url = url;
    }

    @Override
    public final void run() {
        try {

            Client client = Client.create();

            for (int i = 0; i < requests; i++) {
                ClientResponse response = issueRequest(client.resource(url));
                if (response.getStatus() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
                }

                String output = response.getEntity(String.class);

//        System.out.println("Output from Server .... \n");
//        System.out.println(output);
//        System.out.flush();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    protected abstract ClientResponse issueRequest(WebResource webResource);
}
