package com.thoughtworks.pulpticket.show.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show , Long> {
    @Query(value = "select * from show where movie_id = ?1 AND date = ?2" , nativeQuery = true)
    List<Show> findShowsByDateAndMovie(String movieId , Date date);
}
