package utils;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import models.Card;
import models.User;
import services.ApplicationService;

public class CardUtils {
    public static void storeApprovedCard(HttpSession session, Card approvedCard) {
        // set attributes into session
        ApplicationService applicationService = new ApplicationService();
        session.setAttribute("approvedCard", approvedCard);
        BigDecimal account = approvedCard.getAccount();
        BigDecimal pincode = approvedCard.getPincode();
        Long userId = applicationService.getUserIdByAccountAndPin(account, pincode);
        User user = applicationService.getUserById(userId);
        session.setAttribute("user", user);
        session.setAttribute("role", user.getRole());
    }

    // get attributes from session.
    public static Card getApprovedCard(HttpSession session) {
        Card approvedCard= (Card) session.getAttribute("approvedCard");
        return approvedCard;
    }
}
