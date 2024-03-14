package whxismou.atm.manager.ATMManager.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.cdimascio.dotenv.Dotenv;
import whxismou.atm.manager.ATMManager.Entidades.UserApp;
import whxismou.atm.manager.ATMManager.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SuppressWarnings("null")
    public void saveUser(UserApp user) {
        userRepository.save(user);
    }

    public UserApp getUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @SuppressWarnings("null")
    public UserApp getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserApp getUserByVerificationToken(String token) {
        return userRepository.findByVerificationToken(token);
    }

    public ArrayList<UserApp> getAllUsers() {
        return (ArrayList<UserApp>) userRepository.findAll();
    }

    @SuppressWarnings("null")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void sendVerificationEmail(UserApp user) {
        String token = generateToken();
        user.setVerificationToken(token);
        user.setTokenExpiration(LocalDateTime.now().plusHours(24)); // Token expira en 24 horas

        user.setNumeroCuenta(getRandomAccountNumber(token));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        this.userRepository.save(user);

        String subject = "Verifica tu cuenta";
        Dotenv dotenv = Dotenv.load();

        String confirmationUrl = dotenv.get("MAIL_WEB_HOST") + token;
        String message = "Por favor, haz clic en el siguiente enlace para confirmar tu cuenta: " + confirmationUrl;

        sendEmail(user.getEmail(), subject, message);
    }

    private String getRandomAccountNumber(String token) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < token.length(); i++) {
            char actualChar = token.charAt(i);
            if (Character.isDigit(actualChar)) {
                sb.append(actualChar);
            }
        }

        if (sb.length() < 20) {
            for (int i = 0; i < 20 - sb.length(); i++) {
                sb.append((int) (Math.random() * 10));
            }
        }

        return sb.toString();
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}
