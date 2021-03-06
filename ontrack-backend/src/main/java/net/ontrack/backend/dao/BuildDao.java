package net.ontrack.backend.dao;

import net.ontrack.backend.dao.model.TBuild;
import net.ontrack.core.model.Ack;
import net.ontrack.core.model.BuildFilter;
import net.ontrack.core.model.Status;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BuildDao {

    List<TBuild> findByBranch(int branch, int offset, int count);

    TBuild getById(int id);

    int createBuild(int branch, String name, String description);

    List<TBuild> query(int branch, BuildFilter filter);

	TBuild findLastBuildWithValidationStamp(int validationStamp, Set<Status> statuses);

	TBuild findLastBuildWithPromotionLevel(int promotionLevel);

    TBuild findLastByBranch(int branch);

    Collection<TBuild> findByName(String name);

    Integer findByBrandAndName(int branchId, String buildName);

    Integer findBuildAfterUsingNumericForm(int branchId, String buildName);

    Ack delete(int buildId);

    Ack updateBuild(int buildId, String name, String description);
}
