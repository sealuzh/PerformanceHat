package eu.cloudwave.wp5.feedbackhandler.model.db;

import org.bson.types.ObjectId;

/**
 * Base interface for all MongoDB-specific entities.
 */
public interface DbEntity {

  /**
   * Returns the id of the entity. The id is assigned by the DBS.
   * 
   * @return the id of the entity
   */
  public ObjectId getId();

}
