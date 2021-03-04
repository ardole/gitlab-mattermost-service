package fr.ardole.mm.gitlab.service;

import fr.ardole.mm.gitlab.api.MattermostRequest;
import fr.ardole.mm.gitlab.api.MattermostResponse;
import fr.ardole.mm.gitlab.mapper.QueryCommandMapper;
import fr.ardole.mm.gitlab.model.SlashCommandQuery;
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

    public MattermostResponse run(MattermostRequest mattermostRequest) {
        SlashCommandQuery command = queryCommandMapper.requestToSlashQuery(mattermostRequest);
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
        return queryCommandMapper.slashResultToResponse(result);
    }

    public MattermostResponse getErrorCommandResult() {
        return queryCommandMapper.slashResultToResponse(errorCommandResult);
    }

    public MattermostResponse getHelpCommandResult() {
        return queryCommandMapper.slashResultToResponse(helpCommandResult);
    }
}
