package ch.taburett.tichu.server;

import ch.taburett.tichu.services.JwtService;
import ch.taburett.tichu.services.ProxyPlayerService;
import ch.taburett.tichu.sockets.PlayerSocketServlet;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class WebsocketModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PlayerSocketServlet.class);
        bind(ProxyPlayerService.class).in(Singleton.class);

        bind(JwtService.class).toInstance(new JwtService() {
            private Algorithm algorithm = Algorithm.HMAC256("secret");
            private JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                        .build(); //Reusable verifier instance

            @Override
            public String getToken() {
                String token = JWT.create()
                        .withIssuer("auth0")
                        .sign(algorithm);
                return token;
            }

            @Override
            public DecodedJWT verify(String token) {
                DecodedJWT jwt = verifier.verify(token);
                return jwt;
            }
        });
    }
}
