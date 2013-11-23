package org.juitar.workerq.demo;

import org.juitar.workerq.CompletionCallback;
import org.juitar.workerq.CompletionStatus;
import org.juitar.workerq.Result;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class CompletionCallbackImpl implements CompletionCallback {
    @Override
    public void onSuccess(Result result) {
        System.out.println(result);
    }

    @Override
    public void onFailure(Result result, Exception e, CompletionStatus status) {
        e.printStackTrace();
    }
}
