package ai.h2o.feedback.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaData {

    private Integer currentPage;
    private Integer pageSize;
    private Long totalItems;
    private Integer totalPages;

    public static <T> MetaData from (Page<T> page) {
        MetaData metaData = new MetaData();
        metaData.setCurrentPage(page.getPageable().getPageNumber());
        metaData.setPageSize(page.getPageable().getPageSize());
        metaData.setTotalItems(page.getTotalElements());
        metaData.setTotalPages(page.getTotalPages());
        return metaData;
    }

}
