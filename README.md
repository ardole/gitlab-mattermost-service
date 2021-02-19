# Gitlab Mattermost Service

Main goal of this project is to build a slash command service for mattermost, providing:
- List of *applications* (projects) inside a group
- List of environments inside a group
- List of environments for any application
- Test status of an application over simple http request
- Get list of pending merge requests...

## Build it

- With Maven

```
mvn clean install
```

## Configure it

- With an `application.properties`

```
security.mm-token=the-very-secured-value
```

## Start it

### Java
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

Will use these libraries:
- [gitlab4j-api](https://github.com/gitlab4j/gitlab4j-api)
- maybe a template engine