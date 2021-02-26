package fr.ardole.mm.gitlab.slash.command;

import java.util.List;
import java.util.Map;

public abstract class SlashCommand {

    private List<String> arguments;

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public SlashCommandResult execute() {
        SlashCommandResult slashCommandResult = new SlashCommandResult();
        slashCommandResult.setInitialCommand(this);
        slashCommandResult.setDatas(executeAndGetDatas());
        return slashCommandResult;
    }

    protected abstract Map<String, Object> executeAndGetDatas();

    public abstract String getMarkdownTemplateName();
}
