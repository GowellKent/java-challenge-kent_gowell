package ist.challenge.Kent_Gowell.controllers;

import ist.challenge.Kent_Gowell.dto.UserRequest;
import ist.challenge.Kent_Gowell.dto.UserResponse;
import ist.challenge.Kent_Gowell.dto.UserUpdateRequest;
import ist.challenge.Kent_Gowell.entities.User;
import ist.challenge.Kent_Gowell.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListUser() {
        List<User> users = List.of(new User(), new User());

        when(userService.getUsers()).thenReturn(users);

        List<User> result = userController.listUser();

        verify(userService, times(1)).getUsers();

        assertEquals(users, result);
    }

    @Test
    public void testRegistrasi() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testUser");
        userRequest.setPassword("password");
        UserResponse userResponse = new UserResponse();

        when(userService.registrasi(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponse> responseEntity = userController.registrasi(userRequest);

        verify(userService, times(1)).registrasi(userRequest);

        assertEquals(201, responseEntity.getStatusCodeValue());

        assertEquals(userResponse, responseEntity.getBody());
    }

    @Test
    public void testUpdate() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();

        userController.update(userUpdateRequest);

        verify(userService, times(1)).updateUser(userUpdateRequest);
    }
}
