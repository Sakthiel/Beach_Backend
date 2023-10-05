package com.thoughtworks.pulpticket.movie.controller;

import com.thoughtworks.pulpticket.PulpticketApplication;
import com.thoughtworks.pulpticket.movie.repository.Movie;
import com.thoughtworks.pulpticket.movie.repository.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

@SpringBootTest(classes = PulpticketApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MovieControllerIntegrationTest {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void beforeEach(){
        movieRepository.deleteAll();
    }
    @AfterEach
    public void afterEach(){
        movieRepository.deleteAll();
    }

    @Test
    public void should_retrieve_all_movies() throws Exception {
        movieRepository.save(new Movie("tteefdu" , "Bigil" , 100L , "sport" , BigDecimal.valueOf(7.5) ,"https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_.jpg"));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().json("[ {\n" +
                        "        \"id\": \"tteefdu\",\n" +
                        "        \"name\": \"Bigil\",\n" +
                        "        \"duration\": 100,\n" +
                        "        \"genre\": \"sport\",\n" +
                        "        \"rating\": 7.5,\n" +
                        "        \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_.jpg\"\n" +
                        "    }]"));
    }
}
