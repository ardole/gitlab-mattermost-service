package fr.ardole.mm.gitlab.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigIntegrationTest {

    @Autowired
    SecurityConfig securityConfig;

    @Test
    public void configurationIsWellKnown() {
        assertThat(securityConfig.getMmToken(), is(equalTo("the-very-secured-value")));
    }
}