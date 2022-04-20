package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import models.Transaction;
import services.ApplicationService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@AllArgsConstructor
@NoArgsConstructor
@WebServlet("/lastTransactions")
public class AdminLastTransactionsServlet extends HttpServlet {

    private ApplicationService applicationService;

    @Override
    public void init() throws ServletException {
        applicationService = new ApplicationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                req.getMethod(), resp.getStatus(), req.getRequestURI(), resp.getLocale(), req.getRequestedSessionId()));
        List<Transaction> transactionList = applicationService.getAllTransactions()
                .stream()
                .sorted(Comparator.comparingLong(Transaction::getId).reversed())
                .limit(20)
                .collect(Collectors.toList());
        req.setAttribute("transactions", transactionList);
        req.getRequestDispatcher("view/HistoryAdmin.jsp").forward(req, resp);
    }
}
