package ee.pmill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "ee.pmill")
public class PrayermillApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrayermillApplication.class, args);
	}

}
