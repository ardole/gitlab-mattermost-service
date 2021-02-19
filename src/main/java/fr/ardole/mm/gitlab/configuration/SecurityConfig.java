package fr.ardole.mm.gitlab.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
public class SecurityConfig {

    private String mmToken;

    public String getMmToken() {
        return mmToken;
    }

    void setMmToken(String mmToken) {
        this.mmToken = mmToken;
    }
}
