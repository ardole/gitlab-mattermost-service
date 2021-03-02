package fr.ardole.mm.gitlab.mapper;

import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.exception.UnknownCommandException;
import fr.ardole.mm.gitlab.model.MMQuery;
import fr.ardole.mm.gitlab.slash.command.SlashCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class QueryCommandMapper {

    @Autowired
    private ApplicationContext context;

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
                return context.getBean("HelpCommand", SlashCommand.class);
            case "project":
                return context.getBean("ProjectCommand", SlashCommand.class);
            default:
                throw new UnknownCommandException(command);
        }
    }

}
