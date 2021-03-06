package net.ontrack.backend.dao;

import net.ontrack.backend.dao.model.TPromotedRun;
import net.ontrack.core.model.Ack;
import org.joda.time.DateTime;

import java.util.List;

public interface PromotedRunDao {

    TPromotedRun findByBuildAndPromotionLevel(int build, int promotionLevel);

    int createPromotedRun(int build, int promotionLevel, String author, Integer authorId, DateTime creation, String description);

    Integer findBuildByEarliestPromotion(int buildId, int promotionLevelId);

    List<TPromotedRun> findByPromotionLevel(int promotionLevelId, int offset, int count);

    List<TPromotedRun> findByBuild(int buildId);

    Ack remove(int buildId, int promotionLevelId);
}
