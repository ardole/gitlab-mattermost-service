package fr.ardole.mm.gitlab.slash.commands;

import java.util.HashMap;
import java.util.Map;

public class HelpCommand extends SlashCommand {

    @Override
    public Map<String, Object> executeAndGetDatas() {
        return new HashMap<>();
    }

    @Override
    public String getMarkdownTemplateName() {
        return "help.ftl";
    }

}
