package org.juitar.test.rest.client;

import java.net.URI;

/**
 * @author sha1n
 * Date: 1/30/13
 */
class Args {

    private URI uri;
    private String method;
    private int threads;
    private int requests;

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public int getThreads() {
        return threads;
    }

    public int getRequests() {
        return requests;
    }
}
