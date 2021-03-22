# Gitlab Mattermost Service

[![Java CI with Maven](https://github.com/ardole/gitlab-mattermost-service/actions/workflows/maven.yml/badge.svg)](https://github.com/ardole/gitlab-mattermost-service/actions/workflows/maven.yml) [![codecov](https://codecov.io/gh/ardole/gitlab-mattermost-service/branch/master/graph/badge.svg?token=KTN11YDGE1)](https://codecov.io/gh/ardole/gitlab-mattermost-service)

Main goal of this project is to build a slash command service for mattermost, providing:
- List of *applications* (projects) inside a group
- List of environments inside a group
- List of environments for any application
- Test status of an application over simple http request
- Get list of pending merge requests...

## Commands

|Command|Description|
|---|---|
|`/gitlab help`|Print this help|
|`/gitlab project [detail]`|List projects information|
|`/gitlab mergerequest [opened,closed,locked,merged,all]`|List merge requests (default list opened merge-requests only)|
|`/gitlab env PROJECT_NAME`|List environments of a project identified named *PROJECT_NAME*|
|`/gitlab env PROJECT_NAME [status,screenshot] ENV_NAME`|Print status or screenshot of given *ENV_NAME* of project *PROJECT_NAME*|

## Build it

- With Maven

```
mvn clean install
```

## Configure it

- With an `application.properties`

```
# If you would like to configure the mm token in plain text
security.mm-token=the-very-secured-value
# If you would like to configure the mm token with hash value (bcrypt)
security.mm-token-bcrypted=$2y$12$U6DuGjXkL7oNPsfLw8KCpeXK2cT6qfMhNriHEHanrsG2yJx9PlmN2
slash.gitlab-host-url=https://gitlab.com
slash.gitlab-personal-access-token=your-personnal-token
```

## Start it

### Java

- Local way

```
mvn spring-boot:run
```

### Docker
### OpenShift


## Start and test it with Mattermost

- Using Compose and the [docker-compose.yml](./docker-compose.yml) file.

```
docker-compose up -d
```

## How it works

For now its a simple SpringBoot Project, but it may be in the future:
- A quarkus project
- A Ruby project
- A Go project
- Or anything else that I want to try

It uses these libraries:
- [gitlab4j-api](https://github.com/gitlab4j/gitlab4j-api)
- [Freemarker](https://freemarker.apache.org/)

## WIP

- NOW : Naive access using token (can be unsecured)
- Tomorrow : Restrict access to data according to their rights on Gitlab
Simple use of `gitLabApi.setSudoAsId()` -> must be admin of platform
and users of Gitlab and mattermost must be synchronized -> and 
`ApiRegistry` will be redesigned
- Allow two mode of authentication (mapped or not)

## TODO

- remove error template according to right http status
- refactor configuration naming
- synchronize browser singleton
- add a cache feature -> allow to force no cache on command
- faire un test intégration sur le storage le brancher sur classpath dans src/test/resouces et vérifier qu'il ser tbien
- Use external image storage rather than local served by this service (not used to be a u2m usage)
- Allow multiple token for multiple team
- Allow multiple alias for commands
- Automatic mapper of commands according to configuration
- Automatic mapper of commands according to class-loader
- Auto generation of help according to available commands
It will become soon:
- A generic service with dependencies injection of command
