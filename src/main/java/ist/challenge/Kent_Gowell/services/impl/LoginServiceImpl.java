package ist.challenge.Kent_Gowell.services.impl;

import ist.challenge.Kent_Gowell.dto.LoginRequest;
import ist.challenge.Kent_Gowell.entities.User;
import ist.challenge.Kent_Gowell.repositories.UserRepo;
import ist.challenge.Kent_Gowell.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void login(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (username.isEmpty() || password.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username dan / atau password kosong");
        }

        Optional<User> userOptional = userRepo.findByUsername(loginRequest.getUsername());
        if (userOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username Tidak ditemukan");
        }

        User user = userOptional.get();

        if(!Objects.equals(user.getPassword(), password) || !Objects.equals(user.getUsername(), username)){

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username /  password salah");
        }

        throw new ResponseStatusException(HttpStatus.OK, "Sukses Login");
    }
}
