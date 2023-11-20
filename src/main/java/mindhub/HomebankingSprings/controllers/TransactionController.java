package mindhub.HomebankingSprings.controllers;
import mindhub.HomebankingSprings.Service.AccountService;
import mindhub.HomebankingSprings.Service.ClientService;
import mindhub.HomebankingSprings.Service.TransactionService;
import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.models.Transaction;
import mindhub.HomebankingSprings.models.TransactionType;
import org.springframework.web.bind.annotation.RestController;
import mindhub.HomebankingSprings.models.Account;
import mindhub.HomebankingSprings.repositories.AccountRepository;
import mindhub.HomebankingSprings.repositories.ClientRepository;
import mindhub.HomebankingSprings.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
    @RequestMapping("/api")
    public class TransactionController {

        @Autowired
        private ClientService clientService;

        @Autowired
        private AccountService accountService;

        @Autowired
        private TransactionService transactionService;


        @Transactional
        @RequestMapping(path = "/transactions", method = RequestMethod.POST)
        public ResponseEntity<Object> addTransaction(@RequestParam Double amount, @RequestParam String description, @RequestParam String originnumber, @RequestParam String destinationnumber, Authentication authentication) {

            if (amount == null || amount <= 0.0) {
                return new ResponseEntity<>("The value of amount is not valid", HttpStatus.FORBIDDEN);
            }

            if (description.isBlank()) {
                return new ResponseEntity<>("The description cannot be empty", HttpStatus.FORBIDDEN);
            }

            if (originnumber.isEmpty() || destinationnumber.isEmpty()) {
                return new ResponseEntity<>("Please indicate the account number", HttpStatus.FORBIDDEN);
            }
            if (originnumber.equals(destinationnumber)) {
                return new ResponseEntity<>("Please enter a valid account number", HttpStatus.FORBIDDEN);
            }

            Account accountOrigen = accountService.findByNumber(originnumber);
            Account accountDestino = accountService.findByNumber(destinationnumber);

            if (accountDestino == null) {
                return new ResponseEntity<>("Account not found, please enter a valid account", HttpStatus.FORBIDDEN);
            }
            if (accountOrigen == null) {
                return new ResponseEntity<>("Account not found, please enter a valid account", HttpStatus.FORBIDDEN);
            }

            String nameClient = authentication.getName();
            Client client = clientService.findClientByEmail(nameClient);

            if (client == null) {
                return new ResponseEntity<>(" client is null this request is not possible", HttpStatus.FORBIDDEN);
            }

            Set<Account> clientAccounts = client.getAccounts();
            boolean existingAccountOrigin = clientAccounts.stream().anyMatch(account -> account.getNumber().equals(originnumber));

            if (!existingAccountOrigin) {
                return new ResponseEntity<>("Please enter a valid origin account", HttpStatus.FORBIDDEN);
            }

            if (accountOrigen.getBalance() < amount) {
                return new ResponseEntity<>("insufficient founds", HttpStatus.FORBIDDEN);
            }

            Double balanceTransactionDebit = accountOrigen.getBalance() - amount;
            Transaction debit = new Transaction(TransactionType.DEBIT, "to " + destinationnumber + ": " + description, LocalDateTime.now(), amount,balanceTransactionDebit, true);
            Double balanceTransactionCredit =  accountDestino.getBalance() + amount;
            Transaction credit = new Transaction(TransactionType.CREDIT, "from " + originnumber + ": " + description, LocalDateTime.now(), amount, balanceTransactionCredit, true);
            accountOrigen.addTransaction(debit);
            accountDestino.addTransaction(credit);
            accountOrigen.setBalance(balanceTransactionDebit);
            accountDestino.setBalance(balanceTransactionCredit);
            transactionService.saveTransaction(debit);
            transactionService.saveTransaction(credit);
            accountService.saveAccount(accountOrigen);
            accountService.saveAccount(accountDestino);

            return new ResponseEntity<>("Transaction ok", HttpStatus.OK);
        }
    }

