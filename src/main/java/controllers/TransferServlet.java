package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import models.Card;
import models.User;
import services.ApplicationService;

@Log4j
@AllArgsConstructor
@NoArgsConstructor
@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
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
            req.getRequestDispatcher("view/Transfer.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("NO SUCH USER WITH ID %d", id));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                req.getMethod(), resp.getStatus(), req.getRequestURI(), resp.getLocale(), req.getRequestedSessionId()));
        long id = Long.parseLong(req.getParameter("id"));
        Card currentCard = applicationService.getCardById(id);
        BigDecimal dest_account = new BigDecimal(req.getParameter("dest_account"));

        Card dest_card = applicationService.getCardByAccount(dest_account);
        BigDecimal amount = new BigDecimal(req.getParameter("amount"));

        if (currentCard.getBalance().compareTo(amount) < 0) {
            req.setAttribute("error_amount", "Not enough money on the card");
            req.getRequestDispatcher("view/Transfer.jsp").forward(req, resp);
        } else if (dest_card == null) {
            req.setAttribute("error_card", "The card does not exist");
            req.getRequestDispatcher("view/Transfer.jsp").forward(req, resp);
        } else {
            applicationService.transferMoney(id, dest_card.getId(), amount);
            resp.sendRedirect(req.getContextPath() + "/service");
        }
    }

    @Override
    public void destroy() {
        applicationService.destroy();
    }
}