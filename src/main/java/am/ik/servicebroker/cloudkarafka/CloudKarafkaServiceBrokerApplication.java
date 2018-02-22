package am.ik.servicebroker.cloudkarafka;

import org.apache.catalina.filters.RequestDumperFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class CloudKarafkaServiceBrokerApplication {
	public static void main(String[] args) {
		SpringApplication.run(CloudKarafkaServiceBrokerApplication.class, args);
	}

	@Bean
	@Profile("default")
	RequestDumperFilter requestDumperFilter() {
		return new RequestDumperFilter();
	}
}
