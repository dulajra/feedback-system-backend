package ai.h2o.feedback.service;

import ai.h2o.feedback.dao.FeedbackRepository;
import ai.h2o.feedback.model.Feedback;
import ai.h2o.feedback.model.dto.FeedbackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackService.class);

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Page<Feedback> getAllFeedback(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

    public FeedbackDTO saveFeedBack(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setRating(feedbackDTO.getRating());
        feedback.setComment(feedbackDTO.getComment());
        feedback.setName(feedbackDTO.getName());

        feedback = feedbackRepository.save(feedback);
        feedbackDTO.setId(feedback.getId());
        feedbackDTO.setCreatedAt(feedback.getCreatedAt().getTime());
        return feedbackDTO;
    }

    public void deleteFeedback(Integer id) {
        try {
            feedbackRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex){
            LOGGER.error("No feedback found to delete | Id: {}", id, ex);
        }
    }
}
