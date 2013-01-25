package juitar;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author sha1n
 * Date: 1/24/13
 */
public class GetTask extends RequestTask {


    public GetTask(int requests, String url) {
        super(requests, url);
    }

    @Override
    protected ClientResponse issueRequest(WebResource webResource) {
        return webResource.accept("text/plain").get(ClientResponse.class);
    }
}
