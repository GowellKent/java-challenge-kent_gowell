package ist.challenge.Kent_Gowell.controllers;

import ist.challenge.Kent_Gowell.client.SwapiClient;
import ist.challenge.Kent_Gowell.dto.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/challenge/swapi/")
public class SwapiController {

    @Autowired
    private SwapiClient swapiClient;

    @GetMapping
    public SwapiResponse getSwapi(){
        return swapiClient.getSwapi();
    }
}
