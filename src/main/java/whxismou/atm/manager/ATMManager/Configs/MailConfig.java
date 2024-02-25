package whxismou.atm.manager.ATMManager.Configs;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Properties;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    JavaMailSender javaMailSender() {
        Dotenv dotenv = Dotenv.load();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(dotenv.get("MAIL_HOST"));
        mailSender.setPort(Integer.parseInt(dotenv.get("MAIL_PORT"))); // Cambia el puerto según tu configuración
        mailSender.setUsername(dotenv.get("MAIL_USER")); // Cambia esto por tu dirección de correo electrónico
        mailSender.setPassword(dotenv.get("MAIL_PASSWORD")); // Cambia esto por tu contraseña de correo electrónico

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}