package fr.ardole.mm.gitlab.controller;

import fr.ardole.mm.gitlab.configuration.SlashConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebResourceConfigurer implements WebMvcConfigurer {

    @Autowired
    SlashConfig slashConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler(slashConfig.getWebImagePath() + "/**")
            .addResourceLocations(slashConfig.getLocalImageStoragePath());
    }
}