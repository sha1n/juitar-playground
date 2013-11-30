package org.juitar.infra.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sha1n
 * Date: 11/25/13
 */
public class HttpConnectionPool implements Closeable {

    private final int maxSize;
    private final AtomicInteger currentConnections = new AtomicInteger(0);
    private final ConcurrentMap<Channel, HttpConnection> channelToConnectionMap = new ConcurrentHashMap<>();
    private final Queue<HttpConnection> available = new ConcurrentLinkedQueue<>();
    private final Bootstrap bootstrap = new Bootstrap();
    private final String hostname;
    private final int port;
    private volatile boolean open = true;
    private ExceptionHandler exceptionHandler;

    public HttpConnectionPool(int maxSize, String hostname, int port) {
        this.maxSize = maxSize;
        this.hostname = hostname;
        this.port = port;
    }

    public void connect(ConnectRequest connectRequest) {
        HttpConnection httpConnection = available.poll();

        if (httpConnection == null) {
            if (maxSize > currentConnections.get()) {
                connect0(connectRequest);
            } else {
                // TODO: either wait for a connection or reject the request
            }
        }

    }

    void init() {
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
                    currentConnections.incrementAndGet();
                    channelToConnectionMap.put(channel, httpConnection);

                    connectRequest.connected(httpConnection);
                } else {
                    connectRequest.failed(future.cause());
                }
            }
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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

    boolean isClosed() {
        return !open;
    }


    public final ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public final void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void close() throws IOException {
        open = false;
        for (HttpConnection connection : channelToConnectionMap.values()) {
            connection.dispose();
        }
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }


    private class ChannelHandler extends ChannelDuplexHandler {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            HttpConnection httpConnection = channelToConnectionMap.get(ctx.channel());
            httpConnection.readNow();

            if (msg instanceof io.netty.handler.codec.http.HttpResponse) {
                HttpResponse httpResponse = new HttpResponseImpl(httpConnection, (io.netty.handler.codec.http.HttpResponse) msg);
                httpConnection.notifyReadComplete(httpResponse);
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            HttpConnection httpConnection = channelToConnectionMap.get(ctx.channel());
            httpConnection.readNow();

        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            super.write(ctx, msg, promise);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
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
