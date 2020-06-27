package ai.h2o.feedback.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class RequestUtils {

    private static final int MAX_PAGE_SIZE = 1000;

    private RequestUtils() {
    }

    public static Pageable getPageable(int page, int size) {
        page = Math.max(page - 1, 0);
        size = Math.max(size, 1);
        size = Math.min(size, MAX_PAGE_SIZE);
        return PageRequest.of(page, size);
    }

}
