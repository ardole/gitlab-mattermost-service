package fr.ardole.mm.gitlab;

import fr.ardole.mm.gitlab.configuration.SecurityConfig;
import fr.ardole.mm.gitlab.configuration.SlashConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({SecurityConfig.class, SlashConfig.class})
public class GitlabMattermostService {

	public static void main(String[] args) {
		SpringApplication.run(GitlabMattermostService.class, args);
	}

}
