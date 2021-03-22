package fr.ardole.mm.gitlab.exception;

public class BadUsageException extends SlashCommandException {

    public BadUsageException(String message) {
        super(message);
    }
}
