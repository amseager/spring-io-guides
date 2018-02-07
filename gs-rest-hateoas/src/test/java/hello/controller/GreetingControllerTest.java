package hello.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = GreetingController.class)
public class GreetingControllerTest {

    private static final String HATEOAS_CONTENT_TYPE = "application/hal+json;charset=UTF-8";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void greeting_EmptyParamName_ReturnsHelloWorld() throws Exception {
        mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HATEOAS_CONTENT_TYPE))
                .andExpect(jsonPath("$.content", is("Hello, World!")))
                .andExpect(jsonPath("$._links.self.href", containsString("/greeting?name=World")));
    }

    @Test
    public void greeting_FilledParamName_ReturnsHelloParam() throws Exception {
        mockMvc.perform(get("/greeting?name=Alice"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HATEOAS_CONTENT_TYPE))
                .andExpect(jsonPath("$.content", is("Hello, Alice!")))
                .andExpect(jsonPath("$._links.self.href", containsString("/greeting?name=Alice")));

        mockMvc.perform(get("/greeting?name=Bob"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HATEOAS_CONTENT_TYPE))
                .andExpect(jsonPath("$.content", is("Hello, Bob!")))
                .andExpect(jsonPath("$._links.self.href", containsString("/greeting?name=Bob")));
    }
}
