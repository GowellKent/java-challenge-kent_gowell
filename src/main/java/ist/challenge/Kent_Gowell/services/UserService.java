package ist.challenge.Kent_Gowell.services;

import ist.challenge.Kent_Gowell.dto.UserRequest;
import ist.challenge.Kent_Gowell.dto.UserResponse;
import ist.challenge.Kent_Gowell.dto.UserUpdateRequest;
import ist.challenge.Kent_Gowell.entities.User;

import java.util.List;

public interface UserService{

    List<User> getUsers();
    UserResponse registrasi(UserRequest userRequest);
    void updateUser(UserUpdateRequest userUpdateRequest);

}
