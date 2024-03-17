package whxismou.atm.manager.ATMManager.Controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import whxismou.atm.manager.ATMManager.Entidades.UserApp;
import whxismou.atm.manager.ATMManager.Services.UserService;

@Controller
@RequestMapping("/confirmation")
public class ConfirmationController {

    @Autowired
    private UserService userService;

    @GetMapping("/confirm")
    public String confirmAccount(@RequestParam("token") String token) {
        UserApp user = userService.getUserByVerificationToken(token);
        if (user != null && user.getTokenExpiration().isAfter(LocalDateTime.now())) {
            user.setTokenExpiration(null);
            user.setActive(true);
            user.setVerificationToken(null);
            this.userService.saveUser(user);
            
            return "confirmSuccess";
            // return "Cuenta verificada";
        } else {
            return "confirmError";
        }
    }

}
