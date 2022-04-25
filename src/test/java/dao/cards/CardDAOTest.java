package dao.cards;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import lombok.SneakyThrows;
import models.Card;
import models.CardCurrency;
import models.CardStatus;
import models.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardDAOTest {

    @InjectMocks
    private CardDAO cardDAO;

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    List<Card> expectedCardList;

    @Before
    public void init() throws SQLException {

        cardDAO = new CardDAO(connection);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        expectedCardList = new ArrayList<>();
        expectedCardList.add(new Card(1L, new BigDecimal("1234567890123456"), new BigDecimal(10000), CardCurrency.RUR, new Date(12345), "4000", CardStatus.OPEN, 1L));
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id_card")).thenReturn(1L);
        when(resultSet.getBigDecimal("account")).thenReturn(new BigDecimal("1234567890123456"));
        when(resultSet.getBigDecimal("balance")).thenReturn(new BigDecimal(10000));
        when((resultSet.getString("currency"))).thenReturn(String.valueOf(CardCurrency.RUR));
        when(resultSet.getDate("expiration_date")).thenReturn(new Date(12345));
        when(resultSet.getString("pincode")).thenReturn("4000");
        when(resultSet.getString("status")).thenReturn("open");
        when(resultSet.getLong("id_user")).thenReturn(1L);
    }

    @SneakyThrows
    @Test
    public void findById_givenId_thenGetCard() {
        // given
        when(connection.prepareStatement("SELECT id_card, account, balance, currency, expiration_date, pincode, status, id_user FROM cards WHERE id_card = ?")).thenReturn(preparedStatement);

        Card expected = new Card(1L, new BigDecimal("1234567890123456"), new BigDecimal(10000), CardCurrency.RUR, new Date(12345), "4000", CardStatus.OPEN, 1L);
        // when
        final Card actual = cardDAO.findById(1L);
        // then
        verify(preparedStatement, times(1)).setLong(1, 1L);
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findById_givenNoCardFound_thenGetNull() throws SQLException {
        //given
        when(connection.prepareStatement("SELECT id_card, account, balance, currency, expiration_date, pincode, status, id_user FROM cards WHERE id_card = ?")).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(false);
        //when
        final Card actual = cardDAO.findById(2);
        //then
        verify(preparedStatement, times(1)).setLong(1, 2L);
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertNull(actual);
    }

    @Test
    public void findById_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {

        //then
        Assert.assertThrows(RuntimeException.class,
                () -> cardDAO.findById(1));
    }

    @SneakyThrows
    @Test
    public void findAll_givenCards_thenGetAllCards() {
        // given
        when(connection.prepareStatement("SELECT id_card, account, balance, currency, expiration_date, pincode, status, id_user FROM cards")).thenReturn(preparedStatement);
        //when
        final List<Card> actualList = cardDAO.findAll();
        // then
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertNotNull(actualList);
        Assert.assertEquals(actualList, expectedCardList);
    }

    @Test
    public void findAll_NoCardsFound_thenReturnNull() throws SQLException {
        // given
        when(connection.prepareStatement("SELECT id_card, account, balance, currency, expiration_date, pincode, status, id_user FROM cards")).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(false);
        //when
        final List<Card> actual = cardDAO.findAll();
        //then
        verify(preparedStatement, atLeast(1))
                .executeQuery();
        Assert.assertNull(actual);
    }

    @Test
    public void findAll_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {
        //then
        Assert.assertThrows(RuntimeException.class,
                () -> cardDAO.findAll());
    }

    @SneakyThrows
    @Test
    public void findByUserId_givenUserId_thenGetCards() {
        // given
        when(connection.prepareStatement("SELECT id_card, account, balance, currency, expiration_date, pincode, status, id_user FROM cards WHERE id_user = ?")).thenReturn(preparedStatement);
        //when
        final List<Card> actualList = cardDAO.findByUserId(1L);
        // then
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertNotNull(actualList);
        Assert.assertEquals(actualList, expectedCardList);
    }

    @SneakyThrows
    @Test
    public void findUserId_givenPinAndAccount_thenGetUserId() {
        //given
        when(connection.prepareStatement("SELECT id_user FROM cards WHERE account = ? AND pincode = ?")).thenReturn(preparedStatement);
        //when
        final long user_id = cardDAO.findUserId(new BigDecimal("1234567890123456"), "4000");
        //then
        assertTrue(user_id == 1L);
    }

    @Test
    public void findByUserId_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {

        //then
        Assert.assertThrows(RuntimeException.class,
                () -> cardDAO.findByUserId(1L));
    }

    @SneakyThrows
    @Test
    public void findCardByAccAndPin_givenAccountAndPin_thenGetCard() {
        //given
        when(connection.prepareStatement("SELECT id_card, account, balance, currency, expiration_date, pincode, status, id_user FROM cards WHERE account = ? AND pincode = ?")).thenReturn(preparedStatement);

        Card expected = new Card(1L, new BigDecimal("1234567890123456"), new BigDecimal(10000), CardCurrency.RUR, new Date(12345), "4000", CardStatus.OPEN, 1L);
        //when
        final Card actual = cardDAO.findCardByAccAndPin(new BigDecimal("1234567890123456"), "4000");
        // then
        verify(preparedStatement, times(1)).setBigDecimal(1, new BigDecimal("1234567890123456"));
        verify(preparedStatement, times(1)).setString(2, "4000");
        verify(preparedStatement, times(1)).executeQuery();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findCardByAccAndPin_givenSqlException_thenLogAndRethrowRuntimeException() throws SQLException {

        //then
        Assert.assertThrows(RuntimeException.class,
                () -> cardDAO.findCardByAccAndPin(new BigDecimal("1234567890123456"), "4000"));
    }

    @SneakyThrows
    @Test
    public void withdrawMoney_givenAmount_changedAccountAmount() {
        //given
        when(connection.prepareStatement("SELECT id_card, account, balance, currency, expiration_date, pincode, status, id_user FROM cards WHERE id_card = ?")).thenReturn(preparedStatement);
        when(connection.prepareStatement("UPDATE cards SET balance = ? WHERE id_card = ?")).thenReturn(preparedStatement);
        // when
        cardDAO.withdrawMoney(1L, new BigDecimal(5));
        //then
        verify(preparedStatement, times(1)).setLong(1, 1L);
        verify(preparedStatement, times(1)).setBigDecimal(1, new BigDecimal(9995));
        verify(preparedStatement, times(1)).execute();
    }

    @SneakyThrows
    @Test
    public void withdrawMoney_givenNegativeAmount_thenLogAndRethrowRuntimeException() {

        //when
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cardDAO.withdrawMoney(1, BigDecimal.valueOf(-5))
        );
        //then
        assertTrue(exception.getMessage().contains("sql"));
    }

    @SneakyThrows
    @Test
    public void withdrawMoney_givenSqlException_thenLogAndRethrowRuntimeException() {
        //given
        SQLException ex = new SQLException();
        //when
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cardDAO.withdrawMoney(1, new BigDecimal(10))
        );
        //then
        assertTrue(exception.getMessage().contains("sql"));
    }
}