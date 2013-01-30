package org.juitar.test.rest.client;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.net.URI;

/**
 * @author sha1n
 * Date: 1/24/13
 */
class GetTask extends RequestTask {


    GetTask(int requests, URI url) {
        super(requests, url);
    }

    @Override
    protected ClientResponse issueRequest(WebResource webResource) {
        return webResource.accept("text/plain").get(ClientResponse.class);
    }
}
