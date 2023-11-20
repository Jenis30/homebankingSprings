package mindhub.HomebankingSprings.Service.Implement;

import mindhub.HomebankingSprings.Service.AccountService;
import mindhub.HomebankingSprings.dtos.AccountDTO;
import mindhub.HomebankingSprings.models.Account;
import mindhub.HomebankingSprings.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public Set<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    @Override
    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public boolean existByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }

    @Override
    public boolean existsByActive(boolean active) {
        return accountRepository.existsByActive(active);
    }
}
