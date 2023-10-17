package com.thoughtworks.pulpticket.shows.controller;

import com.thoughtworks.pulpticket.PulpticketApplication;
import com.thoughtworks.pulpticket.movie.repository.Movie;
import com.thoughtworks.pulpticket.movie.repository.MovieRepository;
import com.thoughtworks.pulpticket.screen.repository.Screen;
import com.thoughtworks.pulpticket.screen.repository.ScreenRepository;
import com.thoughtworks.pulpticket.show.repository.Show;
import com.thoughtworks.pulpticket.show.repository.ShowRepository;
import com.thoughtworks.pulpticket.slot.repository.Slot;
import com.thoughtworks.pulpticket.slot.repository.SlotRepository;
import com.thoughtworks.pulpticket.users.repository.User;
import com.thoughtworks.pulpticket.users.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PulpticketApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ShowControllerIntegrationTest {
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    private Date showDate;
    private Slot showSlot;

    private Show show;
    private Movie movie;

    private Screen screen;

    private BigDecimal SHOW_COST = BigDecimal.valueOf(250);

    @BeforeEach
    public void beforeEach(){
        userRepository.save(new User("Sakthi" , "Sakthi","ROLE_ADMIN"));

        showDate = Date.valueOf("2023-12-03");
        showSlot = new Slot("slot1", Time.valueOf("09:00:00"), Time.valueOf("12:30:00"));
        movie = new Movie(
                "tteefdu" ,
                "Bigil" , 100L ,
                "sport" , BigDecimal.valueOf(7.5) ,
                "https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_.jpg");
        screen = new Screen("GoldPlex");

        show = new Show(showDate , showSlot , screen , SHOW_COST , movie);
        screenRepository.save(screen);
        slotRepository.save(showSlot);
        movieRepository.save(movie);
        showRepository.save(show);

    }
    @AfterEach
    public void afterEach(){

        showRepository.deleteAll();
        userRepository.deleteAll();
        slotRepository.deleteAll();
        screenRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void should_retrieve_all_shows() throws Exception{
        mockMvc.perform(get("/shows").
                        with(httpBasic("Sakthi", "Sakthi")))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"date\": \"2023-12-03\",\n" +
                        "        \"slot\": {\n" +
                        "            \"id\": 1,\n" +
                        "            \"name\": \"slot1\",\n" +
                        "            \"startTime\": \"9:00 am\",\n" +
                        "            \"endTime\": \"12:30 pm\"\n" +
                        "        },\n" +
                        "        \"screen\": {\n" +
                        "            \"id\": 1,\n" +
                        "            \"name\": \"GoldPlex\"\n" +
                        "        },\n" +
                        "        \"cost\": 250.00,\n" +
                        "        \"movie\": {\n" +
                        "            \"id\": \"tteefdu\",\n" +
                        "            \"name\": \"Bigil\",\n" +
                        "            \"duration\": 100,\n" +
                        "            \"genre\": \"sport\",\n" +
                        "            \"rating\": 7.5,\n" +
                        "            \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_.jpg\"\n" +
                        "        }\n" +
                        "    }]"));
    }

    @Test
    public void should_retrieve_shows_by_movieId_and_date() throws Exception{
        mockMvc.perform(get("/shows/" + movie.getId() + "/" + showDate).
                        with(httpBasic("Sakthi", "Sakthi")))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": 2,\n" +
                        "        \"date\": \"2023-12-03\",\n" +
                        "        \"slot\": {\n" +
                        "            \"id\": 2,\n" +
                        "            \"name\": \"slot1\",\n" +
                        "            \"startTime\": \"9:00 am\",\n" +
                        "            \"endTime\": \"12:30 pm\"\n" +
                        "        },\n" +
                        "        \"screen\": {\n" +
                        "            \"id\": 2,\n" +
                        "            \"name\": \"GoldPlex\"\n" +
                        "        },\n" +
                        "        \"cost\": 250.00,\n" +
                        "        \"movie\": {\n" +
                        "            \"id\": \"tteefdu\",\n" +
                        "            \"name\": \"Bigil\",\n" +
                        "            \"duration\": 100,\n" +
                        "            \"genre\": \"sport\",\n" +
                        "            \"rating\": 7.5,\n" +
                        "            \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_.jpg\"\n" +
                        "        }\n" +
                        "    }]"));
    }
    }



