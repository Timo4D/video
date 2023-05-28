package fh.aalen.video.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class VideoController {

    @Autowired
    VideoService videoService;

    @RequestMapping("/videos")
    public String getVideoList(Model model) {
        model.addAttribute("videos", videoService.getVideoList());
        return "videos";
    }

    @RequestMapping("/videos/{title}")
    public String getVideo(@PathVariable String title, Model model) {
        model.addAttribute("videos", videoService.getVideo(title));
        return "videos";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/videos")
    public void addVideo(@RequestBody Video video) {
        videoService.addVideo(video);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/videos/{title}")
    public void updateVideo(@PathVariable String title, @RequestBody Video video) {
        videoService.updateVideo(title, video);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/videos/{title}")
    public void deleteVideo(@PathVariable String title) {
        videoService.deleteVideo(title);
    }

    @RequestMapping("/videosbygenre/{genre}")
    public List<Video> getAllVideosOfGenre(@PathVariable String genre) {
        return videoService.getAllVideosOfGenre(genre);
    }


}
