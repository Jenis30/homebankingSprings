package mindhub.HomebankingSprings.dtos;

import mindhub.HomebankingSprings.models.Account;
import mindhub.HomebankingSprings.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
        private long id;
        private String firstNane ;
        private String lastName ;
        private String email;
        private Set<AccountDto> accounts;

        public ClientDTO(Client client){
            this.id = client.getId();
            this.firstNane = client.getFirstName();
            this.lastName = client.getLastName();
            this.email = client.getEmail();
            this.accounts= client.getAccounts().stream().map(account -> new AccountDto(account)).collect(Collectors.toSet());
        }

        public String getFirstNane() {
            return firstNane;
        }

        public void setFirstNane(String firstNane) {
            this.firstNane = firstNane;
        }

    public long getId() {
        return id;
    }

    public Set<AccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountDto> accounts) {
        this.accounts = accounts;
    }

    public void setId(long id) {
        this.id = id;
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
    }

