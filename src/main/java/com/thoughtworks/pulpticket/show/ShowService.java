package com.thoughtworks.pulpticket.show;

import com.thoughtworks.pulpticket.show.repository.Show;
import com.thoughtworks.pulpticket.show.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    private ShowRepository showRepository;
    @Autowired
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public List<Show> fetchAll(){
        return showRepository.findAll();
    }
}
