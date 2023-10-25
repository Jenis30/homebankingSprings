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

    // apartir de la clase como va hacer el objeto

    //Propiedades  o atributos se representan como columnas en la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native") // la base de datos decide como generar el ID
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;



    // metodos accesores protegemos los datos de acuerdo a lo que necesitemos

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER) // me muestra el cliente con las cuentas y lazi solo me muestra el cliente sin las cuentas
    private Set<Account>accounts = new HashSet<>();

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private Set<ClientLoan>clientLoans = new HashSet<>();


    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card>cards = new HashSet<>();

    //Construtores son los que me permiten definir como va a estar formado mi objeto

    public Client() {  //sobrecarga de metodos

    }
    public Client(String firstName, String lastName, String email, String password) { // va a recibir por parametro
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

//metodos accesores o comportamientos geter obtener y seter asigna un valor

    public long getId() {return id ; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {this.firstName = firstName;}

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

    public Set<Card> getCards() {
        return cards;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }

    public List<Loan> getLoan(){
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());
    }

}

