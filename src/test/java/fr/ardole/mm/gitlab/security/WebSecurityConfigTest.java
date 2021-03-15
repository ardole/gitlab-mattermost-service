package fr.ardole.mm.gitlab.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/*
Basic verifications, just in case...
Main assertions are done by test WebConfigSecurityIntegrationTest
 */
class WebSecurityConfigTest {

    WebSecurityConfig webSecurityConfig;

    @Mock
    AuthenticationManagerBuilder auth;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webSecurityConfig = new WebSecurityConfig();
    }

    @Test
    void verifyProviderLoading() {
        assertThat(webSecurityConfig.authenticationProvider(), instanceOf(MattermostAuthenticationProvider.class));

        verify(auth, times(0)).authenticationProvider(any(MattermostAuthenticationProvider.class));
        webSecurityConfig.configure(auth);
        verify(auth, times(1)).authenticationProvider(any(MattermostAuthenticationProvider.class));
    }

    @Test
    void verifyFilterLoading() {
        assertThat(webSecurityConfig.authenticationFilter(), instanceOf(MattermostHeadersFilter.class));
    }

}