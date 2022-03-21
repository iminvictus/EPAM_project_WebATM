package services;

import dao.users.UserDAO;
import models.User;
import utils.DatabaseConnectionManager;

import java.sql.SQLException;
import java.util.List;

public class ApplicationService {

    private final static IllegalArgumentException ZERO_OR_NEGATIVE = new IllegalArgumentException("value is zero or negative");

    private final UserDAO userDAO;

    public ApplicationService() {
        DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager("localhost", "postgres", "admin", "atm_service");
        try {
            userDAO = new UserDAO(databaseConnectionManager.getConnection());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = userDAO.findAll();
        return userList != null ? userList : List.of();
    }

    public User getUserById(long id) {
        if (id <= 0) {
            throw ZERO_OR_NEGATIVE;
        }
        return userDAO.findById(id);
    }

    public void destroy() {
        userDAO.closeConnection();
    }
}
