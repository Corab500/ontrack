package org.jenkinsci.plugins.ontrack;

import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;

public abstract class AbstractOntrackNotifier extends Notifier {

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

}
