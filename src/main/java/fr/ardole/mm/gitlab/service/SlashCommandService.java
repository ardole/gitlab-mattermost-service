package fr.ardole.mm.gitlab.service;

import fr.ardole.mm.gitlab.mapper.QueryCommandMapper;
import fr.ardole.mm.gitlab.model.MMQuery;
import fr.ardole.mm.gitlab.model.MMResponse;
import fr.ardole.mm.gitlab.slash.command.SlashCommand;
import fr.ardole.mm.gitlab.slash.command.SlashCommandResult;
import fr.ardole.mm.gitlab.slash.command.StaticSlashCommand;
import fr.ardole.mm.gitlab.slash.converter.SlashResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SlashCommandService {

    @Autowired
    QueryCommandMapper queryCommandMapper;

    @Autowired
    SlashResponseConverter slashResponseConverter;

    public MMResponse run(MMQuery MMQuery) {
        SlashCommand command = queryCommandMapper.queryToCommand(MMQuery);
        SlashCommandResult execute = command.execute();
        return slashResponseConverter.convert(execute);
    }

    public MMResponse run(StaticSlashCommand staticSlashCommand) {
        SlashCommandResult execute = staticSlashCommand.execute();
        return slashResponseConverter.convert(execute);
    }

}
