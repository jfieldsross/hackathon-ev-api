package com.harrydulaney.firebaseauthstarter.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.harrydulaney.firebaseauthstarter.security.model.Credentials;
import com.harrydulaney.firebaseauthstarter.security.model.UserDto;
import com.harrydulaney.firebaseauthstarter.utils.Constants;
import com.harrydulaney.firebaseauthstarter.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * - A custom Token Authentication Filter that checks every request for a JWT bearer token. -
 * <p>
 * Checks if session-based authentication is available by looking for a session cookie.
 * <p>
 * If the session cookie exists, verify it and use it to fetch users details.
 * <p>
 * Set the SecurityContextHolder.
 * <p>
 * If the session cookie is not available then use Bearer token to authenticate.
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    SecurityService securityService;
    CookieUtils cookieUtils;


    public TokenAuthenticationFilter(SecurityService securityService, CookieUtils cookieUtils) {
        this.securityService = securityService;
        this.cookieUtils = cookieUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        verifyToken(request);
        filterChain.doFilter(request, response);
    }

    private void verifyToken(HttpServletRequest request) {
        String session = null;
        FirebaseToken decodedToken = null;
        Credentials.CredentialType type = null;
        Cookie sessionCookie = cookieUtils.getCookie(Constants.SESSION_COOKIE);
        String token = securityService.getBearerToken(request);

        try {
            if (sessionCookie != null) {
                session = sessionCookie.getValue();
                decodedToken = FirebaseAuth.getInstance().verifySessionCookie(session, Constants.CHECK_REVOKED);
                type = Credentials.CredentialType.SESSION;
            } else if (token != null && !token.equalsIgnoreCase("undefined")) {

                sessionCookie = cookieUtils.createCookie(token);
                session = sessionCookie.getValue();
                decodedToken = FirebaseAuth.getInstance().verifySessionCookie(session, Constants.CHECK_REVOKED);
                type = Credentials.CredentialType.ID_TOKEN;
            }
        } catch (FirebaseAuthException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            log.error("<<------------------------ No valid credentials were passed ------------------------>>");
            log.error("Firebase Authentication Exception: " + e.getLocalizedMessage(), e.getCause());
            e.printStackTrace();
            securityService.handleSignOut();
            return;
        }

        UserDto userDto = firebaseTokenToUserDto(decodedToken);
        if (userDto != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDto,
                    new Credentials(type, decodedToken, token, session), null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private UserDto firebaseTokenToUserDto(FirebaseToken decodedToken) {
        UserDto userDto = null;
        if (decodedToken != null) {
            userDto = new UserDto();
            userDto.setUid(decodedToken.getUid());
            userDto.setName(decodedToken.getName());
            userDto.setEmail(decodedToken.getEmail());
            userDto.setPicture(decodedToken.getPicture());
            userDto.setIssuer(decodedToken.getIssuer());
            userDto.setEmailVerified(decodedToken.isEmailVerified());
        }
        return userDto;
    }

}