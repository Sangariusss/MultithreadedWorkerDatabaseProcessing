package test.java.com.sangarius.multithreadedworkerdatabaseprocessing.tests;

import static junit.framework.TestCase.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import main.java.com.sangarius.multithreadedworkerdatabaseprocessing.model.entities.Worker;
import main.java.com.sangarius.multithreadedworkerdatabaseprocessing.util.DatabaseUtil;
import main.java.com.sangarius.multithreadedworkerdatabaseprocessing.model.dataGenerator.DataGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class WorkerGeneratorTest {

    @Test
    public void testGenerateAndSaveWorker() throws SQLException {
        DatabaseUtil.clearWorkersTable();
        int numWorkers = 1;
        List<Worker> workers = DataGenerator.generateWorkers(numWorkers);
        for (Worker worker : workers) {
            DatabaseUtil.insertWorker(worker);
        }
    }

    @Test
    public void testGenerateMultipleWorkers() throws SQLException {
        int numWorkers = 10;
        List<Worker> workers = DataGenerator.generateWorkers(numWorkers);
        for (Worker worker : workers) {
            DatabaseUtil.insertWorker(worker);
        }
    }

    @Test
    public void testPrintWorkersWithMoreExperience() throws SQLException {
        int yearsOfExperience = 5;
        int countWorkersWithExperience = DatabaseUtil.getCountWorkersWithExperience(yearsOfExperience);
        System.out.println("Number of workers with experience more than " + yearsOfExperience + " years: " + countWorkersWithExperience);
    }

    @Test
    public void testPrintMaxAndMinSalary() throws SQLException {
        try (ResultSet resultSet = DatabaseUtil.getMaxAndMinSalary()) {
            while (resultSet.next()) {
                int maxSalary = resultSet.getInt("max_salary");
                int minSalary = resultSet.getInt("min_salary");
                System.out.println("Maximum salary: " + maxSalary);
                System.out.println("Minimum salary: " + minSalary);
            }
        }
    }

    @Test
    public void testPrintWorkersWithEducation() throws SQLException {
        String education = "PhD";
        int countWorkersWithEducation = DatabaseUtil.getCountWorkersWithEducation(education);
        System.out.println("Number of workers with education '" + education + "': " + countWorkersWithEducation);
    }

    @Test
    public void testPrintCountWorkersByGender() throws SQLException {
        try (ResultSet resultSet = DatabaseUtil.getCountWorkersByGender()) {
            int maleCount = 0;
            int femaleCount = 0;
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                int count = resultSet.getInt("count");
                if (gender.equals("Male")) {
                    maleCount += count;
                } else if (gender.equals("Female")) {
                    femaleCount += count;
                }
            }

            System.out.println("Number of Male workers: " + maleCount);
            System.out.println("Number of Female workers: " + femaleCount);
        }
    }

    @Test
    public void testPrintTotalHoursWorked() throws SQLException {
        int totalHoursWorked = DatabaseUtil.getTotalHoursWorked();
        System.out.println("Total hours worked by all workers: " + totalHoursWorked);
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