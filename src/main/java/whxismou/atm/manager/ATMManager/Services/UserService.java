package whxismou.atm.manager.ATMManager.Services;

import java.util.ArrayList;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import whxismou.atm.manager.ATMManager.Entidades.UserApp;
import whxismou.atm.manager.ATMManager.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserApp user) {
        userRepository.save(user);
    }

    public UserApp getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserApp getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public ArrayList<UserApp> getAllUsers() {
        return (ArrayList<UserApp>) userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
