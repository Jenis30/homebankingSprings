package mindhub.HomebankingSprings;

import mindhub.HomebankingSprings.models.Account;
import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.models.Transaction;
import mindhub.HomebankingSprings.repositories.AccountRepository;
import mindhub.HomebankingSprings.repositories.ClientRepository;
import mindhub.HomebankingSprings.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static mindhub.HomebankingSprings.models.TransactionType.*;

@SpringBootApplication
public class HomebankingSpringsApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(HomebankingSpringsApplication.class, args);
	}
	@Bean
		public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,TransactionRepository transactionRepository){

		return args -> {
			LocalDateTime dateTime = LocalDateTime.now();
			Client client = new Client("Melba", "Morel", " melba@mindhub.com");

			clientRepository.save(client);
			Account account = new Account("VIN001", LocalDate.now(),5000.00);
			client.AddAccount(account);
			accountRepository.save(account);

			LocalDate fecha = LocalDate.now();
			Account account1 = new Account("VIN002",fecha.plusDays(1), 7500.00);
			client.AddAccount(account1);
			accountRepository.save(account1);

			Transaction Primer = new Transaction(CREDIT, "Pay Taxes" , dateTime, 455333.33);
			account1.addTransaction(Primer);
			transactionRepository.save(Primer);

			Transaction Second = new Transaction(DEBIT, "Buy cream and beer" , dateTime, -45.33);
			account1.addTransaction(Second);
			transactionRepository.save(Second);

			Transaction third = new Transaction(CREDIT, "Pay Taxis" , dateTime, 455333.1);
			account.addTransaction(third);
			transactionRepository.save(third);

		};
		}

}
