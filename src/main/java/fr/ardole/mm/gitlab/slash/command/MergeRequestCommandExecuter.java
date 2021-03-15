package fr.ardole.mm.gitlab.slash.command;

import fr.ardole.mm.gitlab.api.ResponseType;
import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.SlashCommandQuery;
import fr.ardole.mm.gitlab.model.SlashCommandResult;
import fr.ardole.mm.gitlab.service.ApiRegistry;
import fr.ardole.mm.gitlab.slash.api.SlashCommandExecuter;
import fr.ardole.mm.gitlab.slash.converter.FreemarkerResponseConverter;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(value = "MergeRequestCommand")
public class MergeRequestCommandExecuter extends SlashCommandExecuter {

    @Autowired
    FreemarkerResponseConverter slashResponseConverter;

    @Autowired
    ApiRegistry apiRegistry;

    @Override
    protected void executeAndSetResult(SlashCommandQuery slashCommandQuery, SlashCommandResult slashCommandResult) {
        try {
            Integer id = apiRegistry.getUserApi().getCurrentUser().getId();
            List<String> arguments = slashCommandQuery.getArguments();
            Constants.MergeRequestState mergeRequestState = Constants.MergeRequestState.OPENED;
            if (arguments.size() >= 1) {
                mergeRequestState = Constants.MergeRequestState.valueOf(arguments.get(0).toUpperCase());
            }
            MergeRequestApi mergeRequestApi = apiRegistry.getMergeRequestApi();
            MergeRequestFilter baseMergeRequestFilter = new MergeRequestFilter().withState(mergeRequestState).withOrderBy(Constants.MergeRequestOrderBy.CREATED_AT);
            List<MergeRequest> assignedMergeRequests = mergeRequestApi.getMergeRequests(baseMergeRequestFilter.withAssigneeId(id));
            List<MergeRequest> openedMergeRequests = mergeRequestApi.getMergeRequests(baseMergeRequestFilter.withAuthorId(id));
            Map<String, Object> map = new HashMap<>();
            map.put("assignedMergeRequests", assignedMergeRequests);
            map.put("openedMergeRequests", openedMergeRequests);
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
