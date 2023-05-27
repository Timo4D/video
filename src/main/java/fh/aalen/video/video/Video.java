package fh.aalen.video.video;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fh.aalen.video.person.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Video {
    @Id
    private String title;
    private String ageRating;
    private String description;
    private String genre;

    @ManyToMany(mappedBy="favouriteVideos")
    @JsonIgnore //Otherwise problems with recursion in json video-->person-->video-->...
    private List<Person> personFavourites;

    public Video() {
    }

    public Video(String title, String ageRating, String description, String genre) {
        super();
        this.title = title;
        this.ageRating = ageRating;
        this.description = description;
        this.genre = genre;
    }

    public List<Person> getPersonFavorites() {
        return personFavourites;
    }

    public void setPersonFavorites(List<Person> personFavorites) {
        this.personFavourites = personFavorites;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }
}
