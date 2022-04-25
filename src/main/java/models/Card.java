package models;

import dao.DataTransferObject;
import java.math.BigDecimal;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card implements DataTransferObject {
    private Long id;
    private BigDecimal account;
    private BigDecimal balance;
    private CardCurrency currency;
    private Date expiration_date;
    private String pincode;
    private CardStatus cardStatus;
    private Long id_user;
}
