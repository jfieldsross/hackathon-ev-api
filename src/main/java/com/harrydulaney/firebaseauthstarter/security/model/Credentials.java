package com.harrydulaney.firebaseauthstarter.security.model;


import com.google.firebase.auth.FirebaseToken;
import lombok.Data;

@Data
public class Credentials {

    public enum CredentialType {
        ID_TOKEN, SESSION
    }

    private CredentialType type;
    private FirebaseToken decodedToken;
    private String idToken;
    private String session;


    public Credentials(CredentialType type, FirebaseToken decodedToken, String idToken, String session) {
        this.type = type;
        this.decodedToken = decodedToken;
        this.idToken = idToken;
        this.session = session;
    }

    public CredentialType getType() {
        return type;
    }

    public FirebaseToken getDecodedToken() {
        return decodedToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getSession() {
        return session;
    }


}