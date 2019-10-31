package com.example.library.Controller;

import com.example.library.Entity.Model.User.UserAuthenticatedModel;
import com.example.library.Entity.Model.User.UserChangePasswordModel;
import com.example.library.Entity.Model.User.UserLoginModel;
import com.example.library.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private final LoginService loginService;

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void register(@RequestBody UserLoginModel user) {
        loginService.register(user.getLogin(), user.getPassword());
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserAuthenticatedModel login(@RequestBody UserLoginModel user) {
        return loginService.login(user.getLogin(), user.getPassword());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void update(@RequestBody UserChangePasswordModel user) {
        loginService.update(user.getLogin(), user.getOldPassword(), user.getNewPassword());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestBody UserLoginModel user) {
        loginService.delete(user.getLogin(), user.getPassword());
    }
}
