package fr.ardole.mm.gitlab.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MattermostRequestMatcher mattermostRequestMatcher;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and().csrf().disable()
            // TODO CORS and CSRF not always disabled
            .authorizeRequests().requestMatchers(mattermostRequestMatcher).permitAll()
            .anyRequest().denyAll()
            .and().formLogin().disable().anonymous()
            .and().logout().disable().anonymous();
    }

}