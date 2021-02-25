package fr.ardole.mm.gitlab.mapper;

import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.MMQuery;
import fr.ardole.mm.gitlab.slash.commands.HelpCommand;
import fr.ardole.mm.gitlab.slash.commands.SlashCommand;
import fr.ardole.mm.gitlab.slash.commands.SlashCommandResult;
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

    private SlashCommand extractCommandFromQueryText(String command) {
        String[] arguments = command.trim().toLowerCase().split(" ");
        String module = arguments[0];
        switch (module) {
            case "help":
                return new HelpCommand();
            case "other":
                SlashCommand slashCommand = new SlashCommand() {
                    @Override
                    public SlashCommandResult execute() {
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
                throw new SlashCommandException("Unknown command '" + module + "'");
        }
    }

}
