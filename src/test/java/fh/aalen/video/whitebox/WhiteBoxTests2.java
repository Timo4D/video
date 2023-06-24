package fh.aalen.video.whitebox;

import com.fasterxml.jackson.databind.ObjectMapper;

import fh.aalen.video.person.Person;
import fh.aalen.video.video.Video;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class WhiteBoxTests2 {

    private ObjectMapper objectMapper;
    Video video;
    Person person;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        video = new Video("Sample Video", "18", "A sample video", "Action");
        person = new Person(1, "Doe", new Date());
    }

    @Test
    public void testDeleteVideo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/videos/{title}", "Sample Video")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(video)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateVideo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/videos/{title}", "Sample Video")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(video)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateVideo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(video)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
