package fr.ardole.mm.gitlab.exception;

public class UnknownCommandException extends SlashCommandException {

    public UnknownCommandException(String command) {
        super("Unknown command '" + command + "'");
    }

}
