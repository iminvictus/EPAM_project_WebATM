package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import lombok.SneakyThrows;
import models.Card;
import models.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import services.ApplicationService;
import utils.CardUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {
    private String loginPage = "/view/LoginPage.jsp";

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
    @Mock
    Card card;

    @InjectMocks
    private LoginServlet loginServlet;

    @SneakyThrows
    @Test
    public void doGetTest() {
        //given
        when(request.getRequestDispatcher(loginPage)).thenReturn(dispatcher);
        //when
        loginServlet.doGet(request, response);
        //then
        verify(request, atLeast(1)).getRequestDispatcher(loginPage);
    }

    @SneakyThrows
    @Test
    public void doPostTest_givenAdminRole_thenAdminUI() {
        //given
        when(request.getParameter("account"))
                .thenReturn(String.valueOf(new BigDecimal("4276322513112251")));
        when(request.getParameter("pincode"))
                .thenReturn(String.valueOf(new BigDecimal(0001)));
        when(service.getCardByAccountAndPin(any(BigDecimal.class), any(String.class))).thenReturn(card);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(Role.ADMIN);
        //when
        try (MockedStatic<CardUtils> theMock = Mockito.mockStatic(CardUtils.class)) {
            theMock.when(() -> CardUtils.storeApprovedCard(session, card))
                    .thenAnswer((Answer<Void>) invocation -> null);
            loginServlet.doPost(request, response);
            theMock.verify(() -> CardUtils.storeApprovedCard(session, card), atLeast(1));

        }
        //then
        verify(response).sendRedirect(request.getContextPath() + "/view/HomeAdmin.jsp");
        verify(request, times(1)).getParameter("account");
        verify(request, times(1)).getParameter("pincode");
        verify(service, times(1)).getCardByAccountAndPin(any(BigDecimal.class), any(String.class));
        verify(request, times(2)).getSession();

    }

    @SneakyThrows
    @Test
    public void doPostTest_givenClientRole_thenClientUI() {
        //given
        when(request.getParameter("account"))
                .thenReturn(String.valueOf(new BigDecimal("4276322513112251")));
        when(request.getParameter("pincode"))
                .thenReturn(String.valueOf(new BigDecimal(0001)));
        when(service.getCardByAccountAndPin(any(BigDecimal.class), any(String.class))).thenReturn(card);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(Role.CLIENT);
        //when
        try (MockedStatic<CardUtils> theMock = Mockito.mockStatic(CardUtils.class)) {
            theMock.when(() -> CardUtils.storeApprovedCard(session, card))
                    .thenAnswer((Answer<Void>) invocation -> null);
            loginServlet.doPost(request, response);
            theMock.verify(() -> CardUtils.storeApprovedCard(session, card), atLeast(1));
            verify(response).sendRedirect(request.getContextPath() + "/view/Home.jsp");
        }
        //then
        verify(request, times(1)).getParameter("account");
        verify(request, times(1)).getParameter("pincode");
        verify(service, times(1)).getCardByAccountAndPin(any(BigDecimal.class), any(String.class));
        verify(request, times(2)).getSession();

    }

    @SneakyThrows
    @Test
    public void doPostTest_givenNullCard_thenLoginPage() {
        //given
        when(request.getParameter("account"))
                .thenReturn(String.valueOf(new BigDecimal("4276322513112251")));
        when(request.getParameter("pincode"))
                .thenReturn(String.valueOf(new BigDecimal(0001)));
        when(service.getCardByAccountAndPin(any(BigDecimal.class), any(String.class))).thenReturn(null);
        when(request.getRequestDispatcher(loginPage)).thenReturn(dispatcher);
        //when
        loginServlet.doPost(request, response);
        //then
        verify(request, times(1)).getRequestDispatcher(loginPage);
        verify(request, times(1)).getParameter("account");
        verify(request, times(1)).getParameter("pincode");
        verify(service, times(1)).getCardByAccountAndPin(any(BigDecimal.class), any(String.class));
        verify(request, times(1)).getRequestDispatcher(loginPage);
    }
}