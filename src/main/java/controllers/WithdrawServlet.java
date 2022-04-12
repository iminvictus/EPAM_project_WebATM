package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import models.User;

import services.ApplicationService;
import java.io.IOException;
import java.math.BigDecimal;

@Log4j
@AllArgsConstructor
@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {
    private ApplicationService applicationService;

    public WithdrawServlet() {
    }

    @Override
    public void init() {
        applicationService = new ApplicationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                request.getMethod(), response.getStatus(), request.getRequestURI(), response.getLocale(), request.getRequestedSessionId()));
//        long id = Long.parseLong(request.getParameter("id"));
//        User user = applicationService.getUserById(id);
//        request.setAttribute("user", user);
        request.getRequestDispatcher("view/Withdraw.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                    request.getMethod(), response.getStatus(), request.getRequestURI(), response.getLocale(), request.getRequestedSessionId()));

            long id = Long.parseLong(request.getParameter("id"));
            BigDecimal amountToWithdraw = new BigDecimal(request.getParameter("amount"));

            applicationService.withdrawMoney(id, amountToWithdraw);
            response.sendRedirect(request.getContextPath() + "/service");
        } catch (IOException ex) {
            logger.error("IOException", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void destroy() {
        applicationService.destroy();
    }
}