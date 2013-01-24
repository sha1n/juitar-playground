package junitar.server.netty.jersey;

import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.ContainerProvider;
import com.sun.jersey.spi.container.WebApplication;

/**
 * @author sha1n
 * Date: 1/22/13
 */
public class NettyJerseyContainerProvider implements ContainerProvider<NettyJerseyHandler> {

    public NettyJerseyHandler createContainer(final Class<NettyJerseyHandler> type,
                                              final ResourceConfig resourceConfig,
                                              final WebApplication application) throws ContainerException {
        NettyJerseyHandler channelUpstreamHandlerNetty = null;
        if (type == NettyJerseyHandler.class) {
            channelUpstreamHandlerNetty = new NettyJerseyHandler(application, resourceConfig);
        }

        return channelUpstreamHandlerNetty;
    }
}
