<#if environments??>
Environments for project **${project}**

|Name|Slug|External URL|From branch|Created at|Updated at|Last deployment status|Expected state|Deployed by|Deployed job link|Deployed by runner|Last commit author|Last commit date|Last commit message|Last commit link|
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
<#list environments as env>
|${env.name}|${env.slug!""}|${env.externalUrl!""}|${(env.lastDeployment.ref)!""}|${(env.lastDeployment.createdAt?string('dd/MM/yyyy HH:mm:ss'))!""}|${(env.lastDeployment.updatedAt?string('dd/MM/yyyy HH:mm:ss'))!""}|${(env.lastDeployment.status)!""}|${env.state!""}|${(env.lastDeployment.deployable.user.name)!""}|${(env.lastDeployment.deployable.webUrl)!""}|${(env.lastDeployment.deployable.runner.name)!""}|${(env.lastDeployment.deployable.commit.authorName)!""}|${(env.lastDeployment.deployable.commit.authoredDate?string('dd/MM/yyyy HH:mm:ss'))!""}|${(env.lastDeployment.deployable.commit.message)!""}|${(env.lastDeployment.deployable.commit.webUrl)!""}|
</#list>
</#if>
<#if screen??>
Capture of ${environment.name} taken on ${environment.externalUrl!""}

![${environment.externalUrl!""}](${screen})
</#if>

<#if environmentStatus??>
|Name|External URL|Http Status|
|---|---|---|
|${environmentStatus.environment.name}|${environmentStatus.environment.externalUrl!""}|${environmentStatus.status}|
</#if>

