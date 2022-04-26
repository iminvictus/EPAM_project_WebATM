package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Card;
import services.ApplicationService;
import utils.CardUtils;

import java.io.IOException;

@WebServlet("/change-pin")
public class ChangePinServlet extends HttpServlet {
    private ApplicationService applicationService;

    @Override
    public void init() throws ServletException {
        applicationService = new ApplicationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("view/ChangePin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Card card = (Card) req.getSession().getAttribute("approvedCard");
        if (card == null) {
            req.setAttribute("errorMessage", "You should be authorized");
            resp.sendRedirect("/login");
        } else if (req.getParameter("oldPin") != null && req.getParameter("newPin") != null) {
                String oldPincode = CardUtils.convertToMD5(req.getParameter("oldPin"));
                if (card.getPincode().equals(oldPincode)) {
                    String newPincode = CardUtils.convertToMD5(req.getParameter("newPin"));
                    card.setPincode(newPincode);
                    CardUtils.updateCardPin(req.getSession(), card);
                    resp.sendRedirect("/logout");
                } else {
                    req.setAttribute("errorMessage", "Invalid card number or PIN");
                    req.getRequestDispatcher("view/ChangePin.jsp").forward(req, resp);
                }
        }
    }
}
