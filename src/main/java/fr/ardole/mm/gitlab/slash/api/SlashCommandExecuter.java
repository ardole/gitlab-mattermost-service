package fr.ardole.mm.gitlab.slash.api;

import fr.ardole.mm.gitlab.model.SlashCommand;
import fr.ardole.mm.gitlab.model.SlashCommandResult;

public abstract class SlashCommandExecuter {

    public SlashCommandResult execute(SlashCommand slashCommand) {
        SlashCommandResult slashCommandResult = new SlashCommandResult();
        slashCommandResult.setText(executeAndGetText(slashCommand));
        return slashCommandResult;
    }

    protected abstract String executeAndGetText(SlashCommand slashCommand);


}