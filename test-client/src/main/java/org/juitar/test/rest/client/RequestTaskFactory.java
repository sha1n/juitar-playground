package org.juitar.test.rest.client;

import javax.ws.rs.HttpMethod;

/**
 * @author sha1n
 * Date: 1/30/13
 */
class RequestTaskFactory {

    public RequestTask createTask(Args args) {
        switch (args.getMethod()) {
            case HttpMethod.GET:
                return new GetTask(args.getRequests(), args.getUri());
            case HttpMethod.PUT:
                return new PutTask(args.getRequests(), args.getUri());
            default:
                throw new IllegalArgumentException("Unsupported method: " + args.getMethod());
        }
    }
}
