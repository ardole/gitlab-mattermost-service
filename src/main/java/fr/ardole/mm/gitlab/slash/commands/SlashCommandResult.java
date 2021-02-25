package fr.ardole.mm.gitlab.slash.commands;

import java.util.Map;

public class SlashCommandResult {

    private SlashCommand initialCommand;
    private Map<String, Object> datas;

    public SlashCommand getInitialCommand() {
        return initialCommand;
    }

    public void setInitialCommand(SlashCommand initialCommand) {
        this.initialCommand = initialCommand;
    }

    public Map<String, Object> getDatas() {
        return datas;
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }
}
