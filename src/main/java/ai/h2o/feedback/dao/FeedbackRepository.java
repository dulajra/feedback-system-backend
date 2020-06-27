package ai.h2o.feedback.dao;

import ai.h2o.feedback.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

    Page<Feedback> findAll(Pageable pageable);

}
