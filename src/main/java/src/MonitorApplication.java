package src;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import src.email.EmailProcessor;

@Slf4j
@SpringBootApplication
public class MonitorApplication {

	public static void main(String[] args) {

	//	EmailProcessor emailProcessor = new EmailProcessor();

		SpringApplication.run(MonitorApplication.class, args);
		log.info("Simple log statement with inputs {}, {} and {}", 1, 2, 3);

	}

}
