package mindhub.HomebankingSprings.controllers;


import mindhub.HomebankingSprings.dtos.AccountDTO;
import mindhub.HomebankingSprings.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController //Indico que esta clase sera un controlador
    @RequestMapping("/api")//hacemos la peticion con la ruta
    public class AccountController {

        @Autowired //inyecta una dependecia a una propiedad
        private AccountRepository accountRepository;

        @GetMapping("/accounts") //indico que tipo de peticion manejara este servlet y la r99999999uta para el mismo9
        public Set<AccountDTO> getAccounts (){
            return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        }

        @GetMapping("/accounts/{id}")
        public AccountDTO getAccount (@PathVariable Long id){//Esta anotación se usa para extraer valores de variables de ruta
            return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
        }
}
