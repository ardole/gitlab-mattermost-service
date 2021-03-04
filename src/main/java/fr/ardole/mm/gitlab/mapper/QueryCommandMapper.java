package fr.ardole.mm.gitlab.mapper;

import fr.ardole.mm.gitlab.api.MattermostRequest;
import fr.ardole.mm.gitlab.api.MattermostResponse;
import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.SlashCommandQuery;
import fr.ardole.mm.gitlab.model.SlashCommandResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Component
public class QueryCommandMapper {

    public SlashCommandQuery requestToSlashQuery(MattermostRequest MattermostRequest) {
        String text = MattermostRequest.getText();
        if (StringUtils.hasText(text)) {
            return extractCommandFromQueryText(text);
        } else {
            throw new SlashCommandException("No text given to command");
        }
    }

    private SlashCommandQuery extractCommandFromQueryText(String text) {
        String[] arguments = text.trim().toLowerCase().split(" ");
        String module = arguments[0];
        SlashCommandQuery slashCommandQuery = new SlashCommandQuery();
        slashCommandQuery.setModule(module);
        slashCommandQuery.setArguments(List.of(Arrays.copyOfRange(arguments, 1, arguments.length)));
        return slashCommandQuery;
    }

    public MattermostResponse slashResultToResponse(SlashCommandResult slashCommandResult) {
        MattermostResponse mattermostResponse = new MattermostResponse();
        mattermostResponse.setText(slashCommandResult.getText());
        mattermostResponse.setResponseType(slashCommandResult.getResponseType());
        return mattermostResponse;
    }

}
