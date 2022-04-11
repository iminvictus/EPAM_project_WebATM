package controllers;

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
public class WithdrawServletIT {
    private static final String WITHDRAW_PATH = "view/withdraw.jsp";

    @Mock
    private UserDAO userDAO;
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
        when(request.getParameter("id_withdrawAmount")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("amount")).thenReturn("10");
        when(request.getRequestDispatcher(WITHDRAW_PATH)).thenReturn(dispatcher);
        when(userDAO.findById(1L)).thenReturn(new User(1, "Ivan", "Ivanov", new BigDecimal(100)));
    }

    @Test
    public void doWithdrawTest() throws ServletException, IOException {
        WithdrawServlet servlet = new WithdrawServlet(new ApplicationService(userDAO, transactionDAO));
        ArgumentCaptor <Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        User testUser = new User(1, "Ivan", "Ivanov", new BigDecimal(100));
        servlet.doGet(request, response);

        verify(userDAO, times(1)).findById(1);
        verify(request, times(1)).getParameter("id_withdrawAmount");
        verify(request, times(1)).setAttribute("user", testUser);
        verify(request, times(1)).getRequestDispatcher(WITHDRAW_PATH);
        verify(dispatcher).forward(request, response);

        servlet.doPost(request, response);

        verify(userDAO, times(1)).withdrawMoney(1, new BigDecimal(10));
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/service");
        Mockito.verify(transactionDAO).save(captor.capture());
        Assert.assertEquals(1L, captor.getValue().getUserid());
        Assert.assertEquals("Withdraw", captor.getValue().getType());
        Assert.assertEquals(new BigDecimal(10), captor.getValue().getAmount());
    }
}