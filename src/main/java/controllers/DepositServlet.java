package controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {
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
        if (user != null) {
            req.setAttribute("user", user);
            req.getRequestDispatcher("view/Deposit.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("NO SUCH USER WITH ID %d", id));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                req.getMethod(), resp.getStatus(), req.getRequestURI(), resp.getLocale(), req.getRequestedSessionId()));
        long id = Long.parseLong(req.getParameter("id"));
        BigDecimal amountOfDeposit = new BigDecimal(req.getParameter("amount"));
        String initiator = applicationService.getUserById(id).getRole().toString();
        applicationService.depositMoney(id, amountOfDeposit, initiator);
        resp.sendRedirect(req.getContextPath() + "/service");
    }

    @Override
    public void destroy() {
        applicationService.destroy();
    }
}