package junitar.server.netty.jersey;

import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseWriter;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sha1n
 * Date: 1/22/13
 */
public class NettyJerseyResponseWriter implements ContainerResponseWriter {

    private final Channel channel;
    private HttpResponse httpResponse;

    public NettyJerseyResponseWriter(final Channel channel) {
        this.channel = channel;
    }

    public final OutputStream writeStatusAndHeaders(final long contentLength, final ContainerResponse containerResponse) throws IOException {

        httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(containerResponse.getStatus()));
        List<String> values = new ArrayList<>();
        Set<Map.Entry<String, List<Object>>> headers = containerResponse.getHttpHeaders().entrySet();
        for (Map.Entry<String, List<Object>> header : headers) {
            for (Object value : header.getValue()) {
                values.add(ContainerResponse.getHeaderValue(value));
            }
            httpResponse.setHeader(header.getKey(), values);
            values.clear();
        }

        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        httpResponse.setContent(buffer);

        return new ChannelBufferOutputStream(buffer);

    }

    public final void finish() throws IOException {
        channel.write(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }


}
