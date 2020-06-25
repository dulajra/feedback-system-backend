package ai.h20.feedbackservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDTO {

    private Integer id;
    private Integer rating;
    private String comment;

}
