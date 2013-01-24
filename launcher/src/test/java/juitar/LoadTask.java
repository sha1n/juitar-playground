package juitar;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author sha1n
 * Date: 1/24/13
 */
public class LoadTask implements Runnable {
    @Override
    public void run() {
        try {

            Client client = Client.create();

            for (int i = 0; i < 100; i++) {
                issueRequest(client);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void issueRequest(Client client) {
        WebResource webResource = client
                .resource("http://localhost:8080/async");

        ClientResponse response = webResource.accept("text/plain")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String output = response.getEntity(String.class);

//        System.out.println("Output from Server .... \n");
//        System.out.println(output);
//        System.out.flush();
    }
}
