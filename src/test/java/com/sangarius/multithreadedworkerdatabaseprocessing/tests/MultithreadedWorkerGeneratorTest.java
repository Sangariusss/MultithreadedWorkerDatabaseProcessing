package test.java.com.sangarius.multithreadedworkerdatabaseprocessing.tests;

import java.sql.ResultSet;
import main.java.com.sangarius.multithreadedworkerdatabaseprocessing.model.entities.Worker;
import main.java.com.sangarius.multithreadedworkerdatabaseprocessing.util.DatabaseUtil;
import main.java.com.sangarius.multithreadedworkerdatabaseprocessing.model.dataGenerator.DataGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadedWorkerGeneratorTest {

    @Test
    public void testGenerateAndSaveWorker() throws InterruptedException, SQLException {
        DatabaseUtil.clearWorkersTable();
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
    public void testGenerateMultipleWorkers() throws InterruptedException {
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

    @Test
    public void testPrintWorkersWithMoreExperience() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                int count = DatabaseUtil.getCountWorkersWithExperience(5);
                System.out.println("Number of workers with more than 5 years of experience: " + count);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100);
        }
    }

    @Test
    public void testPrintMaxAndMinSalary() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                int maxSalary = 0;
                int minSalary = 0;
                try (ResultSet resultSet = DatabaseUtil.getMaxAndMinSalary()) {
                    if (resultSet.next()) {
                        maxSalary = resultSet.getInt("max_salary");
                        minSalary = resultSet.getInt("min_salary");
                    }
                }
                System.out.println("Maximum salary: " + maxSalary);
                System.out.println("Minimum salary: " + minSalary);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100);
        }
    }

    @Test
    public void testPrintWorkersWithEducation() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                int count = DatabaseUtil.getCountWorkersWithEducation("PhD");
                System.out.println("Number of workers with education 'PhD': " + count);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100);
        }
    }

    @Test
    public void testPrintCountWorkersByGender() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                int maleCount = 0;
                int femaleCount = 0;
                try (ResultSet resultSet = DatabaseUtil.getCountWorkersByGender()) {
                    while (resultSet.next()) {
                        String gender = resultSet.getString("gender");
                        int count = resultSet.getInt("count");
                        if (gender.equals("Male")) {
                            maleCount += count;
                        } else if (gender.equals("Female")) {
                            femaleCount += count;
                        }
                    }
                }
                System.out.println("Number of Male workers: " + maleCount);
                System.out.println("Number of Female workers: " + femaleCount);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100);
        }
    }

    @Test
    public void testPrintTotalHoursWorked() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                int totalHoursWorked = DatabaseUtil.getTotalHoursWorked();
                System.out.println("Total hours worked by all workers: " + totalHoursWorked);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100);
        }
    }

    @AfterAll
    public static void tearDown() {
        try {
            DatabaseUtil.clearWorkersTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}