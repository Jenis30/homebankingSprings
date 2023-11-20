package mindhub.HomebankingSprings.Service;

import mindhub.HomebankingSprings.dtos.AccountDTO;
import mindhub.HomebankingSprings.models.Account;

import java.util.Set;

public interface AccountService {
    Set<AccountDTO>getAllAccounts();

    Account findAccountById(Long id);

    void saveAccount(Account account);

    Account findByNumber(String number);

    boolean existByNumber(String number);

    boolean existsByActive(boolean active);
}
