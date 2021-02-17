package fr.ardole.mm.gitlab.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class MattermostRequestMatcher implements RequestMatcher {

    Logger LOGGER = LoggerFactory.getLogger(MattermostRequestMatcher.class);

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getParameter("token");
        // TODO implement right way and test it
        return StringUtils.hasText(token);
    }
}
