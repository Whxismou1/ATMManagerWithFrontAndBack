package whxismou.atm.manager.ATMManager.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import whxismou.atm.manager.ATMManager.Entidades.Transaction;
import whxismou.atm.manager.ATMManager.Entidades.UserApp;
import whxismou.atm.manager.ATMManager.Repositories.TransactionRepository;
import whxismou.atm.manager.ATMManager.Repositories.UserRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("null")
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Transaction> getAllTransactions(String username) {
        return (List) transactionRepository.findAllByUsername(username);
    }

    public Double getBalanceByUsername(String username) {
        UserApp user = userRepository.findByUsername(username);

        return Double.parseDouble(user.getBalance());

    }

    @SuppressWarnings("null")
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public void updateBalance(String username, String amount) {
        Double amountDouble = Double.parseDouble(amount);

        UserApp user = userRepository.findByUsername(username);

        Double actualBalance = Double.parseDouble(user.getBalance());

        Double newBalance = actualBalance + amountDouble;
        user.setBalance(newBalance.toString());

        userRepository.save(user);


    }

}
