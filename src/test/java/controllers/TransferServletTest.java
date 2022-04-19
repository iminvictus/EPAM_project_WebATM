package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import lombok.SneakyThrows;
import models.Card;
import models.CardCurrency;
import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.ApplicationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferServletTest {
    private String path = "view/Transfer.jsp";
    private Card cardCurrent;
    private Card cardDest;
    private User user;

    @Mock
    private ApplicationService applicationService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;

    @InjectMocks
    private TransferServlet servlet;

    @Before
    public void init() {
        user = new User(1L, "Ivan", "Ivanov");
        cardCurrent = new Card(1L, new BigDecimal("1234567890123456"), new BigDecimal(10000), CardCurrency.RUR, new Date(12345), "4000", 1L);
        cardDest = new Card(2L, new BigDecimal("1234567890123457"), new BigDecimal(10000), CardCurrency.RUR, new Date(123666), "4001", 2L);
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("amount")).thenReturn(String.valueOf(cardCurrent.getBalance().divide(new BigDecimal(10))));
        when(request.getParameter("dest_account")).thenReturn(String.valueOf(cardDest.getAccount()));
        when(applicationService.getUserById(any(Long.class))).thenReturn(user);
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        when(applicationService.getCardById(1)).thenReturn(cardCurrent);
    }

    @SneakyThrows
    @Test
    public void doGet_whenUserExist_thenTransferPage() {
        //given
        servlet.doGet(request, response);
        //then
        Assert.assertEquals(user, applicationService.getUserById(1L));
        verify(request, times(1)).getRequestDispatcher(path);
    }

    @SneakyThrows
    @Test
    public void doGet_givenNullUser_thenSendError() {
        //given
        when(applicationService.getUserById(any(Long.class))).thenReturn(null);
        //when
        servlet.doGet(request, response);
        //then
        verify(response, times(1)).sendError(HttpServletResponse.SC_NOT_FOUND, String.format("NO SUCH USER WITH ID %d", 1));
    }

    @SneakyThrows
    @Test
    public void doPost_givenExistCard_thenRedirect() {
        //given
        when(request.getParameter("dest_account")).thenReturn(String.valueOf(cardDest.getAccount()));
        when(applicationService.getCardByAccount(cardDest.getAccount())).thenReturn(cardDest);
        //when
        servlet.doPost(request, response);
        //then
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/service");
        verify(applicationService, times(1)).withdrawMoney(cardCurrent.getId(), cardCurrent.getBalance().divide(new BigDecimal(10)));
        verify(applicationService, times(1)).depositMoney(cardDest.getId(), cardCurrent.getBalance().divide(new BigDecimal(10)));
    }

    @SneakyThrows
    @Test
    public void doPost_givenNullCard_thenForwardTransferPage() {
        //given
        when(applicationService.getCardByAccount(cardDest.getAccount())).thenReturn(null);
        when(request.getParameter("amount")).thenReturn(String.valueOf(cardCurrent.getBalance().divide(new BigDecimal(10))));
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        //when
        servlet.doPost(request, response);
        //then
        verify(request, times(1)).getRequestDispatcher(path);
    }
}