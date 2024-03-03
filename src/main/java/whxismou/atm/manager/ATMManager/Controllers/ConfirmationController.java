package whxismou.atm.manager.ATMManager.Controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import whxismou.atm.manager.ATMManager.Entidades.UserApp;
import whxismou.atm.manager.ATMManager.Services.UserService;

public class ConfirmationController {

    @Autowired
    private UserService userService;

    @GetMapping("/confirm")
    public String confirmAccount(@RequestParam("token") String token) {
        UserApp user = userService.getUserByVerificationToken(token);

        if (user != null && user.getTokenExpiration().isAfter(LocalDateTime.now())) {
            user.setActive(true);
            userService.saveUser(user);
            return "Cuenta verificada";
        } else {
            return "Token inválido";
        }
    }

}
