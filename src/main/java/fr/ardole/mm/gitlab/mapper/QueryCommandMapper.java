package fr.ardole.mm.gitlab.mapper;

import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.exception.UnknownCommandException;
import fr.ardole.mm.gitlab.model.MMQuery;
import fr.ardole.mm.gitlab.slash.command.SlashCommand;
import fr.ardole.mm.gitlab.slash.command.predefined.HelpCommand;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        String command = arguments[0];
        switch (command) {
            case "help":
                return new HelpCommand();
            case "other":
                SlashCommand slashCommand = new SlashCommand() {
                    @Override
                    protected Map<String, Object> executeAndGetDatas() {
                        return null;
                    }

                    @Override
                    public String getMarkdownTemplateName() {
                        return null;
                    }
                };
                List<String> argumentsList = Arrays.asList(Arrays.copyOfRange(arguments, 1, arguments.length));
                slashCommand.setArguments(argumentsList);
                return slashCommand;
            default:
                throw new UnknownCommandException(command);
        }
    }

}
