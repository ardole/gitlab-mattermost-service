package fr.ardole.mm.gitlab.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "slash")
public class SlashConfig {

    private String gitlabPersonalAccessToken;
    private String gitlabHostUrl;

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

}
