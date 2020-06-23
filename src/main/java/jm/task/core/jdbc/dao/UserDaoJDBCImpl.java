package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl(Connection connection) {
        this.connection = connection;
    }

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {
            String sql = "create table if not exists users (id bigint auto_increment, name varchar(256), lastName varchar(256), age tinyint, primary key (id))";
            statement.execute(sql);
        } catch (SQLException e) {
            Assert.fail("Ошибка базы данных\n" + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE IF EXISTS users";
            statement.execute(sql);
        } catch (SQLException e) {
            Assert.fail("Ошибка базы данных\n" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (`name`, `lastName`, `age`) VALUES (?, ?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.execute();
        } catch (SQLException e) {
            Assert.fail("Ошибка базы данных\n" + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE (`id` = ?);";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.execute();
        } catch (SQLException e) {
            Assert.fail("Ошибка базы данных\n" + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            User user = new User();
            ResultSet resultSet = stmt.executeQuery("select * from users");

            while (resultSet.next()) {
                list.add(new User(resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4)));
            }
            resultSet.close();
        } catch (SQLException e) {
            Assert.fail("Ошибка базы данных\n" + e.getMessage());
        }

        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String sql = "Truncate table users;";
            statement.execute(sql);
        } catch (SQLException e) {
            Assert.fail("Ошибка базы данных\n" + e.getMessage());
        }
    }
}
