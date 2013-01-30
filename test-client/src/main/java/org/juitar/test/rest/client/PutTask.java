package org.juitar.test.rest.client;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.net.URI;

/**
 * @author sha1n
 * Date: 1/24/13
 */
class PutTask extends RequestTask {


    PutTask(int requests, URI uri) {
        super(requests, uri);
    }


    protected ClientResponse issueRequest(WebResource webResource) {
        return webResource.accept("text/plain").put(ClientResponse.class, "INSERT INTO TEST VALUES (2, 'str2')");
    }
}
