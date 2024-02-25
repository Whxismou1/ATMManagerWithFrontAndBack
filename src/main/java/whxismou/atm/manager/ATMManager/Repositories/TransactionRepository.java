package whxismou.atm.manager.ATMManager.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import whxismou.atm.manager.ATMManager.Entidades.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findAllByUsername(String username);

    // @Query("SELECT balance FROM Transaction WHERE username = ?1")
    // String getBalance(String username);

    // String getBalanceByUsername(String username);


}
