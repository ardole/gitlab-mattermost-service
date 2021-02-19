package fr.ardole.mm.gitlab;

import fr.ardole.mm.gitlab.configuration.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecurityConfig.class)
public class GitlabSlashCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitlabSlashCommandApplication.class, args);
	}

}
