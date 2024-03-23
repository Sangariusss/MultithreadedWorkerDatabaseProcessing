package test.java.com.sangarius.multithreadedworkerdatabaseprocessing.tests;

import main.java.com.sangarius.multithreadedworkerdatabaseprocessing.model.entities.Worker;
import main.java.com.sangarius.multithreadedworkerdatabaseprocessing.util.DatabaseUtil;
import main.java.com.sangarius.multithreadedworkerdatabaseprocessing.model.dataGenerator.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadedWorkerGeneratorTest {

    @BeforeEach
    public void setUp() throws SQLException {
        DatabaseUtil.clearWorkersTable();
    }

    @Test
    public void testGenerateAndSaveWorker() throws SQLException, InterruptedException {
        int numWorkers = 1;
        List<Worker> workers = DataGenerator.generateWorkers(numWorkers);

        ExecutorService executorService = Executors.newFixedThreadPool(numWorkers);
        for (Worker worker : workers) {
            executorService.submit(() -> {
                try {
                    DatabaseUtil.insertWorker(worker);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100);
        }
    }

    @Test
    public void testGenerateMultipleWorkers() throws SQLException, InterruptedException {
        int numWorkers = 100;
        List<Worker> workers = DataGenerator.generateWorkers(numWorkers);

        ExecutorService executorService = Executors.newFixedThreadPool(numWorkers);
        for (Worker worker : workers) {
            executorService.submit(() -> {
                try {
                    DatabaseUtil.insertWorker(worker);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(50);
        }
    }
}