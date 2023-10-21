package ist.challenge.Kent_Gowell.client;

import ist.challenge.Kent_Gowell.dto.SwapiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "swapi", url = "https://swapi.py4e.com/api/people/")
public interface SwapiClient {
        @RequestMapping(method = RequestMethod.GET, value = "?search")
        SwapiResponse getSwapi();
}
