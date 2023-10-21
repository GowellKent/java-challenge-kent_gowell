package ist.challenge.Kent_Gowell.services;

import ist.challenge.Kent_Gowell.dto.LoginRequest;
import ist.challenge.Kent_Gowell.entities.User;
import ist.challenge.Kent_Gowell.repositories.UserRepo;
import ist.challenge.Kent_Gowell.services.impl.LoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LoginServiceImplTest {
    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoginPositiveScenario() {
        LoginRequest loginRequest = new LoginRequest("validUsername", "validPassword");
        User mockUser = new User("validUsername", "validPassword");

        when(userRepo.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(mockUser));

        assertThrows(ResponseStatusException.class, () -> loginService.login(loginRequest));
    }

    @Test
    public void testLoginEmptyUsernameOrPassword() {
        LoginRequest loginRequest = new LoginRequest("", "");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> loginService.login(loginRequest));
        assert (exception.getStatus() == HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testLoginUsernameNotFound() {
        LoginRequest loginRequest = new LoginRequest("nonExistentUser", "validPassword");

        when(userRepo.findByUsername(loginRequest.getUsername())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> loginService.login(loginRequest));
        assert (exception.getStatus() == HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testLoginIncorrectCredentials() {
        LoginRequest loginRequest = new LoginRequest("validUsername", "wrongPassword");
        User mockUser = new User("validUsername", "validPassword"); // Simulasi data pengguna yang valid

        when(userRepo.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(mockUser));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> loginService.login(loginRequest));
        assert (exception.getStatus() == HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testLoginSuccessful() {
        LoginRequest loginRequest = new LoginRequest("validUsername", "validPassword");
        User mockUser = new User("validUsername", "validPassword"); // Simulasi data pengguna yang valid

        when(userRepo.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(mockUser));

        assertThrows(ResponseStatusException.class, () -> loginService.login(loginRequest));
    }
}
