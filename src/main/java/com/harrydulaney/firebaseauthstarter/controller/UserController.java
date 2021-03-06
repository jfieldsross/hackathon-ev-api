package com.harrydulaney.firebaseauthstarter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * User controller - Api for passing authenticated User info to UI.
 * https://firebase.google.com/docs/auth/admin/manage-cookies
 */
@RestController
@RequestMapping("api/user")
public class UserController {


    public UserController() {
    }


    @GetMapping("secured-message")
    public Map<String, Object> getBasic() {
        Map<String, Object> model = new HashMap<>();
        model.put("content", "Secure Message sent Back!");
        return model;
    }
}
