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

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.idLoan = clientLoan.getLoan().getId();
        this.nameLoan = clientLoan.getLoan().getName();
        this.payments = clientLoan.getPayments();
        this.amount = clientLoan.getAmount();
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
}



