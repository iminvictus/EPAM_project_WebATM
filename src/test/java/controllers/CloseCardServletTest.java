package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;


import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import models.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApplicationService;
import utils.CardUtils;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;

@RunWith(MockitoJUnitRunner.class)
public class CloseCardServletTest {
    @Mock
    private ApplicationService service;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private Card card;
    @Mock
    private HttpSession session;
    @InjectMocks
    private CloseCardServlet closeCardServlet;

    @SneakyThrows
    @Test
    public void doGetCloseCardTest(){
        //given
        String path = "/view/Settings.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        //when
        closeCardServlet.doGet(request, response);
        //then
        verify(request, atLeast(1)).getRequestDispatcher(path);
    }

    @SneakyThrows
    @Test
    public void doPostCloseCardTest() {
        //given
        when(request.getSession()).thenReturn(session);
        when(CardUtils.getApprovedCard(session)).thenReturn(card);
        when(card.getAccount()).thenReturn(new BigDecimal(1111222233334444L));
        //when
        closeCardServlet.doPost(request, response);
        //then
        verify(request, atLeast(1)).getSession();
        verify(CardUtils.getApprovedCard(session), atLeast(1)).getAccount();
        verify(service, atLeast(1)).closeCard(1111222233334444L);
        verify(response, atLeast(1)).sendRedirect("/logout");
    }




}