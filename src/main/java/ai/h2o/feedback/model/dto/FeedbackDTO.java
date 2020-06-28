package ai.h2o.feedback.model.dto;

import ai.h2o.feedback.model.Feedback;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class FeedbackDTO {

    private Integer id;
    private Integer rating;
    private String name;
    private String comment;

    @JsonProperty("created_at")
    private Long createdAt;

    public static FeedbackDTO from (Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        BeanUtils.copyProperties(feedback, feedbackDTO, "createdAt");
        feedbackDTO.setCreatedAt(feedback.getCreatedAt().getTime());
        return feedbackDTO;
    }

}
