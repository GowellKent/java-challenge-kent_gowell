package ist.challenge.Kent_Gowell.controllers;

import ist.challenge.Kent_Gowell.dto.LoginRequest;
import ist.challenge.Kent_Gowell.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/challenge/login")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping
    public void login(@RequestBody LoginRequest loginRequest){
        loginService.login(loginRequest);
    }
}
