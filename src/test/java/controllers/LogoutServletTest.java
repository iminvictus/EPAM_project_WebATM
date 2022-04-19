package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApplicationService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogoutServletTest {
    private String loginPage = "/login";


    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    HttpSession session;


    @InjectMocks
    private LogoutServlet logoutServlet;

    @SneakyThrows
    @Test
    public void doGetTest_givenInvalidateSession_thenSendToLoginPage() {
        //given
        when(request.getSession()).thenReturn(session);
        //when
        logoutServlet.doGet(request, response);
        //then
        verify(request, times(1)).getSession();
        verify(response, times(1)).sendRedirect(request.getContextPath() + loginPage);
    }

    @SneakyThrows
    @Test
    public void doPostTest_givenInvalidateSession_thenSendToLoginPage() {
        //given
        when(request.getSession()).thenReturn(session);
        //when
        logoutServlet.doPost(request, response);
        //then
        verify(request, times(1)).getSession();
        verify(response, times(1)).sendRedirect(request.getContextPath() + loginPage);
    }
}