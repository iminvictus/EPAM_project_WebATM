package controllers;

import dao.cards.CardDAO;
import dao.transactions.TransactionDAO;
import dao.users.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

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
public class DepositServletIT {
    private static final String DEPOSIT_PATH = "view/Deposit.jsp";
    private DepositServlet servlet;

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

    @Before
    public void initTest() {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("amount")).thenReturn("111");
        when(request.getRequestDispatcher(DEPOSIT_PATH)).thenReturn(dispatcher);
        when(userDAO.findById(1L)).thenReturn(new User(1L, "Test1", "Test11"));
        servlet = new DepositServlet(new ApplicationService(userDAO, transactionDAO, cardDAO));
    }

    @Test
    public void doDepositTest() throws ServletException, IOException {
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        User testUser = new User(1L, "Test1", "Test11");
        servlet.doGet(request, response);

        verify(userDAO, times(1)).findById(1);
        verify(request, times(1)).getParameter("id");
        verify(request, times(1)).setAttribute("user", testUser);
        verify(request, times(1)).getRequestDispatcher(DEPOSIT_PATH);
        verify(dispatcher).forward(request, response);

        servlet.doPost(request, response);

        verify(cardDAO, times(1)).depositMoney(1, new BigDecimal(111));
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/service");
        Mockito.verify(transactionDAO).save(captor.capture());
        Assert.assertEquals(1L, captor.getValue().getUserid());
        Assert.assertEquals("Deposit", captor.getValue().getType());
        Assert.assertEquals(new BigDecimal(111), captor.getValue().getAmount());
    }
}