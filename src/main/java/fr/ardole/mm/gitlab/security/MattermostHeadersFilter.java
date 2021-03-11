package fr.ardole.mm.gitlab.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MattermostHeadersFilter extends OncePerRequestFilter {

    private static final String USER_NAME = "user_name";
    private static final String TOKEN = "token";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String userName = httpServletRequest.getParameter(USER_NAME);
        String token = httpServletRequest.getParameter(TOKEN);
        Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(userName, token);
        SecurityContextHolder.getContext().setAuthentication(requestAuthentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
