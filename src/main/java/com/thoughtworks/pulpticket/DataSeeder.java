package com.thoughtworks.pulpticket;

import com.thoughtworks.pulpticket.users.repository.User;
import com.thoughtworks.pulpticket.users.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase( UserRepository userRepository){
        return args -> {
            if (userRepository.findByUsername("Sakthi@123").isEmpty()) {
                User user1 = userRepository.save(new User("Sakthi@123", "Sakthi@123" , "ROLE_ADMIN"));
            }
        };
    }
}
