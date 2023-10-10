package mindhub.HomebankingSprings.controllers;


import mindhub.HomebankingSprings.dtos.AccountDto;
import mindhub.HomebankingSprings.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController //Indico que esta clase sera un controlador
    @RequestMapping("/api")//Defino la ruta base para este controlador
    public class AccountController {

        @Autowired //Para crear una instancia de una clase, poder usar mi repositorio
        private AccountRepository accountRepository;

        @GetMapping("/accounts") //indico que tipo de peticion manejara este servlet y la ruta para el mismo
        public Set<AccountDto> getAccounts (){
            return accountRepository.findAll().stream().map(account -> new AccountDto(account)).collect(Collectors.toSet());
        }

        @GetMapping("/account/{id}")
        public AccountDto getAccount (@PathVariable Long id){//Esta anotación se usa para extraer valores de variables de ruta
            return accountRepository.findById(id).map(account -> new AccountDto(account)).orElse(null);
        }
}
