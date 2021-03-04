package fr.ardole.mm.gitlab.mapper;

import fr.ardole.mm.gitlab.api.MMQuery;
import fr.ardole.mm.gitlab.api.MMResponse;
import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.SlashCommand;
import fr.ardole.mm.gitlab.model.SlashCommandResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Component
public class QueryCommandMapper {

    public SlashCommand queryToCommand(MMQuery MMQuery) {
        String text = MMQuery.getText();
        if (StringUtils.hasText(text)) {
            return extractCommandFromQueryText(text);
        } else {
            throw new SlashCommandException("No text given to command");
        }
    }

    private SlashCommand extractCommandFromQueryText(String text) {
        String[] arguments = text.trim().toLowerCase().split(" ");
        String module = arguments[0];
        SlashCommand slashCommand = new SlashCommand();
        slashCommand.setModule(module);
        slashCommand.setArguments(List.of(Arrays.copyOfRange(arguments, 1, arguments.length)));
        return slashCommand;
    }

    public MMResponse resultToResponse(SlashCommandResult slashCommandResult) {
        MMResponse response = new MMResponse();
        response.setText(slashCommandResult.getText());
        return response;
    }

}
