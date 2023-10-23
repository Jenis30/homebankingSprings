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
@GetMapping("/clients")
    public Set<ClientDTO>getClients(){
    return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toSet());
}
@GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){ // captura la parte variable de la url
    return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
}
    @Autowired

    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)

    public ResponseEntity<?> register(

            @RequestParam String name, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {

        if (name.isBlank() ||  lastName.isBlank() || email.isBlank() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Email in use", HttpStatus.FORBIDDEN);
        }

        clientRepository.save(new Client(name, lastName, email, passwordEncoder.encode(password)));
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);

    }
    @RequestMapping("/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName(); // Utiliza getName() para obtener el email.
            Client client = clientRepository.findByEmail(email);
            if (client != null) {
                // Convierte el objeto Client a un ClientDTO si es necesario.
                // Supongo que tienes una lógica para esto.
                ClientDTO clientDTO = new ClientDTO(client);
                return clientDTO;
            }
        }
        return null;
    }
}
