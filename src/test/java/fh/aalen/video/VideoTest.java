package fh.aalen.video;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import fh.aalen.video.video.Video;
import fh.aalen.video.video.VideoService;


@SpringBootTest
public class VideoTest {


    
   Video video = new Video("Testfilm","18","Test","Test");
   VideoService videoService = new VideoService();

    @Test
    void videoExists() {
        videoService.addVideo(video);
       
    }



}
