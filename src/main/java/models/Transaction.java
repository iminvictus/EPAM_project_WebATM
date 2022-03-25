package models;

import dao.DataTransferObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Transaction implements DataTransferObject {
    private long id;
    private long userid;
    private String type;
    private BigDecimal amount;
    private Timestamp time;

    public Transaction() {}

    public Transaction(long userid, String type, BigDecimal amount, Timestamp time) {
        this.userid = userid;
        this.type = type;
        this.amount = amount;
        this.time = time;
    }

    public Transaction(long id, long userid, String type, BigDecimal amount, Timestamp time) {
        this.id = id;
        this.userid = userid;
        this.type = type;
        this.amount = amount;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof models.User)) return false;
        models.User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getTime() { return time; }

    public void setTime(Timestamp time) { this.time = time; }
}
