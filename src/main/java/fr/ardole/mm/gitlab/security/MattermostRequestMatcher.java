package fr.ardole.mm.gitlab.security;

import fr.ardole.mm.gitlab.configuration.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MattermostRequestMatcher implements RequestMatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MattermostRequestMatcher.class);

    @Autowired
    SecurityConfig securityConfig;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getParameter("token");
        if (tokenIsValid(token)) {
            LOGGER.error("New request with a bad token, refused");
            return true;
        } else {
            return false;
        }
    }

    private boolean tokenIsValid(String token) {
        if (token == null) {
            return false;
        } else {
            if (securityConfig.hasBCryptedConfiguration()) {
                return matchWithBCryptedValue(token);
            } else {
                return matchWithPlainTextValue(token);
            }
        }
    }

    private boolean matchWithBCryptedValue(String token) {
        return bCryptPasswordEncoder.matches(token, securityConfig.getMmTokenBCrypted());
    }

    private boolean matchWithPlainTextValue(String token) {
        return token.equals(securityConfig.getMmToken());
    }
}
