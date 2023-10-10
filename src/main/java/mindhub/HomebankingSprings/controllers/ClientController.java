package mindhub.HomebankingSprings.controllers;

import mindhub.HomebankingSprings.dtos.ClientDTO;
import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.repositories.AccountRepository;
import mindhub.HomebankingSprings.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController //indico que la clase va hacer un controlador
@RequestMapping("/api") // define cual es la ruta base para nuestro controlador
public class ClientController {
@Autowired // para utilizar repositorio
private ClientRepository clientRepository;
@GetMapping("/client")  //indico que tipo de peticion manejara este servlet y la ruta para el mismo
    public Set<ClientDTO>getAccounts(){
    return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toSet());
}
@GetMapping("/client/{id}")
    public ClientDTO getAccount(@PathVariable Long id){ //Esta anotación se usa para extraer valores de variables de ruta
    return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
}
}
