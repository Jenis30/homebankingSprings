package mindhub.HomebankingSprings.controllers;


import mindhub.HomebankingSprings.Service.AccountService;
import mindhub.HomebankingSprings.Service.ClientService;
import mindhub.HomebankingSprings.dtos.AccountDTO;
import mindhub.HomebankingSprings.models.Account;
import mindhub.HomebankingSprings.models.AccountType;
import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.repositories.AccountRepository;
import mindhub.HomebankingSprings.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static mindhub.HomebankingSprings.Utils.AccountsUtil.generateAccountNumber;

@RestController //Indico que esta clase sera un controlador
    @RequestMapping("/api")//hacemos la peticion con la ruta
    public class AccountController {

    @Autowired
    ClientService clientService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public Set<AccountDTO> getAccounts() {
        return accountService.getAllAccounts();
    }
    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccounts(Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Set<AccountDTO> accountDTOS = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        if(client != null && accountDTOS != null) {
            return accountDTOS;
        }else{
            return new HashSet<>();
        }
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(Authentication authentication , @PathVariable Long id){
        Client client = clientService.findClientByEmail(authentication.getName());
        Set<Long> accountsId = client.getAccounts().stream().map(account -> account.getId()).collect(Collectors.toSet());
        if (!accountsId.contains(id)) {
            return new ResponseEntity<>("the account does not belong to the authenticated client" , HttpStatus.FORBIDDEN);
        }
        Account foundAccount = accountService.findAccountById(id);
        return new ResponseEntity<>(foundAccount,HttpStatus.CREATED);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam AccountType accountType) {
        Client client =clientService.findClientByEmail(authentication.getName());

        if (client == null) {
            throw new UsernameNotFoundException("unknow client" + authentication.getName());
        }
        if (!accountService.existsByActive(true)){
            return new ResponseEntity<>("account is active", HttpStatus.FORBIDDEN);
        }
        List<Account> acountsActive = client.getAccounts().stream().filter(account -> account.isActive()).collect(Collectors.toList());
        if (acountsActive.size() > 3) {
            return new ResponseEntity<>("You have reached the limit of created accounts",
                    HttpStatus.FORBIDDEN);
        }
        else {
            Account account = new Account(generateNumber(1 , 100000000), LocalDate.now(),  0, true,accountType);
            accountService.saveAccount(account);
            client.addAccount(account);
            clientService.saveClient(client);

            return new ResponseEntity<>("Su cuenta fue creada con exito",HttpStatus.CREATED); // 201
        }
    }
    public String generateNumber(int min, int max){

        String numberComplete;
        do {
           numberComplete = generateAccountNumber(max,min);
        }while (accountService.existByNumber(numberComplete));
            return numberComplete;

    }
    @PutMapping("/clients/current/accounts")  // extra4
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @RequestParam Long id) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findAccountById(id);

        if (client == null) {
            return new ResponseEntity<>("client not exist", HttpStatus.FORBIDDEN);
        }
        if (account == null) {
            return new ResponseEntity<>("account not exist", HttpStatus.FORBIDDEN);
        }
        if (account.getBalance() != 0) {
            return new ResponseEntity<>("no se puede eliminar una cuenta con saldo diferente a cero", HttpStatus.FORBIDDEN);
        }
        if (!account.isActive()) {
            return new ResponseEntity<>("no se puede eliminar una cuenta inactiva", HttpStatus.FORBIDDEN);
        }
        if (!account.getClient().equals(client)) {
            return new ResponseEntity<>("esta no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }
        account.setActive(false);
        account.getTransactions().stream().forEach(transaction -> transaction.setActive(false));
        accountService.saveAccount(account);
        return new ResponseEntity<>("account delete ok", HttpStatus.OK);
    }
}
