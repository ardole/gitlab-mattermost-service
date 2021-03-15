package fr.ardole.mm.gitlab.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MattermostAuthenticationProviderIntegrationTest {

    @Autowired
    MattermostAuthenticationProvider mattermostAuthenticationProvider;

    @Test
    void authenticateWithNullAuthentication() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null);
        BadCredentialsException thrown = assertThrows(BadCredentialsException.class, () -> mattermostAuthenticationProvider.authenticate(authentication));
        assertThat(thrown.getMessage(), is(equalTo("Invalid credentials for user null")));
    }

    @Test
    void authenticateWithBadAuthentication() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("PRINCE", "ABCDEF");
        BadCredentialsException thrown = assertThrows(BadCredentialsException.class, () -> mattermostAuthenticationProvider.authenticate(authentication));
        assertThat(thrown.getMessage(), is(equalTo("Invalid credentials for user PRINCE")));
    }

    @Test
    void authenticateWithGoodAuthentication() {
        Authentication postFilterAuthentication = new UsernamePasswordAuthenticationToken("PRINCE", "the-very-secured-value");
        Authentication postAuthentication = mattermostAuthenticationProvider.authenticate(postFilterAuthentication);
        assertThat(postAuthentication.getPrincipal(), is(equalTo("PRINCE")));
        assertThat(postAuthentication.getCredentials(), is(equalTo("the-very-secured-value")));
        assertThat(postAuthentication.getAuthorities(), is(equalTo(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SLASH"))));
        assertThat(postAuthentication.isAuthenticated(), is(true));
    }

    @Test
    void supportsOnlyUsernamePasswordAuthenticationToken() {
        assertThat(mattermostAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class), is(true));
        assertThat(mattermostAuthenticationProvider.supports(AbstractAuthenticationToken.class), is(false));
        assertThat(mattermostAuthenticationProvider.supports(AnonymousAuthenticationToken.class), is(false));
        assertThat(mattermostAuthenticationProvider.supports(PreAuthenticatedAuthenticationToken.class), is(false));
        assertThat(mattermostAuthenticationProvider.supports(RememberMeAuthenticationToken.class), is(false));
        assertThat(mattermostAuthenticationProvider.supports(RunAsUserToken.class), is(false));
        assertThat(mattermostAuthenticationProvider.supports(TestingAuthenticationToken.class), is(false));
    }
}