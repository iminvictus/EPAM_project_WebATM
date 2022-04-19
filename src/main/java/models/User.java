package models;

import dao.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements DataTransferObject {
    private long id;
    private String name;
    private String surname;
    private Role role;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;

    }

    public User(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;

    }
}
