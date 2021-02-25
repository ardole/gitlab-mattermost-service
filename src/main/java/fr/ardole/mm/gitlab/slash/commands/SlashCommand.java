package fr.ardole.mm.gitlab.slash.commands;

import java.util.List;

public abstract class SlashCommand {

    private List<String> arguments;

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public abstract SlashCommandResult execute();

    public abstract String getMarkdownTemplateName();
}
