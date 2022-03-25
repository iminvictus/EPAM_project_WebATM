package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import org.apache.log4j.Logger;
import services.ApplicationService;
import java.io.IOException;
import java.math.BigDecimal;


@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(WithdrawServlet.class);
    private ApplicationService applicationService;

    @Override
    public void init() throws ServletException {
        applicationService = new ApplicationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                request.getMethod(), response.getStatus(), request.getRequestURI(), response.getLocale(), request.getRequestedSessionId()));
        long id = Long.parseLong(request.getParameter("id_withdrawAmount"));
        User user = applicationService.getUserById(id);
        request.setAttribute("user", user);
        request.getRequestDispatcher("view/withdraw.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                    request.getMethod(), response.getStatus(), request.getRequestURI(), response.getLocale(), request.getRequestedSessionId()));

            long id = Long.parseLong(request.getParameter("userId"));
            BigDecimal amountToWithdraw = BigDecimal.valueOf(Float.parseFloat(request.getParameter("amount")));

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