package whxismou.atm.manager.ATMManager.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import whxismou.atm.manager.ATMManager.Entidades.UserApp;

@Repository
public interface UserRepository extends CrudRepository<UserApp, Long> {

    @Query("SELECT u FROM UserApp u WHERE u.username = ?1")
    UserApp findByUsername(String username);

    UserApp findByVerificationToken(String token);
}
