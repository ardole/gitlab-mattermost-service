package fr.ardole.mm.gitlab.service;

import fr.ardole.mm.gitlab.configuration.SlashConfig;
import org.gitlab4j.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApiRegistry {

    @Autowired
    SlashConfig slashConfig;

    private UserApi userApi;
    private MergeRequestApi mergeRequestApi;
    private ProjectApi projectApi;
    private EnvironmentsApi environmentsApi;

    @PostConstruct
    private void initializeMainApi() {
        GitLabApi gitLabApi = new GitLabApi(slashConfig.getGitlabHostUrl(), slashConfig.getGitlabPersonalAccessToken());
        userApi = gitLabApi.getUserApi();
        mergeRequestApi = gitLabApi.getMergeRequestApi();
        projectApi = gitLabApi.getProjectApi();
        environmentsApi = gitLabApi.getEnvironmentsApi();
    }

    public UserApi getUserApi() {
        return userApi;
    }

    public MergeRequestApi getMergeRequestApi() {
        return mergeRequestApi;
    }

    public ProjectApi getProjectApi() {
        return projectApi;
    }

    public EnvironmentsApi getEnvironmentsApi() {
        return environmentsApi;
    }
}
