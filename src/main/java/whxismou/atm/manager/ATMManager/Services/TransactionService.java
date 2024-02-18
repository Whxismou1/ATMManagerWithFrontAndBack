package whxismou.atm.manager.ATMManager.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import whxismou.atm.manager.ATMManager.Entidades.Transaction;
import whxismou.atm.manager.ATMManager.Repositories.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions(String username) {
        return (List) transactionRepository.findAllByUsername(username);
    }

}
