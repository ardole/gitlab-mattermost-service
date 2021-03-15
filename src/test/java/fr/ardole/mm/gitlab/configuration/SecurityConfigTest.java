package fr.ardole.mm.gitlab.configuration;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class SecurityConfigTest {

    @Test
    void hasBCryptedConfiguration() {
        SecurityConfig securityConfig = new SecurityConfig();
        assertThat(securityConfig.hasBCryptedConfiguration(), is(false));

        securityConfig.setMmTokenBCrypted("ABCD");
        assertThat(securityConfig.hasBCryptedConfiguration(), is(true));
    }
}