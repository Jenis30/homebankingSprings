package mindhub.HomebankingSprings.controllers;

import mindhub.HomebankingSprings.dtos.AccountDTO;
import mindhub.HomebankingSprings.dtos.ClientDTO;
import mindhub.HomebankingSprings.models.Account;
import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.repositories.AccountRepository;
import mindhub.HomebankingSprings.repositories.ClientRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController //indico que la clase va hacer un controlador
@RequestMapping("/api") // asociar una peticion a una ruta
public class ClientController {
@Autowired
private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;

@GetMapping("/clients")
    public Set<ClientDTO>getClients(){
    return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toSet());
}
@GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){ // captura la parte variable de la url
    return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
}


        @GetMapping("/clients/current")
        public ClientDTO getAll(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
        }

        @PostMapping("/clients")
        public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

            if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {

                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);// 403 prohibido
            }

            if (clientRepository.findByEmail(email) != null) {

                return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

            }
            Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
            Account account = new Account(generateNumber(1 , 100000000), LocalDate.now(),  0);
            accountRepository.save(account);
            client.addAccount(account);
            clientRepository.save(client);

            return new ResponseEntity<>(HttpStatus.CREATED); // 201

        }
      public String generateNumber(int min, int max){
        String aux = "VIN";
        long number;
        String numberComplete;
        do {
            number = (int) ((Math.random() * (max - min)) + min);
            String numberFormat = String.format("%03d", number);
            numberComplete = aux + numberFormat; // queda formado el numero de cuenta
        }while (accountRepository.existsByNumber(numberComplete)); // condicion
        return numberComplete;

    }
    }


