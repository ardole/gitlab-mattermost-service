package fr.ardole.mm.gitlab.slash.command;

import java.util.HashMap;
import java.util.Map;

public abstract class StaticSlashCommand extends SlashCommand {

    @Override
    protected Map<String, Object> executeAndGetDatas() {
        return new HashMap<>();
    }

}
