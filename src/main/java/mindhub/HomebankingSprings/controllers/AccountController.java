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

    @GetMapping("/accounts") //indico que tipo de peticion manejara este servlet y la r99999999uta para el mismo9
    public Set<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {//Esta anotación se usa para extraer valores de variables de ruta
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null) {
            throw new UsernameNotFoundException("unknow client" + authentication.getName());
        }
        if (client.getAccounts().size() == 3) {
            return new ResponseEntity<>("Excedio limite de cuentas", HttpStatus.FORBIDDEN);
        }else {
            Account account = new Account(generateNumber(1 , 100000000), LocalDate.now(),  0);
            accountRepository.save(account);
            client.addAccount(account);
            clientRepository.save(client);

            return new ResponseEntity<>("Su cuenta fue creada con exito",HttpStatus.CREATED);
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
