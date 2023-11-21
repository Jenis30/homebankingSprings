package mindhub.HomebankingSprings.controllers;

import mindhub.HomebankingSprings.Service.*;
import mindhub.HomebankingSprings.dtos.LoanApplicationDTO;
import mindhub.HomebankingSprings.dtos.LoanDTO;
import mindhub.HomebankingSprings.models.*;
import mindhub.HomebankingSprings.repositories.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientLoanService clientLoanService;
    @Transactional
    @RequestMapping(value="/loans", method= RequestMethod.POST)

    public ResponseEntity<String> addLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());

        Loan loan = loanService.findLoanById(loanApplicationDTO.getId());

        if (loan == null){
          return new ResponseEntity<>("prestamo no encontrado", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount()<=0 || loanApplicationDTO.getAmount().isNaN()){
            return new ResponseEntity<>("Por favor ingrese un monto valido ", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("la cantidad de cuotas ingresadas no son validas", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount()>loan.getMaxAmount()){
            return new ResponseEntity<>("Monto no disponible", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getNumberAccount().isBlank() || client.getAccounts().stream().noneMatch(account -> account.getNumber().equals(loanApplicationDTO.getNumberAccount()))){
            return new ResponseEntity<>("Por favor ingrese un numero de cuenta valido", HttpStatus.FORBIDDEN);
        }
        double amount = loanApplicationDTO.getAmount();
        double amount1 = amount + (amount * loan.getPercentageInterest());

        Account accountOrigen = accountService.findByNumber(loanApplicationDTO.getNumberAccount());
        Double balanceTransactionCredit =  accountOrigen.getBalance() + amount;
        Transaction credit = new Transaction(TransactionType.CREDIT, "loan " + loan.getName() + " loan approved", LocalDateTime.now(), loanApplicationDTO.getAmount(),balanceTransactionCredit, true);
        Double currentAmout = amount1;
        ClientLoan prestamo = new ClientLoan(amount1, loanApplicationDTO.getPayments(),currentAmout,loanApplicationDTO.getPayments());

        accountOrigen.setBalance(accountOrigen.getBalance() + loanApplicationDTO.getAmount());
        accountOrigen.addTransaction(credit);
        client.addClientLoan(prestamo);
        loan.addClientLoan(prestamo);

        accountService.saveAccount(accountOrigen);
        transactionService.saveTransaction(credit);
        clientLoanService.saveClientLoan(prestamo);
        loanService.saveLoan(loan);
        clientService.saveClient(client);

        return  new ResponseEntity<>("Loan Approved", HttpStatus.CREATED);
    }
    @GetMapping("/loans")
    public Set<LoanDTO> getLoans() {
        return loanService.findAllLoan();
    }

    @PostMapping("/admin/loans")
    public ResponseEntity<Object> newLoanAdmin(Authentication authentication, @RequestParam String name,@RequestParam Double maxAmount,@RequestParam Double percentageInterest,@RequestParam List<Integer> payments){
        Client client = clientService.findClientByEmail(authentication.getName());

        if( name.isBlank() || name.isEmpty()){
            return new ResponseEntity<>("name is required", HttpStatus.FORBIDDEN);
        }
        if( payments.isEmpty()){
            return new ResponseEntity<>("payments is required", HttpStatus.FORBIDDEN);
        }
        if( maxAmount == null ){
            return new ResponseEntity<>("max amount is required", HttpStatus.FORBIDDEN);
        }
        if( percentageInterest == null){
            return new ResponseEntity<>("porcentage is required", HttpStatus.FORBIDDEN);
        }
        if( loanService.existsByName(name)){
            return new ResponseEntity<>("this loan already exists ", HttpStatus.FORBIDDEN);
        }
        if( maxAmount <= 0  ){
            return new ResponseEntity<>("amount cannot be equal to 0 ", HttpStatus.FORBIDDEN);
        }

        Loan loan = new Loan(name,maxAmount,payments,percentageInterest);
        loanService.saveLoan(loan);

        return  new ResponseEntity<>("Loan Created", HttpStatus.CREATED);
    }
    @Transactional
    @PostMapping("/loans/payments")
    public ResponseEntity<Object> payLoan(Authentication authentication, @RequestParam long idLoan , @RequestParam long idAccount , @RequestParam Double amount){
        Client client = clientService.findClientByEmail(authentication.getName());
        ClientLoan clientLoan = clientLoanService.findById(idLoan);
        Account account = accountService.findAccountById(idAccount);
        String loan = clientLoan.getLoan().getName();
        double installmentValue = clientLoan.getAmount() / clientLoan.getPayments();
        double roundedInstallmentValue = Math.round(installmentValue * 100.0) / 100.0;

        if (!clientLoan.getClient().equals(client)){
            return new ResponseEntity<>("The loan doesn't belong to the authenticated client",
                    HttpStatus.FORBIDDEN);
        }
        if (account == null) {
            return new ResponseEntity<>("The account doesn´t exist",
                    HttpStatus.FORBIDDEN);
        }
        if (!account.getClient().equals(client)){
            return new ResponseEntity<>("The account doesn't belong to the authenticated client",
                    HttpStatus.FORBIDDEN);
        }
        if (amount != roundedInstallmentValue){
            return new ResponseEntity<>("The amount entered does not correspond to the payment of 1 installment. Your amount to pay is US$ " + roundedInstallmentValue,
                    HttpStatus.FORBIDDEN);
        }
        if (amount == null) {
            return new ResponseEntity<>("Amount is required",
                    HttpStatus.FORBIDDEN);
        }
        if (amount <= 0){
            return new ResponseEntity<>("The amount cannot be zero or negative",
                    HttpStatus.FORBIDDEN);
        }
        if (account.getBalance() < amount) {
            return new ResponseEntity<>("Your funds are insufficient",
                    HttpStatus.FORBIDDEN);
        }

        double currentBalanceAccountDebit = account.getBalance() - amount;
        Transaction transaction = new Transaction(TransactionType.DEBIT,"Canceled fee " + loan + " loan",LocalDateTime.now(),amount,currentBalanceAccountDebit,true);

        clientLoan.setCurrentAmount(clientLoan.getCurrentAmount()-amount);
        clientLoan.setCurrentPayments(clientLoan.getCurrentPayments()-1);
        account.setBalance(account.getBalance()-amount);
        account.addTransaction(transaction);
        clientLoanService.saveClientLoan(clientLoan);
        transactionService.saveTransaction(transaction);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Payment made successfully",HttpStatus.CREATED);
    }
}
