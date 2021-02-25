package fr.ardole.mm.gitlab.slash.commands;

public class HelpCommand extends SlashCommand {

    @Override
    public SlashCommandResult execute() {
        return new SlashCommandResult();
    }

    @Override
    public String getMarkdownTemplateName() {
        return "help.ftl";
    }

}
