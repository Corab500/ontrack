package net.ontrack.backend.db;

public interface SQL {

	// Project groups
	
	String PROJECT_GROUP_LIST = "SELECT ID, NAME, DESCRIPTION FROM PROJECT_GROUP ORDER BY NAME";
	
	String PROJECT_GROUP_CREATE = "INSERT INTO PROJECT_GROUP (NAME, DESCRIPTION) VALUES (:name, :description)";
	
	// Projects
	
	String PROJECT = "SELECT * FROM PROJECT WHERE ID = :id";
	
	String PROJECT_LIST = "SELECT ID, NAME, DESCRIPTION FROM PROJECT ORDER BY NAME";
	
	String PROJECT_CREATE = "INSERT INTO PROJECT (NAME, DESCRIPTION) VALUES (:name, :description)";
	
	String PROJECT_DELETE = "DELETE FROM PROJECT WHERE ID = :id";
	
	// Branches
	
	String BRANCH = "SELECT * FROM BRANCH WHERE ID = :id";
	
	String BRANCH_LIST = "SELECT ID, PROJECT, NAME, DESCRIPTION FROM BRANCH WHERE PROJECT = :project ORDER BY NAME";
	
	String BRANCH_CREATE = "INSERT INTO BRANCH (PROJECT, NAME, DESCRIPTION) VALUES (:project, :name, :description)";
	
	// Builds
	
	String BUILD = "SELECT * FROM BUILD WHERE ID = :id";
	
	String BUILD_LIST = "SELECT ID, BRANCH, NAME, DESCRIPTION FROM BUILD WHERE BRANCH = :branch ORDER BY ID DESC";
	
	String BUILD_CREATE = "INSERT INTO BUILD (BRANCH, NAME, DESCRIPTION) VALUES (:branch, :name, :description)";
	
	// Validation stamps
	
	long VALIDATION_STAMP_IMAGE_MAXSIZE = 4096;
	
	String VALIDATION_STAMP = "SELECT ID, BRANCH, NAME, DESCRIPTION FROM VALIDATION_STAMP WHERE ID = :id";
	
	String VALIDATION_STAMP_LIST = "SELECT ID, BRANCH, NAME, DESCRIPTION FROM VALIDATION_STAMP WHERE BRANCH = :branch ORDER BY NAME";
	
	String VALIDATION_STAMP_CREATE = "INSERT INTO VALIDATION_STAMP (BRANCH, NAME, DESCRIPTION) VALUES (:branch, :name, :description)";
	
	String VALIDATIONSTAMP_IMAGE_UPDATE = "UPDATE VALIDATION_STAMP SET IMAGE = :image WHERE ID = :id";
	
	String VALIDATIONSTAMP_IMAGE = "SELECT IMAGE FROM VALIDATION_STAMP WHERE ID = :id";
	
	// Validation runs
	
	String VALIDATION_RUN = "SELECT R.*, (SELECT COUNT(ID) FROM VALIDATION_RUN WHERE BUILD = R.BUILD AND VALIDATION_STAMP = R.VALIDATION_STAMP AND ID <= R.ID) AS INDEXNB FROM VALIDATION_RUN R WHERE R.ID = :id";
	
	String VALIDATION_RUN_CREATE = "INSERT INTO VALIDATION_RUN (BUILD, VALIDATION_STAMP, DESCRIPTION) VALUES (:build, :validationStamp, :description)";

    String VALIDATION_RUN_FOR_BUILD_AND_STAMP = "SELECT ID FROM VALIDATION_RUN WHERE BUILD = :build AND VALIDATION_STAMP = :validationStamp ORDER BY ID DESC";
	
	// Validation run statuses
	
	String VALIDATION_RUN_STATUS_CREATE = "INSERT INTO VALIDATION_RUN_STATUS (VALIDATION_RUN, STATUS, DESCRIPTION, AUTHOR, AUTHOR_ID, STATUS_TIMESTAMP) VALUES (:validationRun, :status, :description, :author, :authorId, :statusTimestamp)";

    String VALIDATION_RUN_STATUS_LAST = "SELECT * FROM VALIDATION_RUN_STATUS WHERE VALIDATION_RUN = :id ORDER BY ID DESC LIMIT 1";

	// Audit
	
	String EVENT_NAME = "SELECT %s FROM %s WHERE ID = :id";

	String EVENT_VALUE_INSERT = "INSERT INTO EVENT_VALUES (EVENT, PROP_NAME, PROP_VALUE) VALUES (:id, :name, :value)";
	
	String EVENT_VALUE_LIST = "SELECT PROP_NAME, PROP_VALUE FROM EVENT_VALUES WHERE EVENT = :id";	
}
