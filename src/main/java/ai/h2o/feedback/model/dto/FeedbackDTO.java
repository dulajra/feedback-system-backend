package ai.h2o.feedback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDTO {

    private Integer id;
    private Integer rating;
    private String name;
    private String comment;

    @JsonProperty("created_at")
    private Long createdAt;

}
