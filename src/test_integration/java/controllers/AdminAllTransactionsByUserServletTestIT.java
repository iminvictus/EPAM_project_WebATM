package controllers;

import controllers.AdminAllTransactionsByUserServlet;
import dao.transactions.TransactionDAO;
import dao.users.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApplicationService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminAllTransactionsByUserServletTestIT {

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
    @InjectMocks
    private AdminAllTransactionsByUserServlet servlet;

    private static final String PATH = "view/HistoryByUserAdmin.jsp";
    private ApplicationService service;
    private List<Transaction> list;

    @Before
    public void initTest() {
        list = List.of(
                new Transaction(2, ZonedDateTime.now(), new BigDecimal(1000), "Withdraw", "CLIENT", "Done", 2),
                new Transaction(1, ZonedDateTime.now(), new BigDecimal(1000), "Deposit", "CLIENT", "Done", 1)
        );
        service = new ApplicationService(userDAO, transactionDAO);
        servlet = new AdminAllTransactionsByUserServlet(service);
    }

    @Test
    public void testedDoPost_givenUserId_thenSetTransactionListAndForwardToJSPPage () throws ServletException, IOException {
        // given
        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);
        when(request.getParameter("id")).thenReturn("1");
        when(transactionDAO.findByCardId(1)).thenReturn(list);

        //when
        servlet.doPost(request, response);

        //then
        verify(request, times(1)).getRequestDispatcher(PATH);
        verify(dispatcher).forward(request, response);
        verify(request, times(1)).setAttribute("transactions", list);
        verify(request, times(2)).getParameter("id");
        verify(transactionDAO, times(1)).findByCardId(1);
    }
}