package net.ontrack.web.support;

import net.ontrack.core.model.Entity;
import net.ontrack.core.support.MapBuilder;
import net.ontrack.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class DefaultEntityConverter implements EntityConverter {

    protected final ManagementService managementService;

    @Autowired
    public DefaultEntityConverter(ManagementService managementService) {
        this.managementService = managementService;
    }

    @Override
    public int getValidationRunId(String project, String branch, String build, String validationStamp, int run) {
        int buildId = getBuildId(project, branch, build);
        int validationStampId = getValidationStampId(project, branch, validationStamp);
        int runId = getId(Entity.VALIDATION_RUN, String.valueOf(run), MapBuilder.of(Entity.BUILD, buildId).with(Entity.VALIDATION_STAMP, validationStampId).get());
        return runId;
    }

    @Override
    public int getValidationStampId(String project, String branch, String validationStamp) {
        int projectId = getProjectId(project);
        int branchId = getId(Entity.BRANCH, branch, Collections.singletonMap(Entity.PROJECT, projectId));
        int validationStampId = getId(Entity.VALIDATION_STAMP, validationStamp, MapBuilder.of(Entity.PROJECT, projectId).with(Entity.BRANCH, branchId).get());
        return validationStampId;
    }

    @Override
    public int getPromotionLevelId(String project, String branch, String name) {
        int projectId = getProjectId(project);
        int branchId = getId(Entity.BRANCH, branch, Collections.singletonMap(Entity.PROJECT, projectId));
        int promotionLevelId = getId(Entity.PROMOTION_LEVEL, name, MapBuilder.of(Entity.PROJECT, projectId).with(Entity.BRANCH, branchId).get());
        return promotionLevelId;
    }

    @Override
    public int getBuildId(String project, String branch, String validationStamp) {
        int projectId = getProjectId(project);
        int branchId = getId(Entity.BRANCH, branch, Collections.singletonMap(Entity.PROJECT, projectId));
        int buildId = getId(Entity.BUILD, validationStamp, MapBuilder.of(Entity.PROJECT, projectId).with(Entity.BRANCH, branchId).get());
        return buildId;
    }

    @Override
    public int getBranchId(String project, String branch) {
        int projectId = getProjectId(project);
        int branchId = getId(Entity.BRANCH, branch, Collections.singletonMap(Entity.PROJECT, projectId));
        return branchId;
    }

    @Override
    public int getProjectId(String project) {
        int projectId = getId(Entity.PROJECT, project, Collections.<Entity, Integer>emptyMap());
        return projectId;
    }

    protected int getId(Entity entity, String name, Map<Entity, Integer> parentIds) {
        return managementService.getEntityId(entity, name, parentIds);
    }

}
