package net.ontrack.extension.svn.dao;

import net.ontrack.extension.svn.dao.model.TRevision;
import org.joda.time.DateTime;

import java.util.List;

public interface RevisionDao {

    long getLast();

    void addRevision(long revision, String author, DateTime date, String dbMessage, String branch);

    void deleteAll();

    void addMergedRevisions(long revision, List<Long> mergedRevisions);

    TRevision getLastRevision();

    TRevision get(long revision);
}
