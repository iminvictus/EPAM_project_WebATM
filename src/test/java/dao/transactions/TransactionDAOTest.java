package dao.transactions;

import models.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionDAOTest {
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Connection connection;
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    TransactionDAO dao;

    Transaction expected;
    List<Transaction> expectedList;
    Timestamp timestamp;

    @Before
    public void init() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(preparedStatement.executeQuery("SELECT id_transaction, date, amount, type, initiated_by, state, id_card FROM transactions"))
                .thenReturn(resultSet);
        when(connection.prepareStatement("SELECT id_transaction, date, amount, type, initiated_by, state, id_card FROM transactions WHERE id_transaction = ?"))
                .thenReturn(preparedStatement);
        when(connection.prepareStatement("SELECT id_transaction, date, amount, type, initiated_by, state, id_card FROM transactions WHERE id_card = ?"))
                .thenReturn(preparedStatement);
        when(connection.prepareStatement("INSERT INTO transactions (date, amount, type, initiated_by, state, id_card) VALUES (?, ?, ?, ?, ?, ?)"))
                .thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(preparedStatement);

        expectedList = new ArrayList<>();
        timestamp = new Timestamp(12345);
        expected = new Transaction(1, ZonedDateTime.of(timestamp.toLocalDateTime(), ZoneId.systemDefault()), new BigDecimal(111), "deposit", "CLIENT", "Done", 1L);
        expected.setId(1L);
        expectedList.add(expected);
    }

    @Test
    public void findById_givenTransactionFound_thenReturnTransaction() throws SQLException {
        //given
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id_transaction")).thenReturn(1L);
        when(resultSet.getTimestamp("date")).thenReturn(timestamp);
        when(resultSet.getBigDecimal("amount")).thenReturn(new BigDecimal(111));
        when(resultSet.getString("type")).thenReturn("deposit");
        when(resultSet.getString("initiated_by")).thenReturn("CLIENT");
        when(resultSet.getString("state")).thenReturn("Done");
        when(resultSet.getLong("id_card")).thenReturn(1L);
        //when
        final Transaction actual = dao.findById(1);
        //then
        verify(preparedStatement, times(1)).setLong(1, 1L);
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findById_givenNoTransactionFound_thenReturnNull() throws SQLException {
        //given
        when(resultSet.next()).thenReturn(false);
        //when
        final Transaction actual = dao.findById(2);
        //then
        verify(preparedStatement, times(1)).setLong(1, 2L);
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertNull(actual);
    }

    @Test
    public void findById_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {
        //given
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        //when
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dao.findById(1));
        //then
        Assert.assertTrue(exception.getMessage().contains("sql"));
    }

    @Test
    public void findByUserId_givenUserFound_thenReturnTransaction() throws SQLException {
        //given
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id_transaction")).thenReturn(1L);
        when(resultSet.getTimestamp("date")).thenReturn(timestamp);
        when(resultSet.getBigDecimal("amount")).thenReturn(new BigDecimal(111));
        when(resultSet.getString("type")).thenReturn("deposit");
        when(resultSet.getString("initiated_by")).thenReturn("CLIENT");
        when(resultSet.getString("state")).thenReturn("Done");
        when(resultSet.getLong("id_card")).thenReturn(1L);
        //when
        final List<Transaction> actual = dao.findByCardId(1);
        //then
        verify(preparedStatement, times(1)).setLong(1, 1L);
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertNotNull(actual);
        Assert.assertEquals(expectedList.get(0), actual.get(0));
    }

    @Test
    public void findByUserId_givenNoTransactionFound_thenReturnNull() throws SQLException {
        //given
        when(resultSet.next()).thenReturn(false);
        //when
        final Transaction actual = dao.findById(2);
        //then
        verify(preparedStatement, times(1)).setLong(1, 2L);
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertNull(actual);
    }

    @Test
    public void findByUserId_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {
        //given
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        //when
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dao.findByCardId(1));
        //then
        Assert.assertTrue(exception.getMessage().contains("sql"));
    }

    @Test
    public void findAll_AllTransactionsFound() throws SQLException {
        //given
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id_transaction")).thenReturn(1L);
        when(resultSet.getTimestamp("date")).thenReturn(timestamp);
        when(resultSet.getBigDecimal("amount")).thenReturn(new BigDecimal(111));
        when(resultSet.getString("type")).thenReturn("deposit");
        when(resultSet.getString("initiated_by")).thenReturn("CLIENT");
        when(resultSet.getString("state")).thenReturn("Done");
        when(resultSet.getLong("id_card")).thenReturn(1L);
        //when
        final List<Transaction> actual = dao.findAll();
        //then
        verify(preparedStatement, atLeast(1))
                .executeQuery("SELECT id_transaction, date, amount, type, initiated_by, state, id_card FROM transactions");
        Assert.assertNotNull(actual);
        Assert.assertEquals(expectedList.get(0), actual.get(0));
    }

    @Test
    public void findAll_NoTransactionsFound_thenReturnNull() throws SQLException {
        //given
        when(resultSet.next()).thenReturn(false);
        //when
        final List<Transaction> actual = dao.findAll();
        //then
        verify(preparedStatement, atLeast(1))
                .executeQuery("SELECT id_transaction, date, amount, type, initiated_by, state, id_card FROM transactions");
        Assert.assertNull(actual);
    }

    @Test
    public void findAll_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {
        //given
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        //when
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dao.findAll());
        //then
        Assert.assertTrue(exception.getMessage().contains("sql"));
    }

    @Test
    public void save_TransactionSaved() throws SQLException {
        //when
        dao.save(expected);
        //then
        verify(preparedStatement, times(1))
                .setTimestamp(1, Timestamp.valueOf(ZonedDateTime.of(timestamp.toLocalDateTime(),
                        ZoneId.systemDefault()).toLocalDateTime()));
        verify(preparedStatement, times(1)).setBigDecimal(2, new BigDecimal(111));
        verify(preparedStatement, times(1)).setString(3, "deposit");
        verify(preparedStatement, times(1)).setString(4, "CLIENT");
        verify(preparedStatement, times(1)).setString(5, "Done");
        verify(preparedStatement, times(1)).setLong(6, 1L);
        verify(preparedStatement, times(1)).execute();
    }

    @Test
    public void save_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {
        //given
        SQLException ex = new SQLException();
        //when
        doThrow(ex).when(preparedStatement).setString(3, "deposit");
        //then
        assertThrows(RuntimeException.class,
                () -> dao.save(expected));
    }
}