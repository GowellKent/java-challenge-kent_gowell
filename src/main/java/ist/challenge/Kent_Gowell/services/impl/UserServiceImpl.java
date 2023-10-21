package ist.challenge.Kent_Gowell.services.impl;

import ist.challenge.Kent_Gowell.dto.UserRequest;
import ist.challenge.Kent_Gowell.dto.UserResponse;
import ist.challenge.Kent_Gowell.dto.UserUpdateRequest;
import ist.challenge.Kent_Gowell.entities.User;
import ist.challenge.Kent_Gowell.repositories.UserRepo;
import ist.challenge.Kent_Gowell.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> getUsers() {return userRepo.findAll();}

    public UserResponse registrasi(UserRequest userRequest){
        Optional<User> userOptional = userRepo.findByUsername(userRequest.getUsername());

        if (userOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username sudah terpakai");
        }
        User user = new User();
        user.setPassword(userRequest.getPassword());
        user.setUsername(userRequest.getUsername());

        User newUser = userRepo.save(user);
        return UserResponse.builder().id(newUser.getId()).build();
    }

    public void updateUser(UserUpdateRequest userUpdateRequest) {
        User user = userRepo.findById(userUpdateRequest.getId()).orElseThrow(() -> new IllegalStateException("User ID "+ userUpdateRequest.getId() +" not found") );

        String username = userUpdateRequest.getUsername();
        String password = userUpdateRequest.getPassword();

        if (username.isEmpty() || password.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username / password tidak boleh kosong");
        }
        Optional<User> userOptional = userRepo.findByUsername(username);
        if (userOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username sudah terpakai");
        }
        if( Objects.equals(user.getPassword(), password)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password tidak boleh sama dengan password sebelumnya");
        }

        user.setUsername(username);
        user.setPassword(password);
        userRepo.save(user);

        throw new ResponseStatusException(HttpStatus.CREATED);
    }
}
