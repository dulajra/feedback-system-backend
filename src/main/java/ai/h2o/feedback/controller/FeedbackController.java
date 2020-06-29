package ai.h2o.feedback.controller;

import ai.h2o.feedback.model.Feedback;
import ai.h2o.feedback.model.MetaData;
import ai.h2o.feedback.model.Response;
import ai.h2o.feedback.model.dto.FeedbackDTO;
import ai.h2o.feedback.service.FeedbackService;
import ai.h2o.feedback.utils.RequestUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping(path = "/feedback")
public class FeedbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @ApiOperation(value = "Post New Feedback", notes = "Saves a new feedback", response =
            Response.class)
    @PostMapping
    public Response<FeedbackDTO> postFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        LOGGER.info("Request received to save feedback | Rating: {} | Comment: {}",
                feedbackDTO.getRating(), feedbackDTO.getComment());
        return new Response<>(feedbackService.saveFeedBack(feedbackDTO));
    }

    @ApiOperation(value = "Fetch All Feedback", notes = "Retrieves all feedback with pagination", response =
            Response.class)
    @GetMapping
    public Response<List<FeedbackDTO>> getAllFeedback(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        LOGGER.info("Request received to get all feedback");
        Page<Feedback> feedbackPage = feedbackService.getAllFeedback(RequestUtils.getPageable(page, size));
        List<FeedbackDTO> feedbackList = feedbackPage.stream().map(FeedbackDTO::from).collect(Collectors.toList());

        Response<List<FeedbackDTO>> response = new Response<>(feedbackList);
        response.setMeta(MetaData.from(feedbackPage));
        return response;
    }

    @ApiOperation(value = "Delete Feedback", notes = "Delete feedback with given ID", response =
            Response.class)
    @DeleteMapping(value = "{id}")
    public void deleteFeedback(@PathVariable("id") Integer id) {
        LOGGER.info("Request received to delete feedback | Id: {}", id);
        feedbackService.deleteFeedback(id);
    }

}
