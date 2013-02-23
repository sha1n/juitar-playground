package juitar.worker.jdbc;

import org.juitar.workerq.Work;

/**
 * @author sha1n
 * Date: 2/23/13
 */
public interface WorkPreProcessor {

    Work process(Work work);

}
