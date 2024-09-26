package planing.poker.repository;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EntityScan(basePackages = "planing.poker.domain")
@ComponentScan(basePackages = "planing.poker.repository")
@EnableJpaRepositories(basePackages = "planing.poker.repository")
@EnableAutoConfiguration
public class ConfigDAO {

}
