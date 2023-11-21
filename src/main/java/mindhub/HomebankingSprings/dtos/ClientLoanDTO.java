package mindhub.HomebankingSprings.dtos;

import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.models.ClientLoan;
import mindhub.HomebankingSprings.models.Loan;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class ClientLoanDTO {
    private Long id;
    private Long idLoan;
    private String nameLoan;
    private Double amount;
    private int payments;
    private Double currentAmount;
    private int currentPayments;

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.idLoan = clientLoan.getLoan().getId();
        this.nameLoan = clientLoan.getLoan().getName();
        this.payments = clientLoan.getPayments();
        this.amount = clientLoan.getAmount();
        this.currentAmount = clientLoan.getCurrentAmount();
        this.currentPayments = clientLoan.getCurrentPayments();
    }

    public Long getId() {
        return id;
    }

    public Long getIdLoan() {
        return idLoan;
    }

    public String getNameLoan() {
        return nameLoan;
    }

    public Double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public Double getCurrentAmount() {return currentAmount;}

    public int getCurrentPayments() {return currentPayments;}
}



