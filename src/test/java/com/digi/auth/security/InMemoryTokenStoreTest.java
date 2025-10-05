package com.digi.auth.security;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTokenStoreTest {

    @Test
    void issueToken_generatesTokenAndStoresUsername() {
        InMemoryTokenStore store = new InMemoryTokenStore();

        String token = store.issueToken("alice");

        assertNotNull(token);
        assertFalse(token.isBlank());
        assertEquals(Optional.of("alice"), store.resolveUsername(token));
    }

    @Test
    void resolveUsername_returnsEmptyForUnknownToken() {
        InMemoryTokenStore store = new InMemoryTokenStore();

        assertTrue(store.resolveUsername("missing-token").isEmpty());
    }

    @Test
    void multipleTokens_mapToRespectiveUsers() {
        InMemoryTokenStore store = new InMemoryTokenStore();

        String tokenAlice = store.issueToken("alice");
        String tokenBob = store.issueToken("bob");

        assertEquals(Optional.of("alice"), store.resolveUsername(tokenAlice));
        assertEquals(Optional.of("bob"), store.resolveUsername(tokenBob));
    }
}


