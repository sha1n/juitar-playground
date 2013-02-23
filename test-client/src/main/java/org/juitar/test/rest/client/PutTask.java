package org.juitar.test.rest.client;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.net.URI;
import java.util.UUID;

/**
 * @author sha1n
 * Date: 1/24/13
 */
class PutTask extends RequestTask {


    PutTask(int requests, URI uri) {
        super(requests, uri);
    }


    protected ClientResponse issueRequest(WebResource webResource) {
        String updateTemplate = "INSERT INTO TEST VALUES (%d, %d ,'%s', '%s')";
        return webResource.accept("text/plain").put(ClientResponse.class,
                String.format(updateTemplate,
                        System.currentTimeMillis(),
                        System.nanoTime(),
                        UUID.randomUUID().toString(),
                        "Note that the resource method ultimately has control over the response content. If a javax.ws.rs.core.Response is returned, then the developer can return whatever Content-Type is desired. The @Consumes and @Produces is primarily useful only when processing request information and determining which resource method is possible to invoke. If a specific Response content type is not specified via a returned javax.ws.rs.core.Response object, the response media type is determined by a combination of the @Produces annotation values and the available MessageBodyWriters for the response entitys Java type. This can lead to undesired results if there is no @Produces annotation or if the @Produces annotation has multiple media types listed."));
    }
}
