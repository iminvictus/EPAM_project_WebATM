package dao.users;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import models.Role;
import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {
    @InjectMocks
    private UserDAO dao;

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;


    List<User> expectedUserList;

    @Before
    public void init() throws SQLException {
        dao = new UserDAO(connection);


        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);


        expectedUserList = new ArrayList<>();
        expectedUserList.add(new User(1L, "Ivan", "Ivanov", Role.CLIENT));
    }

    @Test
    public void findById_givenUserFound_thenReturnUser() throws SQLException {
        // given
        when(connection.prepareStatement("SELECT id, name, surname, role FROM users WHERE id = ?")).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Ivan");
        when(resultSet.getString("surname")).thenReturn("Ivanov");
        when(resultSet.getString("role")).thenReturn(String.valueOf(Role.CLIENT));

        User expected = new User(1L, "Ivan", "Ivanov", Role.CLIENT);
        // when
        final User actual = dao.findById(1L);
        // then
        verify(preparedStatement, times(1)).setLong(1, 1L);
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findById_givenNoUserFound_thenReturnNull() throws SQLException {
        // given
        when(connection.prepareStatement("SELECT id, name, surname, role FROM users WHERE id = ?")).thenReturn(preparedStatement);
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

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dao.findById(1)
        );
        assertTrue(exception.getMessage().contains("sql"));
    }

    @Test
    public void findAll_AllUsersFound() throws SQLException {
        //given
        when(connection.createStatement()).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery("SELECT id, name, surname, role FROM users")).thenReturn(resultSet);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Ivan");
        when(resultSet.getString("surname")).thenReturn("Ivanov");
        when(resultSet.getString("role")).thenReturn(String.valueOf(Role.CLIENT));
        //when
        final List<User> actual = dao.findAll();
        //then
        verify(preparedStatement, atLeast(1))
                .executeQuery("SELECT id, name, surname, role FROM users");
        Assert.assertNotNull(actual);
        Assert.assertEquals(expectedUserList.get(0), actual.get(0));
    }

    @Test
    public void findAll_NoUsersFound_thenReturnNull() throws SQLException {
        //given
        when(connection.createStatement()).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery("SELECT id, name, surname, role FROM users")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        //when
        final List<User> actual = dao.findAll();
        //then
        verify(preparedStatement, atLeast(1))
                .executeQuery("SELECT id, name, surname, role FROM users");
        Assert.assertNull(actual);
    }

    @Test
    public void findAll_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dao.findAll()
        );
        assertTrue(exception.getMessage().contains("sql"));
    }

}