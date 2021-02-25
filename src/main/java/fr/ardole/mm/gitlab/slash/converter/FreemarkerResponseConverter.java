package fr.ardole.mm.gitlab.slash.converter;

import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.MMResponse;
import fr.ardole.mm.gitlab.slash.commands.SlashCommandResult;
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

@Component
public class FreemarkerResponseConverter implements SlashResponseConverter {

    @Value("${command.templates.path}")
    private String freemarkerTemplateBasePackagePath;

    public void setFreemarkerTemplateBasePackagePath(String freemarkerTemplateBasePackagePath) {
        this.freemarkerTemplateBasePackagePath = freemarkerTemplateBasePackagePath;
    }

    public MMResponse convert(SlashCommandResult slashCommandResult) {
        Configuration freemarkerConfiguration = getFreemarkerConfiguration();
        try {
            Template freemarkerTemplate = freemarkerConfiguration.getTemplate(slashCommandResult.getInitialCommand().getMarkdownTemplateName());
            try (ByteArrayOutputStream textResultStream = new ByteArrayOutputStream()) {
                try (Writer textResult = new OutputStreamWriter(textResultStream)) {
                    freemarkerTemplate.process(slashCommandResult.getDatas(), textResult);
                    MMResponse response = new MMResponse();
                    response.setText(textResultStream.toString());
                    return response;
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
