package juitar.worker.jdbc;

import juitar.worker.queue.Worker;
import juitar.worker.queue.WorkerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

/**
 * @author sha1n
 * Date: 1/20/13
 */
public class JdbcBatchWorkerFactory implements WorkerFactory {

    private JdbcTemplate jdbcTemplate;

    public final void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.setQueryTimeout(1);
        jdbcTemplate.setFetchSize(500);
        jdbcTemplate.setMaxRows(1000);
    }

    @Override
    public final Worker createWorker() {
        return new JdbcBatchWorker(jdbcTemplate);
    }
}
