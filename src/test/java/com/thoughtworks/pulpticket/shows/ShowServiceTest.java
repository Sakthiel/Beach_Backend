package com.thoughtworks.pulpticket.shows;

import com.thoughtworks.pulpticket.movie.repository.Movie;
import com.thoughtworks.pulpticket.screen.repository.Screen;
import com.thoughtworks.pulpticket.show.ShowService;
import com.thoughtworks.pulpticket.show.repository.Show;
import com.thoughtworks.pulpticket.show.repository.ShowRepository;
import com.thoughtworks.pulpticket.slot.repository.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ShowServiceTest {
    private ShowRepository showRepository;

    private ShowService showService;

    private Date showDate;
    private Slot showSlot;

    private Show show;
    private Movie movie;

    private Screen screen;

    private BigDecimal SHOW_COST = BigDecimal.valueOf(250);
    @BeforeEach
    public void beforeEach(){
        showRepository = mock(ShowRepository.class);
        showService = new ShowService(showRepository);
        showDate = Date.valueOf("2023-12-01");
        showSlot = new Slot("13:00-16:00", Time.valueOf("13:00:00"), Time.valueOf("16:00:00"));
        movie = new Movie(
                "tteefdu" ,
                "Bigil" , 100L ,
                "sport" , BigDecimal.valueOf(7.5) ,
                "https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_.jpg");
        screen = new Screen("GoldPlex");

        show = new Show(showDate , showSlot , screen , SHOW_COST , movie);
    }
    @Test
    public void should_return_all_shows(){
        List<Show> showList = new ArrayList<>();
        showList.add(show);
        when(showRepository.findAll()).thenReturn(showList);

        List<Show> actualList = showService.fetchAll();

        verify(showRepository).findAll();
        assertThat(actualList , is(equalTo(showList)));
    }

    @Test
    public void should_return_show_by_movieId_and_date(){
        String movieId  = "tteefdu";
        List<Show> showList = new ArrayList<>();
        showList.add(show);
        when(showRepository.findShowsByDateAndMovie(movieId,showDate)).thenReturn(showList);

        List<Show> actualList = showService.fetchShowsByDateAndMovie(movieId , showDate);

        verify(showRepository).findShowsByDateAndMovie(movieId,showDate);
        assertThat(actualList , is(equalTo(showList)));
    }
}
