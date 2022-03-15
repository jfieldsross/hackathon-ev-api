package com.harrydulaney.firebaseauthstarter.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.SessionCookieOptions;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Service
public class CookieUtils {

    final HttpServletRequest httpServletRequest;
    final HttpServletResponse httpServletResponse;

    public CookieUtils(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    public Cookie getCookie(String name) {
        return WebUtils.getCookie(httpServletRequest, name);
    }

    public Cookie createCookie(String idToken) {
        long expiresIn = TimeUnit.DAYS.toMillis(5);
        SessionCookieOptions options = SessionCookieOptions.builder()
                .setExpiresIn(expiresIn)
                .build();
        try {
            // Create the session cookie. This will also verify the ID token in the process.
            // The session cookie will have the same claims as the ID token.
            String nuCookieStr = FirebaseAuth.getInstance().createSessionCookie(idToken, options);
            Cookie cookie = new Cookie(Constants.SESSION_COOKIE, nuCookieStr);
            cookie.setHttpOnly(Constants.COOKIE_HTTP_ONLY_LOCAL);
            cookie.setSecure(false);
            cookie.setPath(Constants.PARENT_COOKIE_PATH);
            cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(5));
            httpServletResponse.addCookie(cookie);
            return cookie;
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        throw new InvalidCookieException("Unable to create a cookie using the JWT Token provided..");
    }

    public void deleteCookie(String name) {
        Cookie cookie = new Cookie(name, null);
        System.out.println("Session cookie has been deleted....");
        cookie.setPath(Constants.PARENT_COOKIE_PATH);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(Constants.COOKIE_HTTP_ONLY_LOCAL);
        cookie.setSecure(false);
        httpServletResponse.addCookie(cookie);
    }
}
