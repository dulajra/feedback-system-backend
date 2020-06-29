package ai.h2o.feedback.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class RequestUtils {

    private static final int MAX_PAGE_SIZE = 1000;

    private RequestUtils() {
    }

    /**
     * Validated the pagination parameters and reset to defaults in case not valid
     * @param page - No of the page (Starts from 1)
     * @param size - Size of the page
     * @return - Returns @{@link Pageable} object with valid page no and size
     */
    public static Pageable getPageable(int page, int size) {
        page = Math.max(page - 1, 0);
        size = Math.max(size, 1);
        size = Math.min(size, MAX_PAGE_SIZE);
        return PageRequest.of(page, size);
    }

}
