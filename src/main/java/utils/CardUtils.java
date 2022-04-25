package utils;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import models.Card;
import models.CardStatus;
import models.User;
import services.ApplicationService;

public class CardUtils {
    public static void storeApprovedCard(HttpSession session, Card approvedCard) {
        // set attributes into session
        ApplicationService applicationService = new ApplicationService();
        session.setAttribute("approvedCard", approvedCard);
        BigDecimal account = approvedCard.getAccount();
        String pincode = approvedCard.getPincode();
        CardStatus status = approvedCard.getCardStatus();
        long userId = applicationService.getUserIdByAccountAndPin(account, pincode);
        User user = applicationService.getUserById(userId);
        session.setAttribute("user", user);
        session.setAttribute("role", user.getRole());
        session.setAttribute("status", status);
    }

    // get attributes from session.
    public static Card getApprovedCard(HttpSession session) {
        Card approvedCard = (Card) session.getAttribute("approvedCard");
        return approvedCard;
    }
}
