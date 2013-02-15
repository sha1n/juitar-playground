package juitar.server.netty;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

/**
 * @author sha1n
 * Date: 1/25/13
 */
public class NettySpringContainerTest {

    private NettySpringContainer container;

    @Before
    public void setup() {
        container = new NettySpringContainer();
    }

    @After
    public void teardown() {
        container.stop();
    }

    @Test
    public void testStartStopped() throws Exception {
        container.start();

        Client client = Client.create();
        WebResource resource = client.resource("http://localhost:8080/async");
        ClientResponse response = resource.accept(MediaType.TEXT_PLAIN_TYPE).get(ClientResponse.class);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testStartStarted() throws Exception {
        testStartStopped();

        container.start();

        Client client = Client.create();
        WebResource resource = client.resource("http://localhost:8080/async");
        ClientResponse response = resource.accept(MediaType.TEXT_PLAIN_TYPE).get(ClientResponse.class);
        Assert.assertEquals(200, response.getStatus());
    }


    @Test(expected = ClientHandlerException.class)
    public void testStopStopped() throws Exception {
        container.stop();

        Client client = Client.create();
        WebResource resource = client.resource("http://localhost:8080/async");
        resource.accept(MediaType.TEXT_PLAIN_TYPE).get(ClientResponse.class);
    }

    @Test(expected = ClientHandlerException.class)
    public void testStopStarted() throws Exception {
        testStartStopped();

        container.stop();

        Client client = Client.create();
        WebResource resource = client.resource("http://localhost:8080/async");
        resource.accept(MediaType.TEXT_PLAIN_TYPE).get(ClientResponse.class);
    }

}
