package ai.h2o.feedback.controller;

import ai.h2o.feedback.FeedbackServiceApplication;
import ai.h2o.feedback.model.Feedback;
import ai.h2o.feedback.model.dto.FeedbackDTO;
import ai.h2o.feedback.service.FeedbackService;
import ai.h2o.feedback.utils.RequestUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FeedbackController.class)
@ContextConfiguration(classes = {FeedbackServiceApplication.class})
public class FeedbackControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    FeedbackService feedbackService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetAllFeedback() throws Exception {
        List<Feedback> feedbackList = new ArrayList<>();
        Feedback feedback = new Feedback();
        feedback.setId(1);
        feedback.setRating(4);
        feedback.setComment("Good product");
        feedback.setName("John Doe");
        feedback.setCreatedAt(Timestamp.from(Instant.now()));
        feedbackList.add(feedback);

        Page<Feedback> feedbackPage = new PageImpl<>(feedbackList, RequestUtils.getPageable(1, 5), 100);
        given(feedbackService.getAllFeedback(RequestUtils.getPageable(1, 5))).willReturn(feedbackPage);

        String response = mvc.perform(get("/feedback?page=1&size=5"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(response);

        assertEquals(feedbackList.size(), jsonNode.get("data").size());
        assertEquals(feedbackList.get(0).getId().intValue(), jsonNode.get("data").get(0).get("id").intValue());

        assertEquals(1, jsonNode.get("meta").get("currentPage").intValue());
        assertEquals(5, jsonNode.get("meta").get("pageSize").intValue());
        assertEquals(100, jsonNode.get("meta").get("totalItems").intValue());
        assertEquals(20, jsonNode.get("meta").get("totalPages").intValue());
    }

    @Test
    public void testPostFeedback() throws Exception {
        FeedbackDTO reqFeedbackDTO = new FeedbackDTO();
        reqFeedbackDTO.setRating(4);
        reqFeedbackDTO.setComment("Good product");
        reqFeedbackDTO.setName("John Doe");

        FeedbackDTO resFeedbackDTO = new FeedbackDTO();
        BeanUtils.copyProperties(reqFeedbackDTO, reqFeedbackDTO);
        resFeedbackDTO.setId(1);
        resFeedbackDTO.setCreatedAt(Calendar.getInstance().getTimeInMillis());

        given(feedbackService.saveFeedBack(Mockito.any(FeedbackDTO.class))).willReturn(resFeedbackDTO);

        String response = mvc.perform(post("/feedback")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reqFeedbackDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(response);

        assertEquals(1, jsonNode.get("data").get("id").intValue());
        assertNull(jsonNode.get("meta"));
    }
}