package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import models.Card;
import models.CardCurrency;
import models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApplicationService;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WithdrawServletTest {
    @Mock
    private ApplicationService service;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    HttpSession session;

    @InjectMocks
    private WithdrawServlet servlet;

    @Test
    public void doGetWithdrawTest() throws ServletException, IOException {
        //given
        User user = new User(1L, "Ivan", "Ivanov");
        String path = "view/Withdraw.jsp";

        when(request.getParameter("id")).thenReturn("1");
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        when(service.getUserById(1)).thenReturn(user);
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        //when
        servlet.doGet(request, response);
        //then
        verify(service, atLeast(1)).getUserById(1);
        verify(request, atLeast(1)).setAttribute("user", user);
        verify(request, atLeast(1)).getRequestDispatcher(path);
        verify(dispatcher, atLeast(1)).forward(request, response);
    }

    @Test
    public void doPostWithdrawTest() throws IOException, ServletException {
        //given
        Card card = new Card(1L, new BigDecimal("1234567890123456"), new BigDecimal(10000), CardCurrency.RUR, new Date(12345), "4000", 1L);
        BigDecimal amount = card.getBalance().divide(new BigDecimal(10));
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("amount")).thenReturn("10");
        when(request.getContextPath()).thenReturn("test");
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("amount")).thenReturn(String.valueOf(amount));
        when(session.getAttribute("approvedCard")).thenReturn(card);

        //when
        servlet.doPost(request, response);
        //then
        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("amount");
        verify(service, atLeast(1)).withdrawMoney(1, amount);
        verify(response, atLeast(1)).sendRedirect("test/service");
    }
}