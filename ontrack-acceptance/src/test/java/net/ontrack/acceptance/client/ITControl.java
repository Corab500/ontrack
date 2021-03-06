package net.ontrack.acceptance.client;

import net.ontrack.client.ControlUIClient;
import net.ontrack.client.support.ControlClientCall;
import net.ontrack.core.model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ITControl extends AbstractIT {

    @Test
    public void createBuild() {
        // Prerequisites
        final BranchSummary branch = data.doCreateBranch();
        // Creates a build
        final String buildName = data.uid("BLD");
        BuildSummary build = data.asAdmin(new ControlClientCall<BuildSummary>() {
            @Override
            public BuildSummary onCall(ControlUIClient client) {
                return client.createBuild(
                        branch.getProject().getName(),
                        branch.getName(),
                        new BuildCreationForm(
                                buildName,
                                "Test build",
                                PropertiesCreationForm.create()
                        )
                );
            }
        });
        // Checks
        assertNotNull(build);
        assertEquals(buildName, build.getName());
        assertEquals(branch.getName(), build.getBranch().getName());
        assertEquals(branch.getProject().getName(), build.getBranch().getProject().getName());
    }

    @Test
    public void createBuild_twice() {
        // Prerequisites
        final BuildSummary build = data.doCreateBuild();
        // Call
        data.assertClientMessage(
                new Runnable() {
                    @Override
                    public void run() {
                        data.asAdmin(new ControlClientCall<BuildSummary>() {
                            @Override
                            public BuildSummary onCall(ControlUIClient client) {
                                return client.createBuild(
                                        build.getBranch().getProject().getName(),
                                        build.getBranch().getName(),
                                        new BuildCreationForm(
                                                build.getName(),
                                                "Test build",
                                                PropertiesCreationForm.create()
                                        )
                                );
                            }
                        });
                    }
                },
                "Build with name \"%s\" already exists.", build.getName());
    }

    @Test
    public void createValidationRun() {
        // Prerequisites
        final ValidationStampSummary validationStamp = data.doCreateValidationStamp();
        final BuildSummary build = data.doCreateBuild(validationStamp.getBranch());
        // Creates a validation run
        ValidationRunSummary validationRun = data.asAdmin(new ControlClientCall<ValidationRunSummary>() {
            @Override
            public ValidationRunSummary onCall(ControlUIClient client) {
                return client.createValidationRun(
                        build.getBranch().getProject().getName(),
                        build.getBranch().getName(),
                        build.getName(),
                        validationStamp.getName(),
                        new ValidationRunCreationForm(
                                Status.PASSED,
                                "Test validation run",
                                PropertiesCreationForm.create()
                        )
                );
            }
        });
        // Checks
        assertNotNull(validationRun);
        assertEquals(build.getBranch().getProject().getName(), validationRun.getBuild().getBranch().getProject().getName());
        assertEquals(build.getBranch().getName(), validationRun.getBuild().getBranch().getName());
        assertEquals(build.getName(), validationRun.getBuild().getName());
        assertEquals(validationStamp.getName(), validationRun.getValidationStamp().getName());
        assertEquals(1, validationRun.getRunOrder());
        assertEquals(Status.PASSED, validationRun.getValidationRunStatus().getStatus());
    }
}
