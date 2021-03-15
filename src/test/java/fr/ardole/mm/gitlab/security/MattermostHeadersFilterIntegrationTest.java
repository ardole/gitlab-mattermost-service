package fr.ardole.mm.gitlab.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MattermostHeadersFilterIntegrationTest {

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    FilterChain filterChain;

    @Autowired
    MattermostHeadersFilter mattermostHeadersFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doFilterInternalWhenEverythingIsOk() throws ServletException, IOException {
        when(httpServletRequest.getParameter("token")).thenReturn("ABCD");
        when(httpServletRequest.getParameter("user_name")).thenReturn("Mockito User");

        mattermostHeadersFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication.getPrincipal(), is(equalTo("Mockito User")));
        assertThat(authentication.getCredentials(), is(equalTo("ABCD")));
        assertThat(authentication.getAuthorities(), is(equalTo(AuthorityUtils.NO_AUTHORITIES)));
        assertThat(authentication.isAuthenticated(), is(false));

        verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    void doFilterInternalWhenParametersEmptyDoesNotThrowException() throws ServletException, IOException {
        when(httpServletRequest.getParameter("token")).thenReturn(null);
        when(httpServletRequest.getParameter("user_name")).thenReturn(null);

        mattermostHeadersFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication.getPrincipal(), is(nullValue()));
        assertThat(authentication.getCredentials(), is(nullValue()));
        assertThat(authentication.getAuthorities(), is(equalTo(AuthorityUtils.NO_AUTHORITIES)));
        assertThat(authentication.isAuthenticated(), is(false));

        verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    }
}