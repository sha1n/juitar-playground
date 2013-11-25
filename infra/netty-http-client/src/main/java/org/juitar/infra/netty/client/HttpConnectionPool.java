package org.juitar.infra.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;

import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sha1n
 * Date: 11/25/13
 */
public class HttpConnectionPool {

    private final int maxSize;
    private final AtomicInteger currentConnections = new AtomicInteger(0);
    private final ConcurrentMap<Channel, HttpConnection> channelToConnectionMap = new ConcurrentHashMap<>();
    private final Queue<HttpConnection> available = new ConcurrentLinkedQueue<>();
    private final Bootstrap bootstrap = new Bootstrap();
    private final String hostname;
    private final int port;
    private ExceptionHandler exceptionHandler;

    public HttpConnectionPool(int maxSize, String hostname, int port) {
        this.maxSize = maxSize;
        this.hostname = hostname;
        this.port = port;
    }

    public void connect(ConnectRequest connectRequest) {
        HttpConnection httpConnection = available.poll();

        if (httpConnection == null) {
            if (maxSize < currentConnections.get()) {
                connect(connectRequest);
            } else {
                // TODO: either wait for a connection or reject the request
            }
        }

    }

    private void init() {
        bootstrap.group(new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()));
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new HttpClientCodec());
                pipeline.addLast(new ChannelHandler());
            }
        });

    }

    private void connect0(final ConnectRequest connectRequest) {

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(hostname, port));

        future.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    final Channel channel = future.channel();
                    HttpConnection httpConnection = new HttpConnection(HttpConnectionPool.this, channel, true);
                    connectRequest.connected(httpConnection);
                    currentConnections.incrementAndGet();
                    channelToConnectionMap.put(channel, httpConnection);
                } else {
                    connectRequest.failed(future.cause());
                }
            }
        });
    }

    final void recycle(HttpConnection httpConnection) {
        // Return to available queue only if it's not full
        if (currentConnections.get() < maxSize) {
            available.add(httpConnection);
        } else {
            currentConnections.decrementAndGet();
            channelToConnectionMap.remove(httpConnection.channel);
            httpConnection.dispose();
        }
    }

    final void recycle(Channel channel) {
        HttpConnection httpConnection = channelToConnectionMap.get(channel);
        recycle(httpConnection);
    }


    public final ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public final void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    private class ChannelHandler extends ChannelDuplexHandler {

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            if (HttpConnectionPool.this.exceptionHandler != null) {
                HttpConnectionPool.this.exceptionHandler.handle(cause);
            }
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            HttpConnectionPool.this.recycle(ctx.channel());
        }
    }

}
