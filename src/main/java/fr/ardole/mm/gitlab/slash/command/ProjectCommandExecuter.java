package fr.ardole.mm.gitlab.slash.command;

import fr.ardole.mm.gitlab.api.ResponseType;
import fr.ardole.mm.gitlab.configuration.SlashConfig;
import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.SlashCommandQuery;
import fr.ardole.mm.gitlab.model.SlashCommandResult;
import fr.ardole.mm.gitlab.slash.api.SlashCommandExecuter;
import fr.ardole.mm.gitlab.slash.converter.FreemarkerResponseConverter;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.ProjectApi;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.ProjectFilter;
import org.gitlab4j.api.models.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(value = "ProjectCommand")
public class ProjectCommandExecuter extends SlashCommandExecuter {

    @Autowired
    SlashConfig slashConfig;

    @Autowired
    FreemarkerResponseConverter slashResponseConverter;

    private ProjectFilter privateProjectsFilter;
    private ProjectApi projectApi;

    @PostConstruct
    private void initialiseProjectApi() {
        GitLabApi gitLabApi = new GitLabApi(slashConfig.getGitlabHostUrl(), slashConfig.getGitlabPersonalAccessToken());
        privateProjectsFilter = new ProjectFilter().withVisibility(Visibility.PRIVATE);
        projectApi = gitLabApi.getProjectApi();
    }

    @Override
    protected void executeAndSetResult(SlashCommandQuery slashCommandQuery, SlashCommandResult slashCommandResult) {
        try {
            List<Project> projects = projectApi.getProjects(privateProjectsFilter);
            Map<String, Object> map = new HashMap<>();
            map.put("projects", projects);
            List<String> arguments = slashCommandQuery.getArguments();
            boolean detailsAreAsked = false;
            boolean inChannelAsked = false;
            if (arguments.size() >= 1) {
                detailsAreAsked = "detail".equals(arguments.get(0));
                inChannelAsked = "inchannel".equals(arguments.get(arguments.size() - 1));
            }
            map.put("detailsAreAsked", detailsAreAsked);
            String text = slashResponseConverter.convert(map, getMarkdownTemplateName());
            slashCommandResult.setText(text);
            if (inChannelAsked) {
                slashCommandResult.setResponseType(ResponseType.in_channel);
            } else {
                slashCommandResult.setResponseType(ResponseType.ephemeral);
            }
        } catch (GitLabApiException e) {
            throw new SlashCommandException("Unable to retrieve project list", e);
        }
    }

    public String getMarkdownTemplateName() {
        return "project.ftl";
    }
}
