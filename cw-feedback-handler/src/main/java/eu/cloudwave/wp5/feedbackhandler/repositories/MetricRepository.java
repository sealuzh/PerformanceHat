package eu.cloudwave.wp5.feedbackhandler.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

import eu.cloudwave.wp5.feedbackhandler.model.db.DbMetric;

/**
 * A repository for {@link DbMetric}'s.
 */
public interface MetricRepository extends PagingAndSortingRepository<DbMetric, ObjectId>, MetricRepositoryCustom {

}
