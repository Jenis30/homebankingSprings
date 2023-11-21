package mindhub.HomebankingSprings.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private double amount;
    private int payments;
    private Double currentAmount;
    private int currentPayments;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    private Loan loan;



    public ClientLoan(){

    }

    public ClientLoan( double amount, int payments,Double currentAmount,int currentPayments) {

        this.amount = amount;
        this.payments = payments;
        this.currentAmount = currentAmount;
        this.currentPayments = currentPayments;

    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public Client getClient() {
        return client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Double getCurrentAmount() {return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }
    public int getCurrentPayments() {return currentPayments;
    }
    public void setCurrentPayments(int currentPayments) {
        this.currentPayments = currentPayments;
    }
}
