package juitar.worker.jdbc;

import juitar.worker.queue.Worker;
import juitar.worker.queue.WorkerFactory;
import org.springframework.beans.factory.annotation.Required;

import javax.sql.DataSource;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public class JdbcBatchWorkerFactory implements WorkerFactory {

    private DataSource jdbcTemplate;

    @Required
    public final void setJdbcTemplate(DataSource jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public final Worker createWorker() {
        return new JdbcBatchWorker(jdbcTemplate);
    }
}
