package ai.h20.feedbackservice.service;

import ai.h20.feedbackservice.dao.FeedbackRepository;
import ai.h20.feedbackservice.model.Feedback;
import ai.h20.feedbackservice.model.dto.FeedbackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackService.class);

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<FeedbackDTO> getAllFeedback(Pageable pageable) {
        Page<Feedback> allFeedback = feedbackRepository.findAll(pageable);
        List<FeedbackDTO> feedbackList = new ArrayList<>();
        allFeedback.forEach(f -> {
            FeedbackDTO feedbackDTO = new FeedbackDTO();
            feedbackDTO.setId(f.getId());
            feedbackDTO.setRating(f.getRating());
            feedbackDTO.setComment(f.getComment());
            feedbackList.add(feedbackDTO);
        });

        return feedbackList;
    }

    public FeedbackDTO saveFeedBack(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setRating(feedbackDTO.getRating());
        feedback.setComment(feedbackDTO.getComment());

        feedback = feedbackRepository.save(feedback);
        feedbackDTO.setId(feedback.getId());
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
