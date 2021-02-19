package fr.ardole.mm.gitlab.security;

import fr.ardole.mm.gitlab.configuration.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MattermostRequestMatcher implements RequestMatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MattermostRequestMatcher.class);

    @Autowired
    SecurityConfig securityConfig;

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getParameter("token");
        // todo store a hsh of the value, but allow also not hash but warn inn logs
        if (tokenIsValid(token)) {
            LOGGER.error("New request with a bad token, refused");
            return true;
        } else {
            return false;
        }
    }

    private boolean tokenIsValid(String token) {
        return token != null && token.equals(securityConfig.getMmToken());
    }
}
