package ai.h2o.feedback.service;

import ai.h2o.feedback.dao.FeedbackRepository;
import ai.h2o.feedback.model.Feedback;
import ai.h2o.feedback.model.MetaData;
import ai.h2o.feedback.model.Response;
import ai.h2o.feedback.model.dto.FeedbackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackService.class);

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Response<List<FeedbackDTO>> getAllFeedback(Pageable pageable) {
        Page<Feedback> page = feedbackRepository.findAll(pageable);
        List<FeedbackDTO> feedbackList = page.stream().map(feedback -> {
            FeedbackDTO feedbackDTO = new FeedbackDTO();
            feedbackDTO.setId(feedback.getId());
            feedbackDTO.setCreatedAt(feedback.getCreatedAt().getTime());
            feedbackDTO.setName(feedback.getName());
            feedbackDTO.setRating(feedback.getRating());
            feedbackDTO.setComment(feedback.getComment());
            return feedbackDTO;
        }).collect(Collectors.toList());

        Response<List<FeedbackDTO>> response = new Response<>(feedbackList);
        response.setMeta(MetaData.from(page));

        return response;
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
