package mindhub.HomebankingSprings.dtos;

import mindhub.HomebankingSprings.models.Card;
import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.models.Loan;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
        private long id;
        private String firstName ;
        private String lastName ;
        private String email;
         private String password;
        private Set<AccountDTO> accounts;
        private Set<ClientLoanDTO>clientLoans;
        private Set<CardDTO>cards;
        public ClientDTO(Client client){
            this.id = client.getId();
            this.firstName = client.getFirstName();
            this.lastName = client.getLastName();
            this.email = client.getEmail();
            this.password = client.getPassword();
            this.accounts= client.getAccounts().stream().filter(account -> account.isActive()).map(account -> new AccountDTO(account)).collect(Collectors.toSet());
            this.clientLoans = client.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
            this.cards = client.getCards().stream().filter(card -> card.getActive()).map(card -> new CardDTO(card)).collect(Collectors.toSet());
        }
        // si aplico un .filter antes del map para filtrar las cuentas activas en mi api solo van aparecer las cuentas activas

    public String getFirstName() {
            return firstName;
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

