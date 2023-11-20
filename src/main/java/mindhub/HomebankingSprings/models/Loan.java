package mindhub.HomebankingSprings.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.print.attribute.SetOfIntegerSyntax;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
@Id
@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
@GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private String name;
    private Double maxAmount;
    private Double percentageInterest;
    @ElementCollection  // hace una relacion de uno a muchos automaticamente evitando hacer una clase payments (payments es una propiedad multivaluada)
    private List<Integer> payments;

    @OneToMany(mappedBy = "loan" , fetch = FetchType.EAGER)
    private Set<ClientLoan>clientLoans = new HashSet<>();

   public Loan(){
   }

    public Loan( String name, Double maxAmount, List<Integer> payments,Double percentageInterest) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.percentageInterest = percentageInterest;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Double getPercentageInterest() {
        return percentageInterest;
    }

    public void setPercentageInterest(Double percentageInterest) {
        this.percentageInterest = percentageInterest;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
       this.clientLoans.add(clientLoan);

    }
    @JsonIgnore
    public List<Client> getClient(){
    return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toList());
    }


}
