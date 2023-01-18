package example.itemcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ItemControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemControllerApplication.class, args);
	}

}
