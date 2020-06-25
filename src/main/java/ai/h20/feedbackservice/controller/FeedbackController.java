package ai.h20.feedbackservice.controller;

import ai.h20.feedbackservice.model.Response;
import ai.h20.feedbackservice.model.dto.FeedbackDTO;
import ai.h20.feedbackservice.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/feedback")
public class FeedbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public Response<FeedbackDTO> postFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        LOGGER.info("Request received to save feedback | Rating: {} } Comment: {}",
                feedbackDTO.getRating(), feedbackDTO.getComment());
        return new Response<>(feedbackService.saveFeedBack(feedbackDTO));
    }

    @GetMapping
    public Response<List<FeedbackDTO>> getAllFeedback() {
        LOGGER.info("Request received to get all feedback");
        return new Response<>(feedbackService.getAllFeedback());
    }

    @DeleteMapping(value = "{id}")
    public void deleteFeedback(@PathVariable("id") Integer id) {
        LOGGER.info("Request received to delete feedback | Id: {}", id);
        feedbackService.deleteFeedback(id);
    }

}
