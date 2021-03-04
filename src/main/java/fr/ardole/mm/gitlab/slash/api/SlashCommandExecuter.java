package fr.ardole.mm.gitlab.slash.api;

import fr.ardole.mm.gitlab.model.SlashCommandQuery;
import fr.ardole.mm.gitlab.model.SlashCommandResult;

public abstract class SlashCommandExecuter {

    public SlashCommandResult execute(SlashCommandQuery slashCommandQuery) {
        SlashCommandResult slashCommandResult = new SlashCommandResult();
        slashCommandResult.setText(executeAndGetText(slashCommandQuery));
        return slashCommandResult;
    }

    protected abstract String executeAndGetText(SlashCommandQuery slashCommandQuery);


}