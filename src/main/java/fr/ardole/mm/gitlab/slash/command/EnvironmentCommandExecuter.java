package fr.ardole.mm.gitlab.slash.command;

import fr.ardole.mm.gitlab.api.ResponseType;
import fr.ardole.mm.gitlab.configuration.SlashConfig;
import fr.ardole.mm.gitlab.exception.BadUsageException;
import fr.ardole.mm.gitlab.exception.SlashCommandException;
import fr.ardole.mm.gitlab.model.SlashCommandQuery;
import fr.ardole.mm.gitlab.model.SlashCommandResult;
import fr.ardole.mm.gitlab.service.ApiRegistry;
import fr.ardole.mm.gitlab.slash.api.SlashCommandExecuter;
import fr.ardole.mm.gitlab.slash.converter.FreemarkerResponseConverter;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Environment;
import org.gitlab4j.api.models.Project;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Component(value = "EnvironmentCommand")
public class EnvironmentCommandExecuter extends SlashCommandExecuter {

    @Autowired
    FreemarkerResponseConverter slashResponseConverter;

    @Autowired
    ApiRegistry apiRegistry;

    @Autowired
    SlashConfig slashConfig;

    @Override
    protected void executeAndSetResult(SlashCommandQuery slashCommandQuery, SlashCommandResult slashCommandResult) {
        try {
            Map<String, Object> map = new HashMap<>();
            if (slashCommandQuery.getArguments().isEmpty()) {
                throw new BadUsageException("Missing project name argument");
            } else {
                if (slashCommandQuery.getArguments().size() > 1) {
                    String secondArgument = slashCommandQuery.getArguments().get(1);
                    if (slashCommandQuery.getArguments().size() > 2) {
                        String envToCheck = slashCommandQuery.getArguments().get(2);
                        String projectPath = slashCommandQuery.getArguments().get(0);
                        Project project = apiRegistry.getProjectApi().getProject(projectPath);
                        Environment environment = getEnvironmentByName(envToCheck, project);
                        if ("status".equals(secondArgument)) {
                            processHttpStatus(map, projectPath, environment);
                        } else if ("screenshot".equals(secondArgument)) {
                            processScreenShot(map, projectPath, environment);
                        }
                    } else {
                        throw new BadUsageException("Missing specific env if you want a status name argument");
                    }
                } else {
                    String projectPath = slashCommandQuery.getArguments().get(0);
                    Project project = apiRegistry.getProjectApi().getProject(projectPath);
                    List<Environment> environmentsListed = apiRegistry.getEnvironmentsApi().getEnvironments(project.getId());
                    List<Environment> environmentsWithDetails = getEnvironmentsWithDetails(project, environmentsListed);
                    map.put("project", projectPath);
                    map.put("environments", environmentsWithDetails);
                }
            }
            String text = slashResponseConverter.convert(map, getMarkdownTemplateName());
            slashCommandResult.setText(text);
            slashCommandResult.setResponseType(ResponseType.in_channel);
        } catch (GitLabApiException e) {
            throw new SlashCommandException("Unable to retrieve environment list", e);
        }
    }

    private void processHttpStatus(Map<String, Object> map, String projectPath, Environment environment) {
        String status;
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(new HttpGet(environment.getExternalUrl()));
            status = String.valueOf(response.getStatusLine().getStatusCode());
        } catch (Exception e) {
            status = "CONNECTION FAILED";
        }
        EnvironmentStatus environmentStatus = new EnvironmentStatus(environment, status);
        map.put("environmentStatus", environmentStatus);
        map.put("project", projectPath);
    }

    private void processScreenShot(Map<String, Object> map, String projectPath, Environment environment) {
        try {
            WebDriver driver = Browser.getInstance().getDriver();
            try {
                driver.get(environment.getExternalUrl());
            } finally {
                TakesScreenshot scrShot = ((TakesScreenshot) driver);
                File screenContent = scrShot.getScreenshotAs(OutputType.FILE);
                String localImageStorageUri = new URI(slashConfig.getLocalImageStoragePath()).getPath();
                File fileToPersist = new File(localImageStorageUri, UUID.randomUUID() + ".png");
                FileUtils.copyFile(screenContent, fileToPersist);
                map.put("screen", slashConfig.getHost() + slashConfig.getWebImagePath() + "/" + fileToPersist.getName());
                map.put("project", projectPath);
                map.put("environment", environment);
            }
        } catch (URISyntaxException | IOException e) {
            throw new SlashCommandException("Unable to save screenshot", e);
        }
    }

    private Environment getEnvironmentByName(String envToCheck, Project project) throws GitLabApiException {
        List<Environment> environmentsListed = apiRegistry.getEnvironmentsApi().getEnvironments(project.getId());
        for (Environment environment : environmentsListed) {
            if (envToCheck.equals(environment.getName())) {
                return environment;
            }
        }
        return null;
    }

    private List<Environment> getEnvironmentsWithDetails(Project project, List<Environment> environmentsListed) throws GitLabApiException {
        List<Environment> environmentsWithDetails = new ArrayList<>();
        for (Environment environment : environmentsListed) {
            Environment environmentWithDetails = apiRegistry.getEnvironmentsApi().getEnvironment(project.getId(), environment.getId());
            environmentsWithDetails.add(environmentWithDetails);
        }
        return environmentsWithDetails;
    }

    public String getMarkdownTemplateName() {
        return "environments.ftl";
    }
}