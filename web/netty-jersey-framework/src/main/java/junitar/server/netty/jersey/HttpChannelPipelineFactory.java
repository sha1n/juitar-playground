package junitar.server.netty.jersey;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author sha1n
 * Date: 1/22/13
 */
public class HttpChannelPipelineFactory implements
        ChannelPipelineFactory {

    private final ChannelHandler channelHandler;

    public HttpChannelPipelineFactory(final ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;
    }

    public final ChannelPipeline getPipeline() {
        final ChannelPipeline pipeline = new DefaultChannelPipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("channelHandler", channelHandler);
        return pipeline;
    }
}
