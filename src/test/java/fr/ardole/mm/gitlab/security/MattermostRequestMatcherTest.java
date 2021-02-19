package fr.ardole.mm.gitlab.security;

import fr.ardole.mm.gitlab.configuration.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
    }

    @Test
    public void requestMatcherRefuseBadToken() {
        when(securityConfig.getMmToken()).thenReturn(null);
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(false));
        when(securityConfig.getMmToken()).thenReturn("");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(false));
        when(securityConfig.getMmToken()).thenReturn("bcde4567");
        when(httpServletRequest.getParameter("token")).thenReturn("abcd1234");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(false));
    }

    @Test
    public void requestMatcherAcceptTokenConfiguredOne() {
        when(securityConfig.getMmToken()).thenReturn("the-very-secured-value");
        when(httpServletRequest.getParameter("token")).thenReturn("the-very-secured-value");
        assertThat(mattermostRequestMatcher.matches(httpServletRequest), is(true));
    }

}