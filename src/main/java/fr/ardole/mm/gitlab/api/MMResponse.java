package fr.ardole.mm.gitlab.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MMResponse {

    private String text;
    private ResponseType responseType = ResponseType.ephemeral;

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

    @Override
    public String toString() {
        return "SlashResponse{" +
                "text='" + text + '\'' +
                ", responseType=" + responseType +
                '}';
    }
}