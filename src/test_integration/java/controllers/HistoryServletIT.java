package controllers;

import dao.cards.CardDAO;
import dao.transactions.TransactionDAO;
import dao.users.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import models.Transaction;
import org.junit.Before;
import org.junit.Test;
import services.ApplicationService;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HistoryServletIT {
    private static final String HISTORY_PATH = "view/History.jsp";
    private List<Transaction> histList;
    private List<Transaction> histListId;

    private HistoryServlet historyServlet;
    private UserDAO userDAO;
    private TransactionDAO transactionDAO;
    private CardDAO cardDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @Before
    public void initTest() {
        histList = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, "Deposit", new BigDecimal(1000), ZonedDateTime.now());
        Transaction transaction2 = new Transaction(2, "Withdraw", new BigDecimal(2000), ZonedDateTime.now());
        Transaction transaction3 = new Transaction(1, "Deposit", new BigDecimal(500), ZonedDateTime.now());
        Transaction transaction4 = new Transaction(2, "Withdraw", new BigDecimal(100), ZonedDateTime.now());
        histList.add(transaction1);
        histList.add(transaction2);
        histList.add(transaction3);
        histList.add(transaction4);
        histListId = new ArrayList<>();
        for (Transaction tr: histList
             ) {
            if(tr.getUserid()==1) histListId.add(tr);
       }

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        userDAO = mock(UserDAO.class);
        transactionDAO = mock(TransactionDAO.class);
        cardDAO = mock(CardDAO.class);
        historyServlet = new HistoryServlet(new ApplicationService(userDAO, transactionDAO, cardDAO));
    }

    @Test
    public void doHistoryTest() throws ServletException, IOException {
        // given
        when(request.getRequestDispatcher(HISTORY_PATH)).thenReturn(dispatcher);
        when(transactionDAO.findAll()).thenReturn(histList);
        when(transactionDAO.findByUserId(1)).thenReturn(histListId);

        // when
        historyServlet.doGet(request, response);

        // then
        verify(transactionDAO, times(1)).findAll();
        verify(transactionDAO, times(1)).findByUserId(1);

        verify(request, times(1)).setAttribute("histList", histList);
        verify(request, times(1)).setAttribute("histListId", histListId);
        verify(request, times(1)).getRequestDispatcher(HISTORY_PATH);
        verify(dispatcher).forward(request, response);
    }

}