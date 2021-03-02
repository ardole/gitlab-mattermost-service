package fr.ardole.mm.gitlab.mapper;

import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.exception.UnknownCommandException;
import fr.ardole.mm.gitlab.model.MMQuery;
import fr.ardole.mm.gitlab.slash.command.SlashCommand;
import fr.ardole.mm.gitlab.slash.command.predefined.HelpCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class QueryCommandMapperTest {

    @Autowired
    QueryCommandMapper queryCommandMapper;

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
        exception = assertThrows(UnknownCommandException.class, () -> queryCommandMapper.queryToCommand(query));
        assertThat(exception.getMessage(), is(equalTo("Unknown command 'hellooooooooooooo'")));
    }

}