package fr.ardole.mm.gitlab.slash.command.predefined;

import fr.ardole.mm.gitlab.slash.command.StaticSlashCommand;
import org.springframework.stereotype.Component;

@Component(value = "HelpCommand")
public class HelpCommand extends StaticSlashCommand {

    @Override
    public String getMarkdownTemplateName() {
        return "help.ftl";
    }

}
