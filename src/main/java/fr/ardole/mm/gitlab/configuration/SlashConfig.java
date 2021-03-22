package fr.ardole.mm.gitlab.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "slash")
public class SlashConfig {

    private String gitlabPersonalAccessToken;
    private String gitlabHostUrl;
    private String localImageStoragePath;
    private String host;
    private String webImagePath;
    private String geckoDriverPath;

    public String getGitlabPersonalAccessToken() {
        return gitlabPersonalAccessToken;
    }

    public void setGitlabPersonalAccessToken(String gitlabPersonalAccessToken) {
        this.gitlabPersonalAccessToken = gitlabPersonalAccessToken;
    }

    public String getGitlabHostUrl() {
        return gitlabHostUrl;
    }

    public void setGitlabHostUrl(String gitlabHostUrl) {
        this.gitlabHostUrl = gitlabHostUrl;
    }

    public String getLocalImageStoragePath() {
        return localImageStoragePath;

    }

    public void setLocalImageStoragePath(String localImageStoragePath) {
        this.localImageStoragePath = localImageStoragePath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getWebImagePath() {
        return webImagePath;
    }

    public void setWebImagePath(String webImagePath) {
        this.webImagePath = webImagePath;
    }

    public String getGeckoDriverPath() {
        return geckoDriverPath;
    }

    public void setGeckoDriverPath(String geckoDriverPath) {
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        this.geckoDriverPath = geckoDriverPath;
    }

}
