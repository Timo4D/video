package fh.aalen.video.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class VideoService {

    @Autowired
    VideoRepository videoRepository;

    public List<Video> getVideoList() {
        ArrayList<Video> mylist = new ArrayList<>();
        Iterator<Video> it = videoRepository.findAll().iterator();
        while (it.hasNext())
            mylist.add(it.next());
        return mylist;
    }

    public Video getVideo(String title) {
        return videoRepository.findById(title).orElse(null);
    }

    public void addVideo(Video video) {
        videoRepository.save(video);
    }

    public void updateVideo(String title, Video video) {
        videoRepository.save(video);
    }

    public void deleteVideo(String title) {
        videoRepository.deleteById(title);
    }

    public List<Video> getAllVideosOfGenre(String genre) {
        return videoRepository.findVideoByGenreOrderByTitle(genre);
    }


}
