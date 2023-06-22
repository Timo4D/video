package fh.aalen.video.person;

import fh.aalen.video.video.Video;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String surname;
    private Date birthdate;

    @ManyToMany
    private List<Video> favouriteVideos;

    public Person() {
        this.favouriteVideos = new ArrayList<>();
    }
    public Person(long id, String surname, Date birthdate) {
        this.id = id;
        this.surname = surname;
        this.birthdate = birthdate;
        this.favouriteVideos = new ArrayList<>();
    }

    public List<Video> getFavoriteVideos() {
        return favouriteVideos;
    }

    public void addVideoToFavorites(Video v) {
        this.favouriteVideos.add(v);
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}

