package junitar.server.netty.jersey;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.header.InBoundHeaders;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.WebApplication;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author sha1n
 * Date: 1/22/13
 */
public class NettyJerseyHandler extends SimpleChannelUpstreamHandler {

    public static final String RESOURCE_CONFIG_BASE_URI = "juitar.netty.jersey.resource.base.uri";
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyJerseyHandler.class);

    private final transient WebApplication application;
    private final transient String baseUri;

    public NettyJerseyHandler(final WebApplication application,
                              final ResourceConfig resourceConfig) {
        super();
        this.application = application;
        this.baseUri = (String) resourceConfig.getProperty(RESOURCE_CONFIG_BASE_URI);
    }

    @Override
    public void messageReceived(final ChannelHandlerContext context,
                                final MessageEvent messageEvent) throws URISyntaxException,
            IOException {
        final HttpRequest request = (HttpRequest) messageEvent.getMessage();

        final String base = getBaseUri(request);
        final URI baseUri = new URI(base);
        final URI requestUri = new URI(request.getUri().substring(1));

        final ContainerRequest cRequest = new ContainerRequest(
                application,
                request.getMethod().getName(),
                baseUri,
                requestUri,
                getHeaders(request),
                new ChannelBufferInputStream(request.getContent()));

        application.handleRequest(cRequest, new NettyJerseyResponseWriter(messageEvent.getChannel()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        LOGGER.warn("Unexpected exception from downstream.", e.getCause());
        e.getChannel().close();
    }

    private InBoundHeaders getHeaders(final HttpRequest request) {
        final InBoundHeaders headers = new InBoundHeaders();

        for (String name : request.getHeaderNames()) {
            headers.put(name, request.getHeaders(name));
        }

        return headers;
    }

    private String getBaseUri(final HttpRequest request) {
        String baseUri = this.baseUri;
        if (baseUri == null) {
            baseUri = "http://" + request.getHeader(HttpHeaders.Names.HOST) + "/";
        }
        return baseUri;

    }

}
