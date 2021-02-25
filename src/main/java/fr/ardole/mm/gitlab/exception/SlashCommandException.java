package fr.ardole.mm.gitlab.exception;

public class SlashCommandException extends RuntimeException {

    public SlashCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
