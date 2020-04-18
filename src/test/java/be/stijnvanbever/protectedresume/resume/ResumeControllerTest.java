package be.stijnvanbever.protectedresume.resume;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ResumeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void resumeProperties(DynamicPropertyRegistry registry) {
        registry.add("app.resume.location", ResumeControllerTest::getResumeLocation);
    }

    @Test
    public void shouldReturnResumeAsByteArray() throws Exception {
        MvcResult result = mockMvc.perform(get("/resume"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andReturn();
        Assertions.assertThat(result.getResponse().getContentAsByteArray()).isNotEmpty();
    }

    private static String getResumeLocation() {
        URL blankPdf = ResumeControllerTest.class.getClassLoader().getResource("blank.pdf");
        return blankPdf.getFile();
    }
}