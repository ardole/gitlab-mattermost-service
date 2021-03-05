Your Merge Requests on Gitlab

|Title|Description|CreatedAt|UpdatedAt|Author|Assignee|Source branch|Target branch|Work In Progress|Diverged commits count|State|Merge status|Web Url|Has conflicts|Rebase in progress|Labels|Latest build finished at|Latest build started at|Merge when pipeline succeeds|Force remove source branch|Should remove source branch|Squash|Allow maintainer to push|Blocking discussions resolved|Discussion locked|Merge commit Sha|Squash commitSha|Merge error|Merged at|Merged by|Closed at|Closed by|
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
<#list mergerequests as mr>
|${mr.title}|${mr.description?replace("\n", " ")?replace("*", " ")?replace("\r", "")?truncate(45)}|${mr.createdAt?string('dd/MM/yyyy HH:mm:ss')}|${mr.updatedAt?string('dd/MM/yyyy HH:mm:ss')}|${mr.author.name}|${mr.assignee.name}|${mr.sourceBranch}|${mr.targetBranch}|${mr.workInProgress?string('WIP :construction:', 'No')}|${mr.divergedCommitsCount!"0"}|${mr.state!""}|${mr.mergeStatus!""}|${mr.webUrl!""}|${mr.hasConflicts?string('Conflict :x:', 'No')}|${(mr.rebaseInProgress?string('Rebase :construction:', 'No'))!""}|${mr.labels?join(", ")!""}|${(mr.latestBuildFinishedAt?string('dd/MM/yyyy HH:mm:ss'))!""}|${(mr.latestBuildStartedAt?string('dd/MM/yyyy HH:mm:ss'))!""}|${mr.mergeWhenPipelineSucceeds?string(':white_check_mark:', ':x:')}|${(mr.forceRemoveSourceBranch?string(':white_check_mark:', ':x:'))!''}|${(mr.shouldRemoveSourceBranch?string(':white_check_mark:', ':x:'))!""}|${mr.squash?string(':white_check_mark:', ':x:')}|${(mr.allowMaintainerToPush?string(':white_check_mark:', ':x:'))!""}|${(mr.blockingDiscussionsResolved?string(':white_check_mark:', ':x:'))!""}|${(mr.discussionLocked?string(':white_check_mark:', ':x:'))!""}|${mr.mergeCommitSha!""}|${mr.squashCommitSha!""}|${mr.mergeError!""}|${(mr.mergedAt?string('dd/MM/yyyy HH:mm:ss'))!""}|${(mr.mergedBy.name)!""}|${(mr.closedAt?string('dd/MM/yyyy HH:mm:ss'))!""}|${(mr.closedBy.name)!""}|
</#list>
