package fr.ardole.mm.gitlab.slash.command;

import fr.ardole.mm.gitlab.configuration.SlashConfig;
import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.SlashCommandQuery;
import fr.ardole.mm.gitlab.slash.api.SlashCommandExecuter;
import fr.ardole.mm.gitlab.slash.converter.FreemarkerResponseConverter;
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
public class ProjectCommandExecuter extends SlashCommandExecuter {

    @Autowired
    SlashConfig slashConfig;

    @Autowired
    FreemarkerResponseConverter slashResponseConverter;

    @Override
    protected String executeAndGetText(SlashCommandQuery slashCommandQuery) {
        try {
            GitLabApi gitLabApi = new GitLabApi(slashConfig.getGitlabHostUrl(), slashConfig.getGitlabPersonalAccessToken());
            ProjectFilter privateProjectsFilter = new ProjectFilter().withVisibility(Visibility.PRIVATE);
            List<Project> projects = gitLabApi.getProjectApi().getProjects(privateProjectsFilter);
            Map<String, Object> map = new HashMap<>();
            map.put("projects", projects);
            return slashResponseConverter.convert(map, getMarkdownTemplateName());
        } catch (GitLabApiException e) {
            throw new SlashCommandException("Unable to retrieve project list", e);
        }
    }

    public String getMarkdownTemplateName() {
        return "project.ftl";
    }
}
