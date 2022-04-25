package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Role;
import models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApplicationService;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeast;

@RunWith(MockitoJUnitRunner.class)
public class DepositServletTest {

    @Mock
    private ApplicationService service;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;

    @InjectMocks
    private DepositServlet servlet;

    @Test
    public void doGetDepositTest() throws ServletException, IOException {
        //given
        User user = new User(1L, "Test1", "Test11", "79998887755", "adf@mail.ru", "qwerty1234", "secret", Role.CLIENT);
        String path = "view/Deposit.jsp";

        when(request.getParameter("id")).thenReturn("1");
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
    public void doPostDepositTest() throws IOException, ServletException {
        //given
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("amount")).thenReturn("111");
        when(request.getContextPath()).thenReturn("test");
        when(service.getUserById(1)).thenReturn(new User(1L, "Test1", "Test11", "79998887755", "adf@mail.ru", "qwerty1234", "secret", Role.CLIENT));
        //when
        servlet.doPost(request, response);
        //then
        verify(request, atLeast(1)).getParameter("id");
        verify(request, atLeast(1)).getParameter("amount");
        verify(service, atLeast(1)).depositMoney(1, new BigDecimal(111), "CLIENT");
        verify(response, atLeast(1)).sendRedirect("test/service");
    }
}