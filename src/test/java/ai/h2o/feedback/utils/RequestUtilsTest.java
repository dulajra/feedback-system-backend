package ai.h2o.feedback.utils;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestUtilsTest {

    @Test
    void testGetPageableForSizeParam() {
        Pageable pageable = RequestUtils.getPageable(-1, 5);
        assertEquals(0, pageable.getPageNumber(), "Page no should be reset to 0 for all values < 1");

        pageable = RequestUtils.getPageable(0, 5);
        assertEquals(0, pageable.getPageNumber(), "Page no should be reset to 0 for all values < 1");

        pageable = RequestUtils.getPageable(1, 5);
        assertEquals(0, pageable.getPageNumber(), "Returned page no should be 1 less than passed page no");

        pageable = RequestUtils.getPageable(2, 5);
        assertEquals(1, pageable.getPageNumber(), "Returned page no should be 1 less than passed page no");
    }

    @Test
    void testGetPageableForPageParam(){
        Pageable pageable = RequestUtils.getPageable(2, -1);
        assertEquals(1, pageable.getPageSize(), "Size should be reset to 1 for all values < 1");

        pageable = RequestUtils.getPageable(2, 0);
        assertEquals(1, pageable.getPageSize(), "Size should be reset to 1 for all values < 1");

        pageable = RequestUtils.getPageable(2, 1);
        assertEquals(1, pageable.getPageSize(), "All positive nos less than or equal to 1000 are valid as page size");

        pageable = RequestUtils.getPageable(2, 1000);
        assertEquals(1000, pageable.getPageSize(), "All positive nos less than or equal to 1000 are valid as page size");

        pageable = RequestUtils.getPageable(2, 1001);
        assertEquals(1000, pageable.getPageSize(), "Size should be reset to 1000 for all values > 1000");
    }
}