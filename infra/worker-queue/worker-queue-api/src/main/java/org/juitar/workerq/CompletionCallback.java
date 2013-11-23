package org.juitar.workerq;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public interface CompletionCallback {

    void onSuccess(Result result);

    void onFailure(Result result, Exception e, CompletionStatus status);
}
