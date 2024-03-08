package whxismou.atm.manager.ATMManager.Entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private String username;
    private String password;

    private String balance;

    private boolean isActive;
    private LocalDate fechaNacimiento;
    private String verificationToken;
    private LocalDateTime tokenExpiration;

    // private String numeroCuenta;

    public boolean getIsActive() {
        return this.isActive;
    }

}
