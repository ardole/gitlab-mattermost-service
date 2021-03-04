package fr.ardole.mm.gitlab.mapper;

import fr.ardole.mm.gitlab.api.MattermostRequest;
import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.SlashCommandQuery;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryCommandMapperTest {

    QueryCommandMapper queryCommandMapper = new QueryCommandMapper();

    @Test
    void queryToCommand() {
        MattermostRequest mattermostRequest = new MattermostRequest();
        mattermostRequest.setText("help");
        SlashCommandQuery slashCommandQuery = queryCommandMapper.requestToSlashQuery(mattermostRequest);
        assertThat(slashCommandQuery.getModule(), is(equalTo("help")));
        assertThat(slashCommandQuery.getArguments(), is(empty()));


        mattermostRequest.setText("help you");
        slashCommandQuery = queryCommandMapper.requestToSlashQuery(mattermostRequest);
        assertThat(slashCommandQuery.getModule(), is(equalTo("help")));
        assertThat(slashCommandQuery.getArguments(), hasSize(1));
        assertThat(slashCommandQuery.getArguments().get(0), is(equalTo("you")));
    }

    @Test
    void invalidQuery() {
        MattermostRequest mattermostRequest = new MattermostRequest();
        SlashCommandException exception = assertThrows(SlashCommandException.class, () -> queryCommandMapper.requestToSlashQuery(mattermostRequest));
        assertThat(exception.getMessage(), is(equalTo("No text given to command")));

        mattermostRequest.setText("");
        exception = assertThrows(SlashCommandException.class, () -> queryCommandMapper.requestToSlashQuery(mattermostRequest));
        assertThat(exception.getMessage(), is(equalTo("No text given to command")));

        mattermostRequest.setText(" ");
        exception = assertThrows(SlashCommandException.class, () -> queryCommandMapper.requestToSlashQuery(mattermostRequest));
        assertThat(exception.getMessage(), is(equalTo("No text given to command")));
    }

}