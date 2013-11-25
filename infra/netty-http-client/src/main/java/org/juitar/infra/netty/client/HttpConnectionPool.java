package org.juitar.infra.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sha1n
 * Date: 11/25/13
 */
public class HttpConnectionPool {

    private final int maxSize;
    private final AtomicInteger currentConnections = new AtomicInteger(0);
    private final Queue<HttpConnection> available = new ConcurrentLinkedQueue<>();
    private final Bootstrap bootstrap = new Bootstrap();
    private final String hostname;
    private final int port;

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
            httpConnection.dispose();
        }
    }
}
