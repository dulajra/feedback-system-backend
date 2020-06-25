package ai.h20.feedbackservice.dao;

import ai.h20.feedbackservice.model.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {
}
