package mindhub.HomebankingSprings.controllers;


import mindhub.HomebankingSprings.dtos.AccountDTO;
import mindhub.HomebankingSprings.models.Account;
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

@RestController //Indico que esta clase sera un controlador
    @RequestMapping("/api")//hacemos la peticion con la ruta
    public class AccountController {

    @Autowired //inyecta una dependecia a una propiedad
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts") //indico que tipo de peticion manejara este servlet y la ruta para el mismo
    public Set<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }
    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccounts(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Set<AccountDTO> accountDTOS = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        if(client != null && accountDTOS != null) {
            return accountDTOS;
        }else{
            return new HashSet<>();
        }
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(Authentication authentication , @PathVariable Long id){
        Client client = (clientRepository.findByEmail(authentication.getName()));
        Set<Long> accountsId = client.getAccounts().stream().map(account -> account.getId()).collect(Collectors.toSet());
        if (!accountsId.contains(id)) {
            return new ResponseEntity<>("the account does not belong to the authenticated client" , HttpStatus.FORBIDDEN);
        }
        AccountDTO foundAccount = accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
        return new ResponseEntity<>(foundAccount,HttpStatus.CREATED);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null) {
            throw new UsernameNotFoundException("unknow client" + authentication.getName());
        }
        if (client.getAccounts().size() == 3) {
            return new ResponseEntity<>("Excedio limite de cuentas", HttpStatus.FORBIDDEN); // 403
        }else {
            Account account = new Account(generateNumber(1 , 100000000), LocalDate.now(),  0);
            accountRepository.save(account);
            client.addAccount(account);
            clientRepository.save(client);

            return new ResponseEntity<>("Su cuenta fue creada con exito",HttpStatus.CREATED); // 201
        }
    }
    public String generateNumber(int min, int max){
        Set<AccountDTO> accounts = getAccounts();
        Set<String> numberAccount = accounts.stream().map(account ->account.getNumber()).collect(Collectors.toSet());

        String aux = "VIN";
        long number;
        String numberComplete;
        do {
            number = (int) ((Math.random() * (max - min)) + min);
            String numberFormat = String.format("%03d", number);
            numberComplete = aux + number;
        }while (numberAccount.contains(numberComplete));
            return numberComplete;

    }

}
