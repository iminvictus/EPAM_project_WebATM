package models;

import dao.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements DataTransferObject {
    private long id;
    private long userid;
    private String type;
    private BigDecimal amount;
    private ZonedDateTime time;

    public Transaction(long userid, String type, BigDecimal amount, ZonedDateTime time) {
        this.userid = userid;
        this.type = type;
        this.amount = amount;
        this.time = time;
    }
}

