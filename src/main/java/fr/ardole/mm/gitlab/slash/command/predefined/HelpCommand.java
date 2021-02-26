package fr.ardole.mm.gitlab.slash.command.predefined;

import fr.ardole.mm.gitlab.slash.command.StaticSlashCommand;

public class HelpCommand extends StaticSlashCommand {

    @Override
    public String getMarkdownTemplateName() {
        return "help.ftl";
    }

}
