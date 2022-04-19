package controllers;

import dao.cards.CardDAO;
import dao.transactions.TransactionDAO;
import dao.users.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import models.Card;
import models.CardCurrency;
import models.Role;
import models.Transaction;
import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApplicationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferServletTestIT {
    private String path = "view/Transfer.jsp";

    @Mock
    private UserDAO userDAO;
    @Mock
    private CardDAO cardDAO;
    @Mock
    private TransactionDAO transactionDAO;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;

    @SneakyThrows
    @Test
    public void doGetTransferTest() {
        //given
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        ApplicationService applicationService = new ApplicationService(userDAO, transactionDAO, cardDAO);
        TransferServlet transferServlet = new TransferServlet(applicationService);

        Card cardCurrent = new Card(1L, new BigDecimal("1234567890123456"), new BigDecimal(10000), CardCurrency.RUR, new Date(12345), "4000", 1L);
        Card cardDest = new Card(2L, new BigDecimal("1234567890123457"), new BigDecimal(10000), CardCurrency.RUR, new Date(123666), "4001", 2L);
        User user = new User(1L, "Petr", "Petrov");
        BigDecimal amount = cardCurrent.getBalance().divide(new BigDecimal(10));

        when(request.getParameter("id")).thenReturn(String.valueOf(user.getId()));
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        when(request.getParameter("amount")).thenReturn(String.valueOf(amount));
        when(request.getParameter("dest_account")).thenReturn(String.valueOf(cardDest.getAccount()));
        when(userDAO.findById(user.getId())).thenReturn(user);
        when(cardDAO.findById(cardCurrent.getId())).thenReturn(cardCurrent);
        when(cardDAO.findByAccount(cardDest.getAccount())).thenReturn(cardDest);
        //when
        transferServlet.doGet(request, response);
        //then
        verify(userDAO, times(1)).findById(1);
        verify(request, times(1)).getParameter("id");
        verify(request, times(1)).setAttribute("user", user);
        verify(request, times(1)).getRequestDispatcher(path);
        Assert.assertEquals(user, applicationService.getUserById(1L));
        //when
        transferServlet.doPost(request, response);
        //then
        verify(cardDAO, times(1)).withdrawMoney(cardCurrent.getId(), amount);
        verify(cardDAO, times(1)).depositMoney(cardDest.getId(), amount);
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/service");
        Mockito.verify(transactionDAO, times(2)).save(captor.capture());

        List<Transaction> transactions = captor.getAllValues();
        List<Long> userIds = new ArrayList<>();
        List<String> typesList = new ArrayList<>();
        for (Transaction tr : transactions
        ) {
            userIds.add(tr.getUserid());
            typesList.add(tr.getType());
        }

        Assert.assertTrue(userIds.contains(cardCurrent.getUserid()));
        Assert.assertTrue(userIds.contains(cardDest.getUserid()));
        Assert.assertTrue(typesList.contains("Deposit"));
        Assert.assertTrue(typesList.contains("Withdraw"));
        Assert.assertTrue(transactions.size() == 2);

    }
}