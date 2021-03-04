package fr.ardole.mm.gitlab.model;

import fr.ardole.mm.gitlab.api.ResponseType;

public class SlashCommandResult {

    private String text;
    private ResponseType responseType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
}
