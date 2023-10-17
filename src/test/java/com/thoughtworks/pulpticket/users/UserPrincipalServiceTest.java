package com.thoughtworks.pulpticket.users;

import com.thoughtworks.pulpticket.users.repository.User;
import com.thoughtworks.pulpticket.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserPrincipalServiceTest {
    private UserRepository userRepository;
    private UserPrincipalService userPrincipalService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userPrincipalService = new UserPrincipalService(userRepository);
    }

    @Test
    public void should_load_user_by_username() {
        User user = new User("Sakthi" , "Sakthi","ROLE_ADMIN");


        when(userRepository.findByUsername("Sakthi")).thenReturn(Optional.of(user));
        UserDetails userDetails = userPrincipalService.loadUserByUsername("Sakthi");

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test
    public void should_throw_username_not_found_exception() {

        when(userRepository.findByUsername("invalidUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userPrincipalService.loadUserByUsername("invalidUser");
        });
    }

    @Test
    public void should_return_true_if_username_is_available() {
        when(userRepository.existsByUsernameIgnoreCase("availableUsername")).thenReturn(false);

        boolean isAvailable = userPrincipalService.isUsernameAvailable("availableUsername");

        assertTrue(isAvailable);
    }

    @Test
    public void should_return_false_if_username_is_not_available() {
        when(userRepository.existsByUsernameIgnoreCase("takenUsername")).thenReturn(true);

        boolean isAvailable = userPrincipalService.isUsernameAvailable("takenUsername");

        assertFalse(isAvailable);
    }

}
