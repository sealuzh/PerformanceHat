package eu.cloudwave.wp5.feedbackhandler.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;

/**
 * A repository for {@link DbApplication}'s.
 */
public interface ApplicationRepository extends PagingAndSortingRepository<DbApplication, ObjectId>, ApplicationRepositoryCustom {

}
