package controllers;

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
public class AdminLastTransactionsServletTest {
    @Mock
    private ApplicationService service;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
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
    }

    @Test
    public void testedDoGet_givenGetRequest_thenReturnLast20Transactions() throws ServletException, IOException {
        //given
        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);
        when(service.getAllTransactions()).thenReturn(list);

        //when
        servlet.doGet(request, response);

        //then
        verify(request, atLeast(1)).setAttribute("transactions", list);
        verify(request, atLeast(1)).getRequestDispatcher(PATH);
        verify(dispatcher, atLeast(1)).forward(request, response);
    }
}