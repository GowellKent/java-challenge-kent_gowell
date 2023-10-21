package ist.challenge.Kent_Gowell.controllers;

import ist.challenge.Kent_Gowell.dto.LoginRequest;
import ist.challenge.Kent_Gowell.services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LoginControllerTest {
    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoginSuccess() {

        LoginRequest loginRequest = new LoginRequest("username", "password");

        loginController.login(loginRequest);

        verify(loginService, times(1)).login(loginRequest);
    }

    @Test
    public void testLoginEmptyCredentials() {

        LoginRequest loginRequest = new LoginRequest("", "");

        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username dan / atau password kosong"))
                .when(loginService).login(any(LoginRequest.class));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> loginController.login(loginRequest));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testLoginFailed() {

        LoginRequest loginRequest = new LoginRequest("invalidUsername", "invalidPassword");

        Mockito.doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username /  password salah"))
                .when(loginService).login(any(LoginRequest.class));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> loginController.login(loginRequest));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }
}
