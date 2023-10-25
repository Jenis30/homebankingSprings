package mindhub.HomebankingSprings.controllers;

import mindhub.HomebankingSprings.dtos.ClientDTO;
import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.repositories.AccountRepository;
import mindhub.HomebankingSprings.repositories.ClientRepository;
import org.springframework.security.core.Authentication;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController //indico que la clase va hacer un controlador
@RequestMapping("/api") // asociar una peticion a una ruta
public class ClientController {
@Autowired
private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        @RequestMapping(path = "/clients", method = RequestMethod.POST)

        public ResponseEntity<Object> register(

                @RequestParam String firstName, @RequestParam String lastName,

                @RequestParam String email, @RequestParam String password) {


            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

            }


            if (clientRepository.findByEmail(email) != null) {

                return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

            }
            Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));

            clientRepository.save(client);

            return new ResponseEntity<>(HttpStatus.CREATED);


        }
    }


