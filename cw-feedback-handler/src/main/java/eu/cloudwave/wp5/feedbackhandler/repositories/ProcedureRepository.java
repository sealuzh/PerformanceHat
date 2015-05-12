package eu.cloudwave.wp5.feedbackhandler.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedure;

/**
 * A repository for {@link DbProcedure}'s.
 */
public interface ProcedureRepository extends PagingAndSortingRepository<DbProcedure, ObjectId> {

}
