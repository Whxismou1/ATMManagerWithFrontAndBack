
package whxismou.atm.manager.ATMManager.Controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import whxismou.atm.manager.ATMManager.Entidades.UserApp;
import whxismou.atm.manager.ATMManager.Services.UserService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/users")
public class UserAppController {

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUser(@RequestBody UserApp user) {
        userService.sendVerificationEmail(user);
        return ResponseEntity
                .ok("Usuario registrado. Por favor, verifica tu correo electrónico para activar tu cuenta.");
    }

    @GetMapping("/login")
    public ResponseEntity<?> getUserByUsernameAndPassword(@RequestParam("username") String username,
            @RequestParam("password") String password) {

        UserApp user = userService.getUserByUsername(username);
        if (user != null) {
            // Verificar si la contraseña coincide

            if (!user.getIsActive()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no verificado");
            }

            if (passwordEncoder.matches(password, user.getPassword())) {
                // Si la contraseña coincide, devuelve el usuario como respuesta
                return ResponseEntity.ok(user);
            } else {
                // Si la contraseña no coincide, devuelve un mensaje de error
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password incorrecta");
            }
        } else {
            // Si el usuario no se encuentra, devuelve un mensaje de error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @GetMapping()
    public ArrayList<UserApp> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
