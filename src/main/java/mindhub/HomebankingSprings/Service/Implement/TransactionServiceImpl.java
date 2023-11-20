package mindhub.HomebankingSprings.Service.Implement;

import mindhub.HomebankingSprings.Service.TransactionService;
import mindhub.HomebankingSprings.models.Transaction;
import mindhub.HomebankingSprings.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
