package fr.ardole.mm.gitlab.security;

import fr.ardole.mm.gitlab.configuration.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MattermostAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(MattermostAuthenticationProvider.class);

    @Autowired
    SecurityConfig securityConfig;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object credential = authentication.getCredentials();
        if (credentialIsValid(credential)) {
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), credential, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SLASH"));
        } else {
            LOGGER.error("New request with a bad token, refused");
            throw new BadCredentialsException("Invalid credentials for user " + authentication.getPrincipal());
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }

    private boolean credentialIsValid(Object credential) {
        if (credential instanceof String) {
            String token = (String) credential;
            if (securityConfig.hasBCryptedConfiguration()) {
                return matchWithBCryptedValue(token);
            } else {
                return matchWithPlainTextValue(token);
            }
        } else {
            return false;
        }
    }

    private boolean matchWithBCryptedValue(String token) {
        return bCryptPasswordEncoder.matches(token, securityConfig.getMmTokenBCrypted());
    }

    private boolean matchWithPlainTextValue(String token) {
        return token.equals(securityConfig.getMmToken());
    }
}