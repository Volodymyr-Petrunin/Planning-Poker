package planing.poker.common.generation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class GenerationConfiguration {
    @Bean
    public Random random(){
        return new Random();
    }
}
