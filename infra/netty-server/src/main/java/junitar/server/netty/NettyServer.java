package junitar.server.netty;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author sha1n
 * Date: 1/22/13
 */
public class NettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);
    private static final ChannelGroup ALL_CHANNELS = new DefaultChannelGroup();

    private final ChannelPipelineFactory pipelineFactory;
    private final ServerBootstrap bootstrap;
    private final int port;

    public NettyServer(final ChannelPipelineFactory pipelineFactory, final int port) {
        this.pipelineFactory = pipelineFactory;
        this.port = port;
        this.bootstrap = initialize();

    }

    public void start() {
        LOGGER.info("Starting Netty Server....");

        InetSocketAddress socketAddress = new InetSocketAddress(port);
        Channel serverChannel = bootstrap.bind(socketAddress);
        ALL_CHANNELS.add(serverChannel);

        LOGGER.info("Netty Server listening on port " + port);
    }

    public void stop() {
        LOGGER.info("Netty Server going down....");

        ChannelGroupFuture future = ALL_CHANNELS.close();
        future.awaitUninterruptibly();

        bootstrap.getFactory().releaseExternalResources();

        ALL_CHANNELS.clear();

        LOGGER.info("Netty Server is down.");

    }

    private ServerBootstrap initialize() {
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newFixedThreadPool(2),
                        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2)));

        bootstrap.setPipelineFactory(pipelineFactory);

        return bootstrap;
    }
}
