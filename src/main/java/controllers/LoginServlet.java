package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import models.Card;
import models.CardStatus;
import models.Role;
import services.ApplicationService;
import utils.CardUtils;

@Log4j
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private ApplicationService applicationService;

    @Override
    public void init() throws ServletException {
        applicationService = new ApplicationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                req.getMethod(), resp.getStatus(), req.getRequestURI(), resp.getLocale(), req.getRequestedSessionId()));
       req.getRequestDispatcher("/view/LoginPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                req.getMethod(), resp.getStatus(), req.getRequestURI(), resp.getLocale(), req.getRequestedSessionId()));
        BigDecimal account = new BigDecimal(req.getParameter("account"));
        String pincode = CardUtils.convertToMD5(req.getParameter("pincode"));
        Card card = applicationService.getCardByAccountAndPin(account, pincode);

        if (card != null) {
            CardUtils.storeApprovedCard(req.getSession(), card);
            Role role = (Role) req.getSession().getAttribute("role");
            CardStatus status = (CardStatus) req.getSession().getAttribute("status");
            if (role.equals(Role.ADMIN)) {
                resp.sendRedirect(req.getContextPath() + ("/view/HomeAdmin.jsp"));
            } else if (role.equals(Role.CLIENT)) {
                if (status.equals(CardStatus.CLOSED)){
                    String errorMessage = "Card is CLOSED";
                    req.setAttribute("errorMessage", errorMessage);
                    req.getRequestDispatcher("view/LoginPage.jsp").forward(req, resp);
                    return;
                }
                resp.sendRedirect(req.getContextPath() + ("/view/Home.jsp"));}
        } else {
            String errorMessage = "Invalid card number or PIN";
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("/view/LoginPage.jsp").forward(req, resp);
        }

    }
}
