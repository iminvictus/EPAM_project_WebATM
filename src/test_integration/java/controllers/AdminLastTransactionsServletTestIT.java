package controllers;

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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminLastTransactionsServletTestIT {

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
    @Mock
    private ApplicationService service;
    @InjectMocks
    private AdminLastTransactionsServlet servlet;

    private static final String PATH = "view/HistoryAdmin.jsp";
    private List<Transaction> list;

    @Before
    public void initTest() {
        list = List.of(
                new Transaction(2, ZonedDateTime.now(), new BigDecimal(1000), "Withdraw", "CLIENT", "Done", 2),
                new Transaction(1, ZonedDateTime.now(), new BigDecimal(1000), "Deposit", "CLIENT", "Done", 1)
        );
        service = new ApplicationService(userDAO, transactionDAO);
        servlet = new AdminLastTransactionsServlet(service);
    }


    @Test
    public void testedDoGet_givenGETRequest_thenReturnLast20Transactions() throws ServletException, IOException {
        //given
        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);
        when(transactionDAO.findAll()).thenReturn(list);

        //when
        servlet.doGet(request, response);

        //then
        verify(transactionDAO, times(1)).findAll();
        verify(request, times(1)).setAttribute("transactions", list);
        verify(request, times(1)).getRequestDispatcher(PATH);
        verify(dispatcher).forward(request, response);
    }

}