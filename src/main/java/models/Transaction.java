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
    private ZonedDateTime date;
    private BigDecimal amount;
    private String type;
    private String initiated_by;
    private String state;
    private long id_card;

    public Transaction(ZonedDateTime date, BigDecimal amount, String type, String initiated_by, String state, long id_card) {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.initiated_by = initiated_by;
        this.state = state;
        this.id_card = id_card;
    }
}

