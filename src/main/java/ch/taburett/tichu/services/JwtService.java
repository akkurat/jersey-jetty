package ch.taburett.tichu.services;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JwtService {
    String getToken();
    DecodedJWT verify(String token );
}
