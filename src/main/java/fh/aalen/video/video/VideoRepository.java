package fh.aalen.video.video;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoRepository extends CrudRepository<Video, String> {

    public List<Video> findVideoByGenreOrderByTitle(String genre);

}
