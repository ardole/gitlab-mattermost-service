package fr.ardole.mm.gitlab.slash.command.gitlab;

import fr.ardole.mm.gitlab.configuration.SlashConfig;
import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.slash.command.SlashCommand;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.ProjectFilter;
import org.gitlab4j.api.models.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(value = "ProjectCommand")
public class ProjectCommand extends SlashCommand {

    @Autowired
    SlashConfig slashConfig;

    @Override
    protected Map<String, Object> executeAndGetDatas() {
        try {
            GitLabApi gitLabApi = new GitLabApi(slashConfig.getGitlabHostUrl(), slashConfig.getGitlabPersonalAccessToken());
            ProjectFilter privateProjectsFilter = new ProjectFilter().withVisibility(Visibility.PRIVATE);
            List<Project> projects = gitLabApi.getProjectApi().getProjects(privateProjectsFilter);
            Map<String, Object> map = new HashMap<>();
            map.put("projects", projects);
            return map;
        } catch (GitLabApiException e) {
            throw new SlashCommandException("Unable to retrieve project list", e);
        }
    }

    @Override
    public String getMarkdownTemplateName() {
        return "project.ftl";
    }
}
