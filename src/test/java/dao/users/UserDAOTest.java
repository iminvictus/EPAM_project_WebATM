package dao.users;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UserDAOTest {
    @InjectMocks
    private UserDAO dao;

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private User user;

    List<User> expectedUserList;

    @Before
    public void init() throws SQLException {
        preparedStatement = mock(PreparedStatement.class);
        connection = mock(Connection.class);
        resultSet = mock(ResultSet.class);
        dao = new UserDAO(connection);
        user = mock(User.class);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement("UPDATE users SET balance = ? WHERE id = ?")).thenReturn(preparedStatement);
        when(connection.prepareStatement("SELECT id, name, surname, balance FROM users WHERE id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery("SELECT id, name, surname, balance FROM users")).thenReturn(resultSet);
        when(connection.createStatement()).thenReturn(preparedStatement);

        expectedUserList = new ArrayList<>();
        expectedUserList.add(new User(1L, "Ivan", "Ivanov", BigDecimal.TEN));
    }

    @Test
    public void findById_givenUserFound_thenReturnUser() throws SQLException {
        // given
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Ivan");
        when(resultSet.getString("surname")).thenReturn("Ivanov");
        when(resultSet.getBigDecimal("balance")).thenReturn(BigDecimal.TEN);

        User expected = new User(1L, "Ivan", "Ivanov", BigDecimal.TEN);
        // when
        final User actual = dao.findById(1);
        // then
        verify(preparedStatement, times(1)).setLong(1, 1L);
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findById_givenNoUserFound_thenReturnNull() throws SQLException {
        // given
        when(resultSet.next()).thenReturn(false);
        // when
        final User actual = dao.findById(2);
        // then
        verify(preparedStatement, times(1)).setLong(1, 2L);
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertNull(actual);
    }

    @Test
    public void findById_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {
        SQLException ex = new SQLException();
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenThrow(ex);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dao.findById(1)
        );
        assertTrue(exception.getMessage().contains("sql"));
    }

    @Test
    public void findAll_AllUsersFound() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Ivan");
        when(resultSet.getString("surname")).thenReturn("Ivanov");
        when(resultSet.getBigDecimal("balance")).thenReturn(BigDecimal.TEN);

        final List<User> actual = dao.findAll();

        verify(preparedStatement, atLeast(1))
                .executeQuery("SELECT id, name, surname, balance FROM users");
        Assert.assertNotNull(actual);
        Assert.assertEquals(expectedUserList.get(0), actual.get(0));
    }

    @Test
    public void findAll_NoUsersFound_thenReturnNull() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        final List<User> actual = dao.findAll();

        verify(preparedStatement, atLeast(1))
                .executeQuery("SELECT id, name, surname, balance FROM users");
        Assert.assertNull(actual);
    }

    @Test
    public void findAll_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {
        SQLException ex = new SQLException();
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenThrow(ex);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dao.findAll()
        );
        assertTrue(exception.getMessage().contains("sql"));
    }

    @Test
    public void withdrawMoney_givenAmount_changedAccountAmount() throws SQLException {
        // given
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Ivan");
        when(resultSet.getString("surname")).thenReturn("Ivanov");
        when(resultSet.getBigDecimal("balance")).thenReturn(BigDecimal.TEN);
        when(user.getBalance()).thenReturn(BigDecimal.TEN);
        // when
        dao.withdrawMoney(1, BigDecimal.valueOf(5));
        //then
        verify(preparedStatement, times(1)).setBigDecimal(1, BigDecimal.valueOf(5));
        verify(preparedStatement, times(1)).setLong(2, 1L);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void withdrawMoney_givenNegativeAmount_thenLogAndRethrowRuntimeException() throws SQLException {
        // should run correctly when withdrawMoney method will be changed in UserDAO, exception is expected
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Ivan");
        when(resultSet.getString("surname")).thenReturn("Ivanov");
        when(resultSet.getBigDecimal("balance")).thenReturn(BigDecimal.TEN);
        when(user.getBalance()).thenReturn(BigDecimal.TEN);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dao.withdrawMoney(1, BigDecimal.valueOf(-5))
        );
        assertTrue(exception.getMessage().contains("sql"));
    }

    @Test
    public void withdrawMoney_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {
        SQLException ex = new SQLException();
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenThrow(ex);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dao.withdrawMoney(1, new BigDecimal(10))
        );
        assertTrue(exception.getMessage().contains("sql"));
    }
}