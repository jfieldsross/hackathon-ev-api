package com.harrydulaney.firebaseauthstarter.controller;

import com.harrydulaney.firebaseauthstarter.security.SecurityService;
import com.harrydulaney.firebaseauthstarter.security.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    private final SecurityService securityService;

    public UserController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping(value = "session-login")
    public ResponseEntity<UserDto> startSession(@AuthenticationPrincipal UserDto userDto) {
        return ResponseEntity.ok(userDto);
    }


    @GetMapping("secured-message")
    public Map<String, Object> getBasic() {
        Map<String, Object> model = new HashMap<>();
        model.put("content", "Secure Message sent Back!");
        return model;
    }

    @PostMapping(value = "session-logout")
    public ResponseEntity<Map<String, String>> endSession() {
        return securityService.handleSignOut();
    }
}
