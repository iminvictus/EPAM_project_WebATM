package controllers;

import dao.cards.CardDAO;
import dao.transactions.TransactionDAO;
import dao.users.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import java.sql.Date;
import models.Card;
import models.CardCurrency;
import models.Transaction;
import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApplicationService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WithdrawServletIT {
    private static final String WITHDRAW_PATH = "view/Withdraw.jsp";

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
    @Mock
    HttpSession session;

    @Before
    public void initTest() {
        Card card = new Card(1L, new BigDecimal("1234567890123456"), new BigDecimal(10000), CardCurrency.RUR, new Date(12345), "4000", 1L);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("amount")).thenReturn("10");
        when(request.getRequestDispatcher(WITHDRAW_PATH)).thenReturn(dispatcher);
        when(userDAO.findById(1L)).thenReturn(new User(1L, "Ivan", "Ivanov"));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("approvedCard")).thenReturn(card);
    }

    @Test
    public void doWithdrawTest() throws ServletException, IOException {
        WithdrawServlet servlet = new WithdrawServlet(new ApplicationService(userDAO, transactionDAO, cardDAO));
        ArgumentCaptor <Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        User testUser = new User(1L, "Ivan", "Ivanov");
        servlet.doGet(request, response);

        verify(userDAO, times(1)).findById(1);
        verify(request, times(1)).getParameter("id");
        verify(request, times(1)).setAttribute("user", testUser);
        verify(request, times(1)).getRequestDispatcher(WITHDRAW_PATH);
        verify(dispatcher).forward(request, response);

        servlet.doPost(request, response);

        verify(cardDAO, times(1)).withdrawMoney(1, new BigDecimal(10));
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/service");
        Mockito.verify(transactionDAO).save(captor.capture());
        Assert.assertEquals(1L, captor.getValue().getUserid());
        Assert.assertEquals("Withdraw", captor.getValue().getType());
        Assert.assertEquals(new BigDecimal(10), captor.getValue().getAmount());
    }
}