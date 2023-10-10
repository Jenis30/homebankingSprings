package mindhub.HomebankingSprings.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

     private String number ;
    private LocalDate creationDate ;
    private double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @OneToMany(mappedBy="account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();
    public Account (){

    }

    public Account (String number, LocalDate creationDate, double balance){
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;

    }

    public void addTransaction(Transaction transaction){//Este metodo recibe una cuenta(objeto) de la clase Account
        transaction.setAccount(this) ;//Le Asigno la cuenta al cliente que este llamando este metodo
        transactions.add(transaction); //A la propiedad accounts de esta clase, le vamos a agregar la cuenta que recibimos por parametro
    }
    public long getId() {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
