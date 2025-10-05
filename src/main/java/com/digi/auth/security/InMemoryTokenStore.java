package com.digi.auth.security;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * A simple in-memory token store mapping issued tokens to usernames.
 * Suitable for demos and local development only.
 */
@Component
public class InMemoryTokenStore {

    private final ConcurrentHashMap<String, String> tokenToUser = new ConcurrentHashMap<>();

    /**
     * Issues a new token for the provided username.
     * @param username the username to associate with the token
     * @return the generated token
     */
    public String issueToken(String username) {
        String token = UUID.randomUUID().toString();
        tokenToUser.put(token, username);
        return token;
    }

    /**
     * Resolves the username associated with the provided token.
     * @param token the token to resolve
     * @return an Optional containing the username if present, otherwise empty
     */
    public Optional<String> resolveUsername(String token) {
        return Optional.ofNullable(tokenToUser.get(token));
    }
}


