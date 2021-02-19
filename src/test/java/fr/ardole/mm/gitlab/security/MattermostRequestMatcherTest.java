package fr.ardole.mm.gitlab.security;

import fr.ardole.mm.gitlab.configuration.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest
class MattermostRequestMatcherTest {

    @Autowired
    MattermostRequestMatcher mattermostRequestMatcher;

    @MockBean
    SecurityConfig securityConfig;

    @Mock
    HttpServletRequest httpServletRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(securityConfig.hasBCryptedConfiguration()).thenCallRealMethod();
    }

    @Test
    public void requestMatcherRefuseWhenTokenAreNotConfigured() {
        when(securityConfig.getMmTokenBCrypted()).thenReturn(null);
        when(securityConfig.getMmToken()).thenReturn(null);
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(false));
    }

    @Test
    public void requestMatcherRefuseWhenTokenAreEmpty() {
        when(securityConfig.getMmTokenBCrypted()).thenReturn("");
        when(securityConfig.getMmToken()).thenReturn("");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(false));
    }

    @Test
    public void requestMatcherRefuseWhenTokenPlainTextOnly() {
        when(securityConfig.getMmToken()).thenReturn("bcde4567");
        when(securityConfig.getMmTokenBCrypted()).thenReturn(null);
        when(httpServletRequest.getParameter("token")).thenReturn("abcd1234");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(false));

        when(securityConfig.getMmToken()).thenReturn("bcde4567");
        when(securityConfig.getMmTokenBCrypted()).thenReturn("");
        when(httpServletRequest.getParameter("token")).thenReturn("abcd1234");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(false));
    }

    @Test
    public void requestMatcherRefuseWhenTokenBCryptOnly() {
        when(securityConfig.getMmToken()).thenReturn(null);
        when(securityConfig.getMmTokenBCrypted()).thenReturn("$2y$12$oqLNL6grxLNuxoPnJLQGNO7ABe8VfKGJAxYjFIo1Q3zOf16VDs/Be");
        when(httpServletRequest.getParameter("token")).thenReturn("abcd1234");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(false));

        when(securityConfig.getMmToken()).thenReturn(null);
        when(securityConfig.getMmTokenBCrypted()).thenReturn("$2y$12$oqLNL6grxLNuxoPnJLQGNO7ABe8VfKGJAxYjFIo1Q3zOf16VDs/Be");
        when(httpServletRequest.getParameter("token")).thenReturn("abcd1234");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(false));
    }

    @Test
    public void requestMatcherRefuseWhenTokenAreAllConfigured() {
        when(securityConfig.getMmToken()).thenReturn("bcde1234");
        when(securityConfig.getMmTokenBCrypted()).thenReturn("$2y$12$oqLNL6grxLNuxoPnJLQGNO7ABe8VfKGJAxYjFIo1Q3zOf16VDs/Be");
        when(httpServletRequest.getParameter("token")).thenReturn("abcd1234");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(false));
    }

    @Test
    public void requestMatcherAcceptTokenConfiguredInPlainText() {
        when(securityConfig.getMmToken()).thenReturn("the-very-secured-value");
        when(httpServletRequest.getParameter("token")).thenReturn("the-very-secured-value");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(true));
    }

    @Test
    public void requestMatcherAcceptTokenConfiguredInBCryptEvenIfPlainTextConfigured() {
        when(securityConfig.getMmToken()).thenReturn("the-very-secured-value");
        when(securityConfig.getMmTokenBCrypted()).thenReturn("$2y$12$xSydzpoUiCqaCpMyxzc9k.V7eWRGbmiuA.ZUyMzZ5lEjsXDB4UIXW");
        when(httpServletRequest.getParameter("token")).thenReturn("the-very-secured-value");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(true));
    }

    @Test
    public void requestMatcherAcceptTokenConfiguredINBcryptWhenAlone() {
        when(securityConfig.getMmToken()).thenReturn(null);
        when(securityConfig.getMmTokenBCrypted()).thenReturn("$2y$12$xSydzpoUiCqaCpMyxzc9k.V7eWRGbmiuA.ZUyMzZ5lEjsXDB4UIXW");
        when(httpServletRequest.getParameter("token")).thenReturn("the-very-secured-value");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(true));
    }

}