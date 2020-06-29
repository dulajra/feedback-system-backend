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

    /**
     * Extracts pagination information from @{@link Page} and set to meta field in reponse.
     * @param page @{@link Page} object from pagination if any
     * @return @{@link MetaData} with pagination information
     */
    public static <T> MetaData from (Page<T> page) {
        MetaData metaData = new MetaData();
        metaData.setCurrentPage(page.getPageable().getPageNumber() + 1);
        metaData.setPageSize(page.getPageable().getPageSize());
        metaData.setTotalItems(page.getTotalElements());
        metaData.setTotalPages(page.getTotalPages());
        return metaData;
    }

}
