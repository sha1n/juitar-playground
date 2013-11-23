package org.juitar.workerq.demo;

import org.juitar.workerq.Worker;
import org.juitar.workerq.WorkerFactory;

/**
 * @author sha1n
 * Date: 1/19/13
 */
public class WorkerFactoryImpl implements WorkerFactory {
    @Override
    public Worker createWorker() {
        return new WorkerImpl();
    }
}
