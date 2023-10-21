package ist.challenge.Kent_Gowell.controllers;

import ist.challenge.Kent_Gowell.client.SwapiClient;
import ist.challenge.Kent_Gowell.dto.SwapiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SwapiControllerTest {
    @InjectMocks
    private SwapiController swapiController;

    @Mock
    private SwapiClient swapiClient;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSwapiPositiveScenario() {
        SwapiResponse expectedResponse = new SwapiResponse();

        Mockito.when(swapiClient.getSwapi()).thenReturn(expectedResponse);

        SwapiResponse actualResponse = swapiController.getSwapi();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetSwapiNegativeScenario() {
        Mockito.when(swapiClient.getSwapi()).thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error accessing Swapi"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> swapiController.getSwapi());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }
}