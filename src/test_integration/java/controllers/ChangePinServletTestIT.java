package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Card;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import utils.CardUtils;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ChangePinServletTestIT {

    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;

    private static final String PATH = "view/ChangePin.jsp";
    private ChangePinServlet servlet;
    private Card card;

    @Before
    public void initTest() {
        card = new Card();
        card.setPincode(CardUtils.convertToMD5("4000"));
        card.setAccount(new BigDecimal(100500));
        servlet = new ChangePinServlet();
    }

    @Test
    public void doPost_givenValidCardValidNewPin_thenUpdatePin() throws ServletException, IOException {
        //given
        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("approvedCard")).thenReturn(card);
        when(request.getParameter("newPin")).thenReturn("5000");
        String newPin = CardUtils.convertToMD5("5000");

        //when
        servlet.doPost(request, response);

        //then
        verify(response, times(1)).sendRedirect("/logout");
        assertEquals(card.getPincode(), newPin);
    }

    @Test
    public void doPost_givenInvalidCard_then() throws ServletException, IOException {
        //given
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("approvedCard")).thenReturn(null);

        //when
        servlet.doPost(request, response);

        //then
        verify(response, times(1)).sendRedirect("/logout");
        verify(request, times(1)).setAttribute("errorMessage", "You should be authorized");
    }
}