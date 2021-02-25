package fr.ardole.mm.gitlab.slash.converter;

import fr.ardole.mm.gitlab.model.MMResponse;
import fr.ardole.mm.gitlab.model.ResponseType;
import fr.ardole.mm.gitlab.slash.commands.SlashCommand;
import fr.ardole.mm.gitlab.slash.commands.SlashCommandResult;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


class FreemarkerResponseConverterTest {

    @Test
    void convertFromTemplate() {
        FreemarkerResponseConverter converter = new FreemarkerResponseConverter();
        converter.setFreemarkerTemplateBasePackagePath("/test-templates");

        TestCommand testCommand = new TestCommand();
        SlashCommandResult slashCommandResult = testCommand.execute();

        MMResponse mmResponse = converter.convert(slashCommandResult);
        assertThat(mmResponse.getResponseType(), is(equalTo(ResponseType.in_channel)));
        assertThat(mmResponse.getText(), is(equalTo("# Welcome!\r\n\r\n## Welcome Evoklo !\r\n\r\nOur latest product: [Vaster 1](https://vaster1vprod.http.moon])")));
    }

    /*
     This class is test sample, product class is taken from https://freemarker.apache.org/docs/pgui_quickstart_all.html
     */
    public static class TestCommand extends SlashCommand {

        @Override
        public SlashCommandResult execute() {
            Map<String, Object> map = new HashMap<>();
            map.put("user", "Evoklo");
            Product product = new Product();
            product.setName("Vaster 1");
            product.setUrl("https://vaster1vprod.http.moon");
            map.put("latestProduct", product);
            SlashCommandResult slashCommandResult = new SlashCommandResult();
            slashCommandResult.setInitialCommand(this);
            slashCommandResult.setDatas(map);
            return slashCommandResult;
        }

        @Override
        public String getMarkdownTemplateName() {
            return "test.ftl";
        }

        public static class Product {

            private String url;
            private String name;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }

    }


}