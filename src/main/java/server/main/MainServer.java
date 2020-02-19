package server.main;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

//Should already be configured correctly for all use cases, i.e., you will most likely not need to change this class
@SpringBootApplication
@Configuration
public class MainServer {

	
	// the test client assumes 18235 as its default port
	private static final int DEFAULT_PORT = 18235;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MainServer.class);
		
		// sets the port programmatically
        app.setDefaultProperties(Collections.singletonMap("server.port", DEFAULT_PORT));
        app.run(args);
	}
}
