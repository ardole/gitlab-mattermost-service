package fr.ardole.mm.gitlab.mapper;

import fr.ardole.mm.gitlab.api.MattermostRequest;
import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.SlashCommand;
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
        SlashCommand slashCommand = queryCommandMapper.queryToCommand(mattermostRequest);
        assertThat(slashCommand.getModule(), is(equalTo("help")));
        assertThat(slashCommand.getArguments(), is(empty()));


        mattermostRequest.setText("help you");
        slashCommand = queryCommandMapper.queryToCommand(mattermostRequest);
        assertThat(slashCommand.getModule(), is(equalTo("help")));
        assertThat(slashCommand.getArguments(), hasSize(1));
        assertThat(slashCommand.getArguments().get(0), is(equalTo("you")));
    }

    @Test
    void invalidQuery() {
        MattermostRequest mattermostRequest = new MattermostRequest();
        SlashCommandException exception = assertThrows(SlashCommandException.class, () -> queryCommandMapper.queryToCommand(mattermostRequest));
        assertThat(exception.getMessage(), is(equalTo("No text given to command")));

        mattermostRequest.setText("");
        exception = assertThrows(SlashCommandException.class, () -> queryCommandMapper.queryToCommand(mattermostRequest));
        assertThat(exception.getMessage(), is(equalTo("No text given to command")));

        mattermostRequest.setText(" ");
        exception = assertThrows(SlashCommandException.class, () -> queryCommandMapper.queryToCommand(mattermostRequest));
        assertThat(exception.getMessage(), is(equalTo("No text given to command")));
    }

}