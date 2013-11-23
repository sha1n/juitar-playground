package org.juitar.workerq;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class Work {

    private final String id;
    private final Object payload;
    private final CompletionCallback completionCallback;

    public Work(final String id, final Object payload, final CompletionCallback completionCallback) {
        this.id = id;
        this.payload = payload;
        this.completionCallback = completionCallback;
    }

    public final String getId() {
        return id;
    }

    public final Object getPayload() {
        return payload;
    }

    public final CompletionCallback getCompletionCallback() {
        return completionCallback;
    }


}
