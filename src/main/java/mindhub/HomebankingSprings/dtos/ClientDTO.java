package mindhub.HomebankingSprings.dtos;

import mindhub.HomebankingSprings.models.Card;
import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.models.Loan;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
        private long id;
        private String firstNane ;
        private String lastName ;
        private String email;
         private String password;
        private Set<AccountDTO> accounts;
        private Set<ClientLoanDTO>clientLoans;
        private Set<CardDTO>cards;
        public ClientDTO(Client client){
            this.id = client.getId(); // asigno el ID del client que estoy pasando por parametro a traves del metodo get
            this.firstNane = client.getFirstName();
            this.lastName = client.getLastName();
            this.email = client.getEmail();
            this.password = client.getPassword();
            this.accounts= client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
            this.clientLoans = client.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
            this.cards = client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
        }

        public String getFirstNane() {
            return firstNane;
        }

    public long getId() {
        return id;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

    public Set<CardDTO> getCards() {
        return cards;
    }

    public Set<ClientLoanDTO> getLoans() {
        return clientLoans;
    }

    public String getPassword() {
        return password;
    }
}

