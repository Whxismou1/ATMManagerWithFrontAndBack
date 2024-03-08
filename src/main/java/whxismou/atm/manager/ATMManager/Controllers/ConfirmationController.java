package whxismou.atm.manager.ATMManager.Controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;

import whxismou.atm.manager.ATMManager.Entidades.UserApp;
import whxismou.atm.manager.ATMManager.Services.UserService;

@RestController
@RequestMapping("/confirmation")
public class ConfirmationController {

    @Autowired
    private UserService userService;

    @GetMapping("/confirm")
    public String confirmAccount(@RequestParam("token") String token, Model model) {
        UserApp user = userService.getUserByVerificationToken(token);
        if (user != null && user.getTokenExpiration().isAfter(LocalDateTime.now())) {
            user.setTokenExpiration(null);
            user.setActive(true);
            user.setVerificationToken(null);
            this.userService.saveUser(user);
            model.addAttribute("message", "Cuenta verificada");
            return "confirmSuccess"; // Nombre de la vista HTML
            // return "Cuenta verificada";
        } else {
            model.addAttribute("message", "Token inválido");
            return "confirmError"; // Nombre de la vista HTML
        }
    }

}
