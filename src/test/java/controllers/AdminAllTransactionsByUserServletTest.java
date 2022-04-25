package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Transaction;
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
public class AdminAllTransactionsByUserServletTest {
    @Mock
    private ApplicationService service;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;

    @InjectMocks
    private AdminAllTransactionsByUserServlet servlet;

    private static final String PATH = "view/HistoryByUserAdmin.jsp";

    @Test
    public void testedDoGet_givenGETRequest_thenForwardToJSPPage() throws ServletException, IOException {
        //given
        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);

        //when
        servlet.doGet(request, response);

        //then
        verify(request, atLeast(1)).getRequestDispatcher(PATH);
        verify(dispatcher, atLeast(1)).forward(request, response);
    }

    @Test
    public void testedDoPost_givenUserId_thenSetTransactionListAndForwardToJSPPage() throws IOException, ServletException {
        //given
        when(request.getParameter("id")).thenReturn("1");
        when(service.getTransactionsByCardId(1)).thenReturn(List.of(
                new Transaction(1, ZonedDateTime.now(), new BigDecimal(1000), "Deposit", "CLIENT", "Done", 1),
                new Transaction(2, ZonedDateTime.now(), new BigDecimal(1000), "Withdraw", "CLIENT", "Done", 2)
        ));
        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);

        //when
        servlet.doPost(request, response);

        //then
        verify(request, atLeast(2)).getParameter("id");
        verify(service, atLeast(1)).getTransactionsByCardId(1);
        verify(request, atLeast(1)).getRequestDispatcher(PATH);
        verify(dispatcher, atLeast(1)).forward(request, response);
    }


    @Test
    public void testedDoPost_givenNullId_thenSetTransactionListAndForwardToJSPPage() throws IOException, ServletException {
        when(request.getParameter("id")).thenReturn(null);
        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getRequestDispatcher(PATH);
        verify(dispatcher, atLeast(1)).forward(request, response);
    }
}