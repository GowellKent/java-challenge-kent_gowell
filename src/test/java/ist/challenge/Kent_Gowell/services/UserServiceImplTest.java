package ist.challenge.Kent_Gowell.services;

import ist.challenge.Kent_Gowell.dto.UserRequest;
import ist.challenge.Kent_Gowell.dto.UserResponse;
import ist.challenge.Kent_Gowell.dto.UserUpdateRequest;
import ist.challenge.Kent_Gowell.entities.User;
import ist.challenge.Kent_Gowell.repositories.UserRepo;
import ist.challenge.Kent_Gowell.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUsers() {
        when(userRepo.findAll()).thenReturn(List.of(new User(), new User()));

        List<User> users = userService.getUsers();

        verify(userRepo, times(1)).findAll();

        assertFalse(users.isEmpty());
    }

    @Test
    public void testRegistrasi_Success() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testUser");
        userRequest.setPassword("password");

        when(userRepo.findByUsername("testUser")).thenReturn(Optional.empty());

        User savedUser = new User();
        savedUser.setId(1L);
        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        UserResponse userResponse = userService.registrasi(userRequest);

        verify(userRepo, times(1)).findByUsername("testUser");
        verify(userRepo, times(1)).save(any(User.class));

        assertEquals(1L, userResponse.getId());
    }

    @Test
    public void testRegistrasi_UsernameTaken() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("existingUsername");
        userRequest.setPassword("password");

        when(userRepo.findByUsername("existingUsername")).thenReturn(Optional.of(new User()));

        assertThrows(ResponseStatusException.class, () -> userService.registrasi(userRequest));
    }

    @Test
    public void testUpdate_Success() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(1L);
        userUpdateRequest.setUsername("newUsername");
        userUpdateRequest.setPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldUsername");
        existingUser.setPassword("oldPassword");
        when(userRepo.findById(1L)).thenReturn(Optional.of(existingUser));

        when(userRepo.findByUsername("newUsername")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.updateUser(userUpdateRequest));

        verify(userRepo, times(1)).findById(1L);
        verify(userRepo, times(1)).findByUsername("newUsername");
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdate_UsernameTaken() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(1L);
        userUpdateRequest.setUsername("existingUsername");
        userUpdateRequest.setPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("existingUsername");
        existingUser.setPassword("oldPassword");
        when(userRepo.findById(1L)).thenReturn(Optional.of(existingUser));

        when(userRepo.findByUsername("existingUsername")).thenReturn(Optional.of(new User()));

        assertThrows(ResponseStatusException.class, () -> userService.updateUser(userUpdateRequest));
    }

    @Test
    public void testUpdate_UserNotFound() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(999L); // Invalid ID

        when(userRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> userService.updateUser(userUpdateRequest));
    }

    @Test
    public void testUpdate_EmptyUsernamePassword() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(1L);
        userUpdateRequest.setUsername("");
        userUpdateRequest.setPassword("");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldUsername");
        existingUser.setPassword("oldPassword");
        when(userRepo.findById(1L)).thenReturn(Optional.of(existingUser));

        assertThrows(ResponseStatusException.class, () -> userService.updateUser(userUpdateRequest));
    }

    @Test
    public void testUpdate_SamePassword() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(1L);
        userUpdateRequest.setUsername("newUsername");
        userUpdateRequest.setPassword("oldPassword");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldUsername");
        existingUser.setPassword("oldPassword");
        when(userRepo.findById(1L)).thenReturn(Optional.of(existingUser));

        assertThrows(ResponseStatusException.class, () -> userService.updateUser(userUpdateRequest));
    }
}
