package mindhub.HomebankingSprings.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity // clase o entidad
public class Client {

    //Propiedades  o atributos se representan como columnas en la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native") // la base de datos decide como generar el ID
    private long id;
    private String firstName, lastName, email;

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER) // mappeBy es relacionar por
    private Set<Account>accounts = new HashSet<>();

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private Set<ClientLoan>clientLoans = new HashSet<>();


    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card>cards = new HashSet<>();

    //Construtores

    public Client() {  //sobrecarga de metodos

    }
    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
//metodos o comportamientos geter obtener y seter asigna un valor

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addAccount(Account account){ // nos vincula el cliente con la cuenta
        account.setClient(this);
        accounts.add(account);
    }
    public void addCard(Card card){
       card.setClient(this);
       cards.add(card);
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void addClientLoan(ClientLoan clientLoan){
        this.clientLoans.add(clientLoan);
        clientLoan.setClient(this);
    }

    public List<Loan> getLoan(){
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());
    }
}

