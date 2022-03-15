package com.harrydulaney.firebaseauthstarter.security;


import com.harrydulaney.firebaseauthstarter.security.model.Credentials;
import com.harrydulaney.firebaseauthstarter.utils.Constants;
import com.harrydulaney.firebaseauthstarter.utils.CookieUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service("SecurityService")
public class SecurityService {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(SecurityService.class);

    private final HttpServletRequest httpServletRequest;

    private final CookieUtils cookieUtils;

    public SecurityService(HttpServletRequest httpServletRequest, CookieUtils cookieUtils) {
        this.httpServletRequest = httpServletRequest;
        this.cookieUtils = cookieUtils;
    }


    public User getUser() {
        User userPrincipal = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof User) {
            userPrincipal = ((User) principal);
        }
        return userPrincipal;
    }


    private Credentials getCredentials() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return (Credentials) securityContext.getAuthentication().getCredentials();
    }

    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = null;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7);
        }
        return bearerToken;
    }

    public ResponseEntity<Map<String, String>> handleSignOut() {
        Map<String, String> resp = new HashMap<>();
        cookieUtils.deleteCookie(Constants.SESSION_COOKIE);
        SecurityContextHolder.getContext().setAuthentication(null);
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            resp.put("message", "Sign Out Success");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        resp.put("message", "Sign Out Failed");
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}