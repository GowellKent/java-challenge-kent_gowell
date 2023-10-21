package ist.challenge.Kent_Gowell.controllers;

import ist.challenge.Kent_Gowell.dto.UserRequest;
import ist.challenge.Kent_Gowell.dto.UserResponse;
import ist.challenge.Kent_Gowell.dto.UserUpdateRequest;
import ist.challenge.Kent_Gowell.entities.User;
import ist.challenge.Kent_Gowell.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/challenge/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listUser(){
        return userService.getUsers();
    }

    @PostMapping("/registrasi")
    public ResponseEntity<UserResponse> registrasi(@RequestBody UserRequest userRequest) throws Exception{
        UserResponse userResponse = userService.registrasi(userRequest);
        return ResponseEntity
                .created(new URI("/registrasi/" ))
                .body(userResponse);
    }

    @PutMapping
    public void update(@RequestBody UserUpdateRequest userUpdateRequest){
        userService.updateUser(userUpdateRequest);
    }
}
