package main.java.com.sangarius.multithreadedworkerdatabaseprocessing.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import main.java.com.sangarius.multithreadedworkerdatabaseprocessing.model.entities.Worker;

public class DatabaseUtil {

    private static final String URL = "jdbc:h2:./src/main/resources/db/workers_database";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void closeResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    public static void insertWorker(Worker worker) throws SQLException {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO workers (id, firstName, lastName, age, gender, education, experience, salary, hoursWorked) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setInt(1, worker.getId());
            statement.setString(2, worker.getFirstName());
            statement.setString(3, worker.getLastName());
            statement.setInt(4, worker.getAge());
            statement.setString(5, worker.getGender());
            statement.setString(6, worker.getEducation());
            statement.setInt(7, worker.getExperience());
            statement.setInt(8, worker.getSalary());
            statement.setInt(9, worker.getHoursWorked());
            statement.executeUpdate();
        } finally {
            closeConnection(connection);
        }
    }

    public static void clearWorkersTable() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM workers");
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static int getCountWorkersWithExperience(int minExperience) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) FROM workers WHERE experience > ?");
            statement.setInt(1, minExperience);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } finally {
            closeResultSet(resultSet);
            closeConnection(connection);
        }
        return 0;
    }

    public static ResultSet getMaxAndMinSalary() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT MAX(salary) AS max_salary, MIN(salary) AS min_salary FROM workers;\n");
            return resultSet;
        } catch (SQLException e) {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            closeConnection(connection);
            throw e;
        }
    }

    public static int getCountWorkersWithEducation(String education) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) FROM workers WHERE education = ?");
            statement.setString(1, education);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } finally {
            closeResultSet(resultSet);
            closeConnection(connection);
        }
        return 0;
    }

    public static ResultSet getCountWorkersByGender() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT gender, COUNT(*) as count FROM workers GROUP BY gender");
            return resultSet;
        } catch (SQLException e) {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            throw e;
        }
    }

    public static int getTotalHoursWorked() throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(
                "SELECT SUM(hoursWorked) FROM workers");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } finally {
            closeResultSet(resultSet);
            closeConnection(connection);
        }
        return 0;
    }
}