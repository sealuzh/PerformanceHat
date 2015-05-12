package eu.cloudwave.wp5.feedbackhandler.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedureExecution;

/**
 * A repository for {@link DbProcedureExecution}'s.
 */
public interface ProcedureExecutionRepository extends PagingAndSortingRepository<DbProcedureExecution, ObjectId>, ProcedureExecutionRepositoryCustom {

}
