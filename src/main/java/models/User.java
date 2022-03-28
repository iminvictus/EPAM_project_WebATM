package models;

import dao.DataTransferObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class User implements DataTransferObject {
    private long id;
    private String name;
    private String surname;
    private BigDecimal balance;

    public User(String name, String surname, BigDecimal balance) {
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }
}
