package dao.cards;

import dao.DataAccessObject;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j;
import models.Card;
import models.CardCurrency;


@Log4j
public class CardDAO extends DataAccessObject<Card> {
    private static final String FIND_BY_ID = "SELECT id_card, account, balance, currency, expiration_date, pincode, id_user FROM cards WHERE id_card = ?";
    private static final String FIND_BY_USER_ID = "SELECT id_card, account, balance, currency, expiration_date, pincode, id_user FROM cards WHERE id_user = ?";
    private static final String FIND_ALL = "SELECT id_card, account, balance, currency, expiration_date, pincode, id_user FROM cards";
    private static final String FIND_USER_ID_BY_CARD_AND_PIN = "SELECT id_user FROM cards WHERE account = ? AND pincode = ?";
    private static final String FIND_CARD_BY_ACC_AND_PIN = "SELECT id_card, account, balance, currency, expiration_date, pincode, id_user FROM cards WHERE account = ? AND pincode = ?";


    public CardDAO(Connection connection) {
        super(connection);
    }
    @Override
    public Card findById(long id) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Card card = new Card();
            while (resultSet.next()) {
                card.setId(resultSet.getLong("id_card"));
                card.setAccount(resultSet.getBigDecimal("account"));
                card.setBalance(resultSet.getBigDecimal("balance"));
                card.setCurrency(CardCurrency.valueOf(resultSet.getString("currency").toUpperCase()));
                card.setExpiration(resultSet.getTime("expiration_date"));
                card.setUserid(resultSet.getLong("id_user"));
                card.setPincode(resultSet.getBigDecimal("pincode"));

            }
            logger.info("findById method was invoked in CardDAO");
            return card.getId() != 0 ? card : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Card> findAll() {
        try {
            PreparedStatement statement = this.connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Card> cardsList = new ArrayList<>();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getLong("id_card"));
                card.setAccount(resultSet.getBigDecimal("account"));
                card.setBalance(resultSet.getBigDecimal("balance"));
                card.setCurrency(CardCurrency.valueOf(resultSet.getString("currency").toUpperCase()));
                card.setExpiration(resultSet.getTime("expiration_date"));
                card.setPincode(resultSet.getBigDecimal("pincode"));
                card.setUserid(resultSet.getLong("id_user"));
                cardsList.add(card);
            }
            logger.info("findAll method was invoked in CardDAO");
            return cardsList.size() != 0 ? cardsList : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }

    public List<Card> findByUserId(long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_USER_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Card> cardsListId = new ArrayList<>();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getLong("id_card"));
                card.setAccount(resultSet.getBigDecimal("account"));
                card.setBalance(resultSet.getBigDecimal("balance"));
                card.setCurrency(CardCurrency.valueOf(resultSet.getString("currency").toUpperCase()));
                card.setExpiration(resultSet.getTime("expiration_date"));
                card.setPincode(resultSet.getBigDecimal("pincode"));
                card.setUserid(resultSet.getLong("id_user"));
                if (card.getUserid() == id)
                    cardsListId.add(card);
            }
            logger.info("findByUserId method was invoked in CardDAO");
            return cardsListId.size() != 0 ? cardsListId : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }

    public Long findUserId(BigDecimal account, BigDecimal pincode) {
        Long userId = 0L;
        try (PreparedStatement statement = this.connection.prepareStatement(FIND_USER_ID_BY_CARD_AND_PIN)) {
            statement.setBigDecimal(1, account);
            statement.setBigDecimal(2, pincode);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getLong("id_user");
            }

            logger.info("findUserId method was invoked in CardDAO");
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
        return userId;
    }

    public Card findCardByAccAndPin(BigDecimal account, BigDecimal pincode) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(FIND_CARD_BY_ACC_AND_PIN);
            statement.setBigDecimal(1, account);
            statement.setBigDecimal(2, pincode);
            ResultSet resultSet = statement.executeQuery();
            Card card = new Card();
            while (resultSet.next()) {
                card.setId(resultSet.getLong("id_card"));
                card.setAccount(resultSet.getBigDecimal("account"));
                card.setBalance(resultSet.getBigDecimal("balance"));
                card.setCurrency(CardCurrency.valueOf(resultSet.getString("currency").toUpperCase()));
                card.setExpiration(resultSet.getTime("expiration_date"));
                card.setUserid(resultSet.getLong("id_user"));
                card.setPincode(resultSet.getBigDecimal("pincode"));

            }
            logger.info("findById method was invoked in CardDAO");
            return card.getId() != null ? card : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }
}
