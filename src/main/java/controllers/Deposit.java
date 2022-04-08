package controllers;

import lombok.extern.log4j.Log4j;
import models.User;
import services.ApplicationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@Log4j
@WebServlet("/deposit")
public class Deposit extends HttpServlet {
    private ApplicationService applicationService;

    @Override
    public void init() {
        applicationService = new ApplicationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                req.getMethod(), resp.getStatus(), req.getRequestURI(), resp.getLocale(), req.getRequestedSessionId()));
        long id = Long.parseLong(req.getParameter("id"));
        User user = applicationService.getUserById(id);
        req.setAttribute("user", user);
        req.getRequestDispatcher("view/Deposit.jsp?=2").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                    request.getMethod(), response.getStatus(), request.getRequestURI(), response.getLocale(), request.getRequestedSessionId()));

            long id = Long.parseLong(request.getParameter("id"));
            BigDecimal amountOfDeposit = new BigDecimal(request.getParameter("amount"));

            applicationService.depositMoney(id, amountOfDeposit);
            response.sendRedirect(request.getContextPath() + "/service");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        applicationService.destroy();
    }
}