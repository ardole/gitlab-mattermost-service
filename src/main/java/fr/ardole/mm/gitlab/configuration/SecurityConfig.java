package fr.ardole.mm.gitlab.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "security")
public class SecurityConfig {

    private String mmToken;
    private String mmTokenBCrypted;

    public String getMmToken() {
        return mmToken;
    }

    void setMmToken(String mmToken) {
        this.mmToken = mmToken;
    }

    public String getMmTokenBCrypted() {
        return mmTokenBCrypted;
    }

    public void setMmTokenBCrypted(String mmTokenBCrypted) {
        this.mmTokenBCrypted = mmTokenBCrypted;
    }

    public boolean hasBCryptedConfiguration() {
        return StringUtils.hasText(getMmTokenBCrypted());
    }
}
