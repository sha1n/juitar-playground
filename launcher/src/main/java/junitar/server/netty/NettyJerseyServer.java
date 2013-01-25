package junitar.server.netty;

import com.sun.jersey.api.container.ContainerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import juitar.context.ContextAccess;
import junitar.server.netty.jersey.HttpChannelPipelineFactory;
import junitar.server.netty.jersey.NettyJerseyHandler;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sha1n
 * Date: 1/22/13
 */
public class NettyJerseyServer {

    private GenericApplicationContext applicationContext;
    private NettyServer nettyServer;

    public static final void main(String... args) throws IOException {
        NettyJerseyServer server = new NettyJerseyServer();
        server.start();
    }

    private void start() throws IOException {

        initApplicationContext();
        initNettyServer();

        applicationContext.start();
        nettyServer.start();
    }

    private void initApplicationContext() throws IOException {
        applicationContext = new SpringContextLoader().load();
        applicationContext.refresh();
        ContextAccess.setApplicationContext(applicationContext);
    }

    private void initNettyServer() {
        final Map<String, Object> props = new HashMap<>();
        props.put(PackagesResourceConfig.PROPERTY_PACKAGES, "juitar.web.rest.resource");
        props.put(NettyJerseyHandler.RESOURCE_CONFIG_BASE_URI, "/api");
        ResourceConfig resourceConfig = new PackagesResourceConfig(props);

        NettyJerseyHandler jerseyHandler = ContainerFactory.createContainer(NettyJerseyHandler.class, resourceConfig);
        ChannelPipelineFactory channelPipelineFactory = new HttpChannelPipelineFactory(jerseyHandler);

        nettyServer = new NettyServer(channelPipelineFactory, 8080);
    }

    private void stop() {
        try {
            applicationContext.stop();
        } finally {
            nettyServer.stop();
        }
    }
}
