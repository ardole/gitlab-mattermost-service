package fr.ardole.mm.gitlab.slash.converter;

import fr.ardole.mm.gitlab.model.MMResponse;
import fr.ardole.mm.gitlab.slash.command.SlashCommandResult;

public interface SlashResponseConverter {

    MMResponse convert(SlashCommandResult slashCommandResult);

}
