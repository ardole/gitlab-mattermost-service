package fr.ardole.mm.gitlab.slash.predefined;

import fr.ardole.mm.gitlab.model.SlashCommandResult;
import fr.ardole.mm.gitlab.slash.converter.FreemarkerResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component(value = "ErrorCommand")
public class ErrorCommandResult extends SlashCommandResult {

    @Autowired
    FreemarkerResponseConverter slashResponseConverter;

    @Override
    public String getText() {
        return slashResponseConverter.convert(new HashMap<>(), "error.ftl");
    }
}
