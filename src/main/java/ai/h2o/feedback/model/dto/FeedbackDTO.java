package ai.h2o.feedback.model.dto;

import ai.h2o.feedback.model.Feedback;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class FeedbackDTO {

    private Integer id;

    @ApiModelProperty(example = "3")
    private Integer rating;

    @ApiModelProperty(example = "John Doe")
    private String name;

    @ApiModelProperty(example = "Very good product. Can recommend")
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
