package services;

import dao.cards.CardDAO;
import dao.transactions.TransactionDAO;
import dao.users.UserDAO;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApplicationServiceTest {

    private static List<Transaction> histList;
    private static List<Transaction> histListId;
    private static List<User> usersList;
    private static List<Card> cardsList;
    private Card card;
    private UserDAO userDAO;
    private TransactionDAO transactionDAO;
    private CardDAO cardDAO;
    private ApplicationService applicationService;

    @BeforeClass
    public static void createTestLists() {
        //transactions' lists
        histList = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, "Deposit", new BigDecimal(1000), ZonedDateTime.now());
        transaction1.setId(1);
        Transaction transaction2 = new Transaction(2, "Withdraw", new BigDecimal(2000), ZonedDateTime.now());
        transaction2.setId(2);
        Transaction transaction3 = new Transaction(1, "Deposit", new BigDecimal(500), ZonedDateTime.now());
        transaction3.setId(3);
        Transaction transaction4 = new Transaction(2, "Withdraw", new BigDecimal(100), ZonedDateTime.now());
        transaction4.setId(4);
        histList.add(transaction1);
        histList.add(transaction2);
        histList.add(transaction3);
        histList.add(transaction4);
        histListId = new ArrayList<>();
        for (Transaction tr : histList) {
            if (tr.getUserid() == 1) histListId.add(tr);
        }
        //users' list
        usersList = new ArrayList<>();
        User user1 = new User(1L,"Mike", "Mayers");

        User user2 = new User(2L,"Spike", "Spayers");

        User user3 = new User(3L,"John", "Smith");

        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);

        //cards' list
        cardsList = new ArrayList<>();
        Card card1 = new Card(1L, new BigDecimal("1234567890123456"), new BigDecimal(10000), CardCurrency.RUR, new Date(11111), "4000", 1L, CardStatus.OPEN);
        Card card2 = new Card(2L, new BigDecimal("1234567890123457"), new BigDecimal(15000), CardCurrency.RUR, new Date(11111), "4000", 2L, CardStatus.OPEN);
        Card card3 = new Card(3L, new BigDecimal("1234567890123458"), new BigDecimal(20000), CardCurrency.RUR, new Date(11111), "4000", 3L, CardStatus.OPEN);

        cardsList.add(card1);
        cardsList.add(card2);
        cardsList.add(card3);

    }

    @Before
    public void init() {
        transactionDAO = mock(TransactionDAO.class);
        userDAO = mock(UserDAO.class);
        cardDAO = mock(CardDAO.class);
        applicationService = new ApplicationService(userDAO, transactionDAO, cardDAO);
    }

    @Test
    public void getAllUsers() {
        //given
        when(userDAO.findAll()).thenReturn(usersList);
        //when
        ArrayList<User> listGot = (ArrayList<User>) applicationService.getAllUsers();
        //then
        Assert.assertTrue(listGot.size() > 0);
        Assert.assertEquals(listGot, usersList);
    }

    @Test
    public void getUserById() {
        //given
        User user = usersList.get(0);
        long id = usersList.get(0).getId();
        when(userDAO.findById(id)).thenReturn(user);
        //when
        User userGot = applicationService.getUserById(id);
        //then
        Assert.assertNotNull(userGot);
        Assert.assertEquals(userGot, user);
    }

    @Test
    public void getAllTransactions() {
        //given
        TransactionDAO transactionDAO = mock(TransactionDAO.class);
        UserDAO userDAO = mock(UserDAO.class);
        CardDAO cardDAO = mock(CardDAO.class);
        applicationService = new ApplicationService(userDAO, transactionDAO, cardDAO);
        when(transactionDAO.findAll()).thenReturn(histList);
        //when
        ArrayList<Transaction> listGot = (ArrayList<Transaction>) applicationService.getAllTransactions();
        //then
        Assert.assertTrue(listGot.size() > 0);
        Assert.assertEquals(listGot, histList);
    }

    @Test
    public void getTransactionsByUserId() {
        //given
        TransactionDAO transactionDAO = mock(TransactionDAO.class);
        UserDAO userDAO = mock(UserDAO.class);
        applicationService = new ApplicationService(userDAO, transactionDAO, cardDAO);
        long id = 1;
        when(transactionDAO.findByUserId(id)).thenReturn(histListId);
        //when
        ArrayList<Transaction> listGot = (ArrayList<Transaction>) applicationService.getTransactionsByUserId(id);
        //then
        Assert.assertTrue(listGot.size() > 0);
        Assert.assertEquals(listGot, histListId);
    }

    @Test
    public void getTransactionById() {
        //given
        long id = histList.get(0).getId();
        Transaction transaction = histList.get(0);
        when(transactionDAO.findById(id)).thenReturn(transaction);
        //when
        Transaction transactionGot = applicationService.getTransactionById(id);
        //then
        Assert.assertNotNull(transactionGot);
        Assert.assertEquals(transaction, transactionGot);
    }

    @Test
    public void depositMoneyTest() {
        //given
        doAnswer(invocation -> {
            long id = invocation.getArgument(0);
            BigDecimal amount = invocation.getArgument(1);

            Assert.assertEquals(1L, id);
            Assert.assertEquals(new BigDecimal(100), amount);
            for (Card cardNew: cardsList) {
                if (cardNew.getId() == id) {
                    cardNew.setBalance(cardNew.getBalance().add(amount));
                    card = cardNew;
                    break;
                }
            }
            return card;
        }).when(cardDAO).depositMoney(any(Long.class), any(BigDecimal.class));
        //when
        applicationService.depositMoney(1L, new BigDecimal(100));
        //then
        verify(cardDAO, times(1)).depositMoney(1L, new BigDecimal(100));
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getId()==1L);
    }

    @Test
    public void withdrawMoney() {
        //given
        doAnswer(invocation -> {
            long id = invocation.getArgument(0);
            BigDecimal amount = invocation.getArgument(1);

            Assert.assertEquals(1L, id);
            Assert.assertEquals(new BigDecimal(100), amount);
            for (Card cardNew: cardsList) {
                if (cardNew.getId() == id) {
                    cardNew.setBalance(cardNew.getBalance().subtract(amount));
                    card = cardNew;
                    break;
                }
            }
            return card;
        }).when(cardDAO).withdrawMoney(any(Long.class), any(BigDecimal.class));
        //when
        applicationService.withdrawMoney(1L, new BigDecimal(100));
        //then
        verify(cardDAO, times(1)).withdrawMoney(1L, new BigDecimal(100));
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getId()==1L);
    }
}