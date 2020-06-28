package ai.h2o.feedback.utils;

import org.junit.Test;
import org.springframework.data.domain.Pageable;

import static org.junit.Assert.assertEquals;

public class RequestUtilsTest {

    @Test
    public void testGetPageableForSizeParam() {
        Pageable pageable = RequestUtils.getPageable(-1, 5);
        assertEquals("Page no should be reset to 0 for all values < 1", 0, pageable.getPageNumber());

        pageable = RequestUtils.getPageable(0, 5);
        assertEquals("Page no should be reset to 0 for all values < 1", 0, pageable.getPageNumber() );

        pageable = RequestUtils.getPageable(1, 5);
        assertEquals("Returned page no should be 1 less than passed page no",0, pageable.getPageNumber());

        pageable = RequestUtils.getPageable(2, 5);
        assertEquals("Returned page no should be 1 less than passed page no",1, pageable.getPageNumber());
    }

    @Test
    public void testGetPageableForPageParam(){
        Pageable pageable = RequestUtils.getPageable(2, -1);
        assertEquals("Size should be reset to 1 for all values < 1",1, pageable.getPageSize());

        pageable = RequestUtils.getPageable(2, 0);
        assertEquals("Size should be reset to 1 for all values < 1",1, pageable.getPageSize());

        pageable = RequestUtils.getPageable(2, 1);
        assertEquals("All positive <= 1000 are valid as page size",1, pageable.getPageSize());

        pageable = RequestUtils.getPageable(2, 1000);
        assertEquals("All positive nos <= 1000 are valid as page size",1000, pageable.getPageSize());

        pageable = RequestUtils.getPageable(2, 1001);
        assertEquals( "Size should be reset to 1000 for all values > 1000", 1000, pageable.getPageSize());
    }

}
