package ai.h2o.feedback.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private T data;
    private MetaData meta;

    public Response(T data) {
        this.data = data;
    }

}
