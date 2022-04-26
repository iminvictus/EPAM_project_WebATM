package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Card;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import utils.CardUtils;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class ChangePinServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private Card card;
    @Mock
    private CardUtils cardUtils;

    @InjectMocks
    private ChangePinServlet changePinServlet;

    private static final String PATH = "view/ChangePin.jsp";

    @Test
    public void doPostChangePinServlet_givenNullCard_thenRedirectToLoginPage() throws ServletException, IOException {
        //given
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("approvedCard")).thenReturn(null);

        //when
        changePinServlet.doPost(request, response);

        //then
        verify(request, times(1)).setAttribute("errorMessage", "You should be authorized");
        verify(response, times(1)).sendRedirect("/login");
    }

    @Test
    public void doPostChangePinServlet_givenValidCardAndNewPin_thenRedirectAndSetNewPassword() throws ServletException, IOException {
        //given
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("approvedCard")).thenReturn(card);
        when(request.getParameter("newPin")).thenReturn("4000");

        //when
        try (MockedStatic<CardUtils> utilities = Mockito.mockStatic(CardUtils.class)) {
            utilities.when(() -> CardUtils.convertToMD5("4000"))
                    .thenReturn("5000");
            changePinServlet.doPost(request, response);
            //then
            verify(response, times(1)).sendRedirect("/logout");
            verify(card, times(1)).setPincode("5000");
        }
    }
}