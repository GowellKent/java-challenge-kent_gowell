package ist.challenge.Kent_Gowell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KentGowellApplication {

	public static void main(String[] args) {
		SpringApplication.run(KentGowellApplication.class, args);
	}

}
