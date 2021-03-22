package fr.ardole.mm.gitlab.slash.command;

import org.gitlab4j.api.models.Environment;

public class EnvironmentStatus {

    Environment environment;
    String status;

    public EnvironmentStatus(Environment environment, String status) {
        this.environment = environment;
        this.status = status;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
