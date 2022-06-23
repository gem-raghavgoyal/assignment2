package com.raghav.assignment2student.services;

import com.raghav.assignment2student.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Configuration
public class ExecutorsService {
    private static final Logger log = LoggerFactory.getLogger(ExecutorsService.class);

    ExecutorService executor = Executors.newFixedThreadPool(10);

    /* Execute list of callable tasks through executor service*/
    public List<Future<Project>> execute(List<Callable<Project>> callableTasks) {
        List<Future<Project>> futures = new ArrayList<>();
        try{
            futures = executor.invokeAll(callableTasks);

        }catch (Exception e){
            log.error("Exception occurred while submitting job to executor service");
        }
        executor.shutdown();
        executor.shutdownNow();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("Exception occurred while shutting down executor service");
        }
        return futures;
    }

    /* Execute a callable task */
    public Future<Project> execute(Callable<Project> callable) {

        Future<Project> future = executor.submit(callable);

        executor.shutdown();
        executor.shutdownNow();
        try {
            executor.awaitTermination(20l, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return future;
    }

    /* Execute a runnable task*/
    public void executeRunnable(Callable callable){
        executor.submit(callable);

        executor.shutdown();
        executor.shutdownNow();
        try {
            executor.awaitTermination(20l, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
