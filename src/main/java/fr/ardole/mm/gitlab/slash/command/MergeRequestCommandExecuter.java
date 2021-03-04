package fr.ardole.mm.gitlab.slash.command;

import fr.ardole.mm.gitlab.api.ResponseType;
import fr.ardole.mm.gitlab.configuration.SlashConfig;
import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.SlashCommandQuery;
import fr.ardole.mm.gitlab.model.SlashCommandResult;
import fr.ardole.mm.gitlab.slash.api.SlashCommandExecuter;
import fr.ardole.mm.gitlab.slash.converter.FreemarkerResponseConverter;
import org.gitlab4j.api.*;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestFilter;
import org.gitlab4j.api.models.ProjectFilter;
import org.gitlab4j.api.models.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(value = "MergeRequestCommand")
public class MergeRequestCommandExecuter extends SlashCommandExecuter {

    @Autowired
    SlashConfig slashConfig;

    @Autowired
    FreemarkerResponseConverter slashResponseConverter;

    private ProjectFilter privateProjectsFilter;
    private UserApi userApi;
    private MergeRequestApi mergeRequestApi;

    @PostConstruct
    private void initialiseProjectApi() {
        GitLabApi gitLabApi = new GitLabApi(slashConfig.getGitlabHostUrl(), slashConfig.getGitlabPersonalAccessToken());
        privateProjectsFilter = new ProjectFilter().withVisibility(Visibility.PRIVATE);
        userApi = gitLabApi.getUserApi();
        mergeRequestApi = gitLabApi.getMergeRequestApi();
    }

    @Override
    protected void executeAndSetResult(SlashCommandQuery slashCommandQuery, SlashCommandResult slashCommandResult) {
        try {
            Integer id = userApi.getCurrentUser().getId();
            List<String> arguments = slashCommandQuery.getArguments();
            Constants.MergeRequestState mergeRequestState = Constants.MergeRequestState.OPENED;
            if (arguments.size() >= 1) {
                mergeRequestState = Constants.MergeRequestState.valueOf(arguments.get(0).toUpperCase());
            }
            List<MergeRequest> mergeRequests = mergeRequestApi.getMergeRequests(new MergeRequestFilter().withAssigneeId(id).withState(mergeRequestState).withOrderBy(Constants.MergeRequestOrderBy.CREATED_AT));
            Map<String, Object> map = new HashMap<>();
            map.put("mergerequests", mergeRequests);
            String text = slashResponseConverter.convert(map, getMarkdownTemplateName());
            slashCommandResult.setText(text);
            slashCommandResult.setResponseType(ResponseType.in_channel);
        } catch (GitLabApiException e) {
            throw new SlashCommandException("Unable to retrieve merge request list", e);
        }
    }

    public String getMarkdownTemplateName() {
        return "mergerequest.ftl";
    }
}
