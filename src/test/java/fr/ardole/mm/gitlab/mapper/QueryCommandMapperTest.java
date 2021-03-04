package fr.ardole.mm.gitlab.mapper;

import fr.ardole.mm.gitlab.api.MMQuery;
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
        MMQuery query = new MMQuery();
        query.setText("help");
        SlashCommand slashCommand = queryCommandMapper.queryToCommand(query);
        assertThat(slashCommand.getModule(), is(equalTo("help")));
        assertThat(slashCommand.getArguments(), is(empty()));


        query.setText("help you");
        slashCommand = queryCommandMapper.queryToCommand(query);
        assertThat(slashCommand.getModule(), is(equalTo("help")));
        assertThat(slashCommand.getArguments(), hasSize(1));
        assertThat(slashCommand.getArguments().get(0), is(equalTo("you")));
    }

    @Test
    void invalidQuery() {
        MMQuery query = new MMQuery();
        SlashCommandException exception = assertThrows(SlashCommandException.class, () -> queryCommandMapper.queryToCommand(query));
        assertThat(exception.getMessage(), is(equalTo("No text given to command")));

        query.setText("");
        exception = assertThrows(SlashCommandException.class, () -> queryCommandMapper.queryToCommand(query));
        assertThat(exception.getMessage(), is(equalTo("No text given to command")));

        query.setText(" ");
        exception = assertThrows(SlashCommandException.class, () -> queryCommandMapper.queryToCommand(query));
        assertThat(exception.getMessage(), is(equalTo("No text given to command")));
    }

}