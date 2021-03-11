package fr.ardole.mm.gitlab.service;

import fr.ardole.mm.gitlab.configuration.SlashConfig;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.ProjectApi;
import org.gitlab4j.api.UserApi;
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

    @PostConstruct
    private void initialiseProjectApi() {
        GitLabApi gitLabApi = new GitLabApi(slashConfig.getGitlabHostUrl(), slashConfig.getGitlabPersonalAccessToken());
        userApi = gitLabApi.getUserApi();
        mergeRequestApi = gitLabApi.getMergeRequestApi();
        projectApi = gitLabApi.getProjectApi();
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
}
