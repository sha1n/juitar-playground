package juitar;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author sha1n
 * Date: 1/24/13
 */
public class PutTask extends RequestTask {


    public PutTask(int requests, String url) {
        super(requests, url);
    }


    protected ClientResponse issueRequest(WebResource webResource) {
        return webResource.accept("text/plain").put(ClientResponse.class, "INSERT INTO TEST VALUES (2, 'str2')");
    }
}
