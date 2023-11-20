package mindhub.HomebankingSprings.dtos;

import mindhub.HomebankingSprings.models.Transaction;
import mindhub.HomebankingSprings.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private LocalDateTime Date;
    private String description;

    private TransactionType Type;
    private Double amount;

    private Double balance;
    private Boolean active;


    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.Date = transaction.getDate();
        this.description = transaction.getDescription();
        this.Type = transaction.getType();
        this.amount = transaction.getAmount();
        this.balance = transaction.getBalance();
        this.active = transaction.getActive();
    }


    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getType() {return Type;}

    public Double getAmount() {return amount;}

    public Double getBalance() {return balance;}

    public Boolean getActive() {
        return active;
    }
}
