package fr.ardole.mm.gitlab.slash.command.predefined;

import fr.ardole.mm.gitlab.slash.command.StaticSlashCommand;
import org.springframework.stereotype.Component;

@Component
public class ErrorCommand extends StaticSlashCommand {

    @Override
    public String getMarkdownTemplateName() {
        return "error.ftl";
    }

}
