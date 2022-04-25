package controllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import models.Card;
import services.ApplicationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CardUtils;

import java.io.IOException;


@AllArgsConstructor
@NoArgsConstructor
@Log4j
@WebServlet("/close")
public class CloseCardServlet extends HttpServlet {
        private ApplicationService applicationService = new ApplicationService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                request.getMethod(), response.getStatus(), request.getRequestURI(), response.getLocale(), request.getRequestedSessionId()));
        request.getRequestDispatcher("/view/Settings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                    request.getMethod(), response.getStatus(), request.getRequestURI(), response.getLocale(), request.getRequestedSessionId()));
            HttpSession session = request.getSession();
            Card card = CardUtils.getApprovedCard(session);
            long account = Long.parseLong(card.getAccount().toString());
            applicationService.closeCard(account);
            response.sendRedirect("/logout");
        } catch (IOException ex) {
            logger.error("IOException", ex);
            throw new RuntimeException(ex);
        }


    }
}
