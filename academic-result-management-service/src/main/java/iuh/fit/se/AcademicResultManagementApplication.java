package iuh.fit.se;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AcademicResultManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademicResultManagementApplication.class, args);
	}

}
