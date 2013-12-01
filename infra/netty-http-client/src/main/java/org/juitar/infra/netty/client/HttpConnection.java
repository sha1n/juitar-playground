package org.juitar.infra.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author sha1n
 * Date: 11/25/13
 */
public class HttpConnection {

    final Channel channel;
    private final HttpConnectionPool pool;
    private final boolean keepAlive;
    private boolean reading = false;
    private boolean closed = false;
    private ResponseHandler responseHandler;
    private boolean notifiedHandler = false;


    public HttpConnection(HttpConnectionPool pool, Channel channel, boolean keepAlive) {
        this.pool = pool;
        this.keepAlive = keepAlive;
        this.channel = channel;
    }

    public void init(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
        this.notifiedHandler = false;
    }

    public final ChannelFuture write(Object obj) {
        return channel.write(obj);
    }

    public final ChannelFuture writeNow(Object obj) {
        if (reading) {
            return write(obj);
        } else {
            return channel.writeAndFlush(obj);
        }
    }

    public final void flush() {
        channel.flush();
    }

    public final void readNow() {
        reading = true;
        try {
            channel.read();
            channel.flush();
        } finally {
            reading = false;
        }
    }

    public final void close() {
        channel.flush();

        if (!keepAlive) {
            dispose();
        } else {
            // Allow the pool to recycle the connection.
            pool.recycle(this);
        }
    }

    public final void setAutoRead(boolean autoRead) {
        channel.config().setAutoRead(autoRead);
    }

    final void dispose() {
        channel.flush();
        channel.close();
        closed = true;
    }

    public boolean isOpen() {
        return !closed && channel.isOpen();
    }

    public boolean isClosed() {
        return closed || !channel.isOpen();
    }

    public void notifyReadComplete(HttpResponse httpResponse) {
        if (!notifiedHandler) {
            notifiedHandler = true;
            this.responseHandler.handleResponse(httpResponse);
        }
    }

}
