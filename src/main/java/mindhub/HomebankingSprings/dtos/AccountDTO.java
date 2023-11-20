package mindhub.HomebankingSprings.dtos;

import mindhub.HomebankingSprings.models.Account;
import mindhub.HomebankingSprings.models.AccountType;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    private boolean active;
    private AccountType accountType;
    private Set<TransactionDTO> transactions;

    public AccountDTO(Account account) {
        number = account.getNumber();
        id = account.getId();
        creationDate = account.getCreationDate();
        balance = account.getBalance();
        active = account.isActive();
        accountType = account.getAccountType();
        transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public boolean isActive() {
        return active;
    }
    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}


