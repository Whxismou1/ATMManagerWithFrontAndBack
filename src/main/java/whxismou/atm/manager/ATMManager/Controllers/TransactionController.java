package whxismou.atm.manager.ATMManager.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import whxismou.atm.manager.ATMManager.Entidades.Transaction;
import whxismou.atm.manager.ATMManager.Services.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{username}")
    public List<Transaction> getTransactions(@PathVariable String username) {
        return transactionService.getAllTransactions(username);
    }

    @GetMapping("/balance/{username}")
    public Double getBalance(@PathVariable String username) {
        return transactionService.getBalanceByUsername(username);
    }

    @PutMapping("/balance/{username}")
    public void updateBalance(@PathVariable String username, @RequestBody String amount) {
        transactionService.updateBalance(username, amount);
    }



    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }


    @PostMapping()
    public ResponseEntity<?> saveTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.saveTransaction(transaction);

        if(savedTransaction != null){
            return ResponseEntity.ok(savedTransaction);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la transacción");
    }




}
