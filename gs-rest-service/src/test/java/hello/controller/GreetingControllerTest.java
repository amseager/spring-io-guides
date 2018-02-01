package hello.controller;

import hello.domain.Greeting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = GreetingController.class)
public class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    @Test
    public void greeting_SequentRequests_IncrementIdCounter() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andReturn();
        String responseString = mvcResult.getResponse().getContentAsString();
        long id1 = messageConverter.getObjectMapper().readValue(responseString, Greeting.class).getId();

        responseString = mockMvc.perform(get("/greeting")).andReturn().getResponse().getContentAsString();
        long id2 = messageConverter.getObjectMapper().readValue(responseString, Greeting.class).getId();

        responseString = mockMvc.perform(get("/greeting")).andReturn().getResponse().getContentAsString();
        long id3 = messageConverter.getObjectMapper().readValue(responseString, Greeting.class).getId();

        assertThat(id2 - 1, is(id1));
        assertThat(id3 - 1, is(id2));
    }

    @Test
    public void greeting_EmptyParamName_ReturnsHelloWorld() throws Exception {
        mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", is("Hello, World!")));
    }

    @Test
    public void greeting_FilledParamName_ReturnsHelloParam() throws Exception {
        mockMvc.perform(get("/greeting?name=Alice"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", is("Hello, Alice!")));

        mockMvc.perform(get("/greeting?name=Bob"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", is("Hello, Bob!")));
    }
}
