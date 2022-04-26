package utils;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;

import lombok.SneakyThrows;
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

    public static void updateCardPin(HttpSession session, Card newCard) {
        ApplicationService applicationService = new ApplicationService();
        session.setAttribute("approvedCard", newCard);
        applicationService.updateCardPin(newCard.getAccount(), newCard.getPincode());
    }

    // get attributes from session.
    public static Card getApprovedCard(HttpSession session) {
        Card approvedCard = (Card) session.getAttribute("approvedCard");
        return approvedCard;
    }

    @SneakyThrows
    public static String convertToMD5 (String str){
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(str.getBytes());
        byte[] digest = messageDigest.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }
}
