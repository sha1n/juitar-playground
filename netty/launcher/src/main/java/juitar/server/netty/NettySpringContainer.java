package juitar.server.netty;

import com.sun.jersey.api.container.ContainerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import juitar.spring.SpringContextLoader;
import junitar.server.netty.NettyServer;
import junitar.server.netty.jersey.HttpChannelPipelineFactory;
import junitar.server.netty.jersey.NettyJerseyHandler;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.juitar.flags.xml.Loader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sha1n
 * Date: 1/25/13
 */
@ManagedResource(objectName = "juitar:name=container")
class NettySpringContainer {

    private GenericApplicationContext applicationContext;
    private NettyServer nettyServer;
    private volatile State state = new Stopped();

    interface State {
        State start() throws IOException;

        State stop();
    }

    final class Stopped implements State {

        @Override
        public State start() throws IOException {
            initApplicationContext();
            initNettyServer();

            applicationContext.start();
            Loader.loadFlags(Launcher.class.getResourceAsStream("/flags.xml"));
            nettyServer.start();

            return new Started();
        }

        @Override
        public State stop() {
            return this;
        }
    }

    final class Started implements State {

        @Override
        public State start() throws IOException {
            return this;
        }

        @Override
        public State stop() {
            try {
                applicationContext.stop();
                applicationContext.destroy();
                applicationContext = null;
            } finally {
                nettyServer.stop();
                state = new Stopped();
            }

            return state;
        }
    }

    @ManagedOperation(description = "Starts the container")
    public final void start() throws IOException {
        state = state.start();
    }

    @ManagedOperation(description = "Stops the container")
    public void stop() {
        state = state.stop();
    }

    private void initApplicationContext() throws IOException {
        applicationContext = new SpringContextLoader().load();
        applicationContext.refresh();
    }

    private void initNettyServer() {
        final Map<String, Object> props = new HashMap<>();
        props.put(PackagesResourceConfig.PROPERTY_PACKAGES, new String[]{"juitar.web.rest.resource"});
        props.put(NettyJerseyHandler.RESOURCE_CONFIG_BASE_URI, "/api");
        ResourceConfig resourceConfig = new PackagesResourceConfig(props);

        NettyJerseyHandler jerseyHandler = ContainerFactory.createContainer(NettyJerseyHandler.class, resourceConfig);
        ChannelPipelineFactory channelPipelineFactory = new HttpChannelPipelineFactory(jerseyHandler);

        nettyServer = new NettyServer(channelPipelineFactory, 8080);
    }

}
