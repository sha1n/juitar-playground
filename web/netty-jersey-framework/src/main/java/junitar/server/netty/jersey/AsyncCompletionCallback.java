package junitar.server.netty.jersey;

/**
 * @author sha1n
 * Date: 1/24/13
 */
public interface AsyncCompletionCallback {

    void onSuccess();

    void onFailure();
}
