package juitar.worker.jdbc;

import org.juitar.workerq.Work;

/**
 * @author sha1n
 * Date: 2/22/13
 */
public interface TransactionManager {
    void addTxWork(Work work);
}
