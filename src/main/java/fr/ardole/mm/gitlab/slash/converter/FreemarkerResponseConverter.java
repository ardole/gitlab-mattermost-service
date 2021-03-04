package fr.ardole.mm.gitlab.slash.converter;

import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.slash.api.SlashResponseConverter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

@Component
public class FreemarkerResponseConverter implements SlashResponseConverter {

    @Value("${command.templates.path}")
    private String freemarkerTemplateBasePackagePath;

    public void setFreemarkerTemplateBasePackagePath(String freemarkerTemplateBasePackagePath) {
        this.freemarkerTemplateBasePackagePath = freemarkerTemplateBasePackagePath;
    }

    public String convert(Map<String, Object> map, String markdownTemplateName) {
        Configuration freemarkerConfiguration = getFreemarkerConfiguration();
        try {
            Template freemarkerTemplate = freemarkerConfiguration.getTemplate(markdownTemplateName);
            try (ByteArrayOutputStream textResultStream = new ByteArrayOutputStream()) {
                try (Writer textResult = new OutputStreamWriter(textResultStream)) {
                    freemarkerTemplate.process(map, textResult);
                    return textResultStream.toString();
                }
            }
        } catch (IOException | TemplateException e) {
            throw new SlashCommandException("Unable to render text result", e);
        }
    }

    private Configuration getFreemarkerConfiguration() {
        Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_30);
        freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), freemarkerTemplateBasePackagePath);
        freemarkerConfiguration.setDefaultEncoding("UTF-8");
        freemarkerConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freemarkerConfiguration.setLogTemplateExceptions(false);
        freemarkerConfiguration.setWrapUncheckedExceptions(true);
        freemarkerConfiguration.setFallbackOnNullLoopVariable(false);
        return freemarkerConfiguration;
    }

}
