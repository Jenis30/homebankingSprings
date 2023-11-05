package mindhub.HomebankingSprings.dtos;

import mindhub.HomebankingSprings.models.Loan;

import java.util.List;

public class LoanDTO {
    private  Long id;
    private String name;
    private double maxAmount;
    private List<Integer> payments;

    public LoanDTO(Loan loan) {
        this.id= loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
