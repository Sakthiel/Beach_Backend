package com.thoughtworks.pulpticket.users.controller;

import com.thoughtworks.pulpticket.PulpticketApplication;
import com.thoughtworks.pulpticket.users.repository.User;
import com.thoughtworks.pulpticket.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PulpticketApplication.class, properties =  "spring.config.name=application")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void before() {

        userRepository.deleteAll();
    }

    @Test
    public void shouldLoginSuccessfully() throws Exception {
        userRepository.save(new User("testUser", "testUser","ROLE_ADMIN" ));

        mockMvc.perform(get("/login")
                        .with(httpBasic("testUser", "testUser")))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"username\": \"testUser\"\n" +
                        "}"));
    }

    @Test
    public void shouldThrowErrorMessageForInvalidCredentials() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUsernameAvailabilityTrue() throws Exception {
        mockMvc.perform(get("/username?username=testUser"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"isUsernameAvailable\": true\n" +
                        "}"));
    }

}

