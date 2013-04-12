package net.ontrack.extension.svnexplorer.service;

import net.ontrack.extension.svnexplorer.model.ChangeLogFiles;
import net.ontrack.extension.svnexplorer.model.ChangeLogIssues;
import net.ontrack.extension.svnexplorer.model.ChangeLogRevisions;
import net.ontrack.extension.svnexplorer.model.ChangeLogSummary;

public interface SVNExplorerService {

    ChangeLogSummary getChangeLogSummary(int branch, int from, int to);

    ChangeLogRevisions getChangeLogRevisions(ChangeLogSummary summary);

    ChangeLogIssues getChangeLogIssues(ChangeLogSummary summary, ChangeLogRevisions revisions);

    ChangeLogFiles getChangeLogFiles(ChangeLogSummary summary, ChangeLogRevisions revisions);
}
