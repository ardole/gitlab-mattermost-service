package fr.ardole.mm.gitlab.mapper;

import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.MMQuery;
import fr.ardole.mm.gitlab.slash.commands.HelpCommand;
import fr.ardole.mm.gitlab.slash.commands.SlashCommand;
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
        assertThatIsMappedToHelp(query);


        query.setText("help you");
        assertThatIsMappedToHelp(query);
    }

    private void assertThatIsMappedToHelp(MMQuery query) {
        SlashCommand slashCommand = queryCommandMapper.queryToCommand(query);
        assertThat(slashCommand, instanceOf(HelpCommand.class));
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

        query.setText("hellooooooooooooo");
        exception = assertThrows(SlashCommandException.class, () -> queryCommandMapper.queryToCommand(query));
        assertThat(exception.getMessage(), is(equalTo("Unknown command 'hellooooooooooooo'")));
    }

}