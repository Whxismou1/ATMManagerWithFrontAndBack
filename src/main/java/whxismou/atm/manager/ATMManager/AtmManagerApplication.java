package whxismou.atm.manager.ATMManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;



@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = {"whxismou.atm.manager.ATMManager"})
public class AtmManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmManagerApplication.class, args);
	}

}
