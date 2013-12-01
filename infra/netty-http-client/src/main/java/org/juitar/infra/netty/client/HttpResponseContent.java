package org.juitar.infra.netty.client;

import io.netty.handler.codec.http.HttpContent;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sha1n
 * Date: 12/1/13
 */
public class HttpResponseContent {

    private final List<HttpContent> contentParts = new ArrayList<>();

    void addContentPart(HttpContent contentPart) {
        contentParts.add(contentPart);
    }

    public String asString(String charset) {
        StringBuilder contentStringBuilder = new StringBuilder();
        for (HttpContent content : contentParts) {
            contentStringBuilder.append(content.content().toString(Charset.forName("UTF-8")));
        }

        return contentStringBuilder.toString();
    }
}
