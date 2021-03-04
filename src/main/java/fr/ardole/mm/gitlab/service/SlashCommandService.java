package fr.ardole.mm.gitlab.service;

import fr.ardole.mm.gitlab.api.MMQuery;
import fr.ardole.mm.gitlab.api.MMResponse;
import fr.ardole.mm.gitlab.mapper.QueryCommandMapper;
import fr.ardole.mm.gitlab.model.SlashCommand;
import fr.ardole.mm.gitlab.model.SlashCommandResult;
import fr.ardole.mm.gitlab.slash.api.SlashCommandExecuter;
import fr.ardole.mm.gitlab.slash.predefined.ErrorCommandResult;
import fr.ardole.mm.gitlab.slash.predefined.HelpCommandResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SlashCommandService {

    @Autowired
    QueryCommandMapper queryCommandMapper;

    @Autowired
    ErrorCommandResult errorCommandResult;

    @Autowired
    HelpCommandResult helpCommandResult;

    @Autowired
    ApplicationContext context;

    public MMResponse run(MMQuery mmQuery) {
        SlashCommand command = queryCommandMapper.queryToCommand(mmQuery);
        String module = command.getModule();
        SlashCommandExecuter bean;
        switch (module) {
            case "help":
                return getHelpCommandResult();
            case "project":
                bean = context.getBean("ProjectCommand", SlashCommandExecuter.class);
                break;
            default:
                return getErrorCommandResult();
        }
        SlashCommandResult result = bean.execute(command);
        return queryCommandMapper.resultToResponse(result);
    }

    public MMResponse getErrorCommandResult() {
        return queryCommandMapper.resultToResponse(errorCommandResult);
    }

    public MMResponse getHelpCommandResult() {
        return queryCommandMapper.resultToResponse(helpCommandResult);
    }
}
