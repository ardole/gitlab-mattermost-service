package fr.ardole.mm.gitlab.slash.converter;

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

        String text = converter.convert(getTestMap(), "test.ftl");
        assertThat(text, is(equalTo("# Welcome!\n\n## Welcome Evoklo !\n\nOur latest product: [Vaster 1](https://vaster1vprod.http.moon])")));
    }

    public Map<String, Object> getTestMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("user", "Evoklo");
        Product product = new Product();
        product.setName("Vaster 1");
        product.setUrl("https://vaster1vprod.http.moon");
        map.put("latestProduct", product);
        return map;
    }

     /*
     Product class is taken from https://freemarker.apache.org/docs/pgui_quickstart_all.html
     */
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