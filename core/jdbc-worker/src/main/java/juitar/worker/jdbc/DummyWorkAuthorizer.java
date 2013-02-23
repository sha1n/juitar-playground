package juitar.worker.jdbc;

import org.juitar.workerq.Work;

/**
 * @author sha1n
 * Date: 2/23/13
 */
public class DummyWorkAuthorizer implements WorkAuthorizer {
    @Override
    public void authorize(Work work) {
    }
}
