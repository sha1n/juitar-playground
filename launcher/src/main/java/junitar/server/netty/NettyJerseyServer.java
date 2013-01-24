package junitar.server.netty;

import com.sun.jersey.api.container.ContainerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import junitar.server.netty.jersey.HttpChannelPipelineFactory;
import junitar.server.netty.jersey.NettyJerseyHandler;
import org.jboss.netty.channel.ChannelPipelineFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sha1n
 * Date: 1/22/13
 */
public class NettyJerseyServer {

    public static final void main(String... args) {

        final Map<String, Object> props = new HashMap<>();
        props.put(PackagesResourceConfig.PROPERTY_PACKAGES, "juitar.web.rest.resource");
        props.put(NettyJerseyHandler.RESOURCE_CONFIG_BASE_URI, "/api");
        ResourceConfig resourceConfig = new PackagesResourceConfig(props);

        NettyJerseyHandler jerseyHandler = ContainerFactory.createContainer(NettyJerseyHandler.class, resourceConfig);
        ChannelPipelineFactory channelPipelineFactory = new HttpChannelPipelineFactory(jerseyHandler);

        NettyServer nettyServer = new NettyServer(channelPipelineFactory, 8080);

        nettyServer.start();
    }
}
