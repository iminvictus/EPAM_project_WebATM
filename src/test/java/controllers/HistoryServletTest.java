package controllers;

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
import org.junit.Assert;
import org.junit.Before;
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
public class HistoryServletTest {
    private final String path = "view/History.jsp";
    private List<Transaction> histList;
    private List<Transaction> histListId;
    @Mock
    private ApplicationService applicationService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @InjectMocks
    private HistoryServlet historyServlet;

    @Before
    public void initTest() {
        histList = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, ZonedDateTime.now(), new BigDecimal(1000), "Deposit", "CLIENT", "Done", 1);
        Transaction transaction2 = new Transaction(2, ZonedDateTime.now(), new BigDecimal(1000), "Deposit", "CLIENT", "Done", 2);
        Transaction transaction3 = new Transaction(3, ZonedDateTime.now(), new BigDecimal(500), "Withdraw", "CLIENT", "Done", 1);
        Transaction transaction4 = new Transaction(4, ZonedDateTime.now(), new BigDecimal(500), "Withdraw", "CLIENT", "Done", 2);
        histList.add(transaction1);
        histList.add(transaction2);
        histList.add(transaction3);
        histList.add(transaction4);
        histListId = new ArrayList<>();
        for (Transaction tr : histList) {
            if (tr.getId_card() == 1) histListId.add(tr);
        }
    }

    @Test
    public void doHistoryTest() throws ServletException, IOException {
        //given
        when(applicationService.getAllTransactions()).thenReturn(histList);
        when(applicationService.getTransactionsByCardId(1)).thenReturn(histListId);
        when(request.getAttribute("histList")).thenReturn(histList);
        when(request.getAttribute("histListId")).thenReturn(histListId);
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        //when
        historyServlet.doGet(request, response);
        List<Transaction> histListGot = (ArrayList<Transaction>) request.getAttribute("histList");
        List<Transaction> histListIdGot = (ArrayList<Transaction>) request.getAttribute("histListId");
        //then
        verify(request, times(1)).getAttribute("histList");
        verify(request, times(1)).getAttribute("histListId");

        Assert.assertTrue(histListGot.size() > 0);
        Assert.assertTrue(histListIdGot.size() > 0);
        Assert.assertEquals(histListGot, histList);
        Assert.assertEquals(histListIdGot, histListId);
    }
}