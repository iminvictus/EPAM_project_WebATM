package dao.users;

import dao.DataAccessObject;
import lombok.extern.log4j.Log4j;
import models.Role;
import models.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class UserDAO extends DataAccessObject<User> {
    private static final String FIND_BY_ID = "SELECT id_user, name, surname, phone, email, password, secret_word, role FROM users WHERE id_user = ?";
    private static final String FIND_ALL = "SELECT id_user, name, surname, phone, email, password, secret_word, role FROM users";

    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public User findById(long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();
            while(resultSet.next()) {
                user.setId(resultSet.getLong("id_user"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPhone(resultSet.getString("phone"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setSecret_word(resultSet.getString("secret_word"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
            }
            logger.info("findById method was invoked in UserDAO");
            return user.getId() != 0 ? user : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<User> findAll() {
        try(Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id_user"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPhone(resultSet.getString("phone"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setSecret_word(resultSet.getString("secret_word"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                userList.add(user);
            }
            logger.info("findAll method was invoked in UserDAO");
            return userList.size() != 0 ? userList : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }

}
