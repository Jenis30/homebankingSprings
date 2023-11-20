package mindhub.HomebankingSprings.Service;

import mindhub.HomebankingSprings.models.Transaction;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
}
