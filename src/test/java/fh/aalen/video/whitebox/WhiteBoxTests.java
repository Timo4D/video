package fh.aalen.video.whitebox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import fh.aalen.video.person.Person;
import fh.aalen.video.video.Video;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class WhiteBoxTests {

    private Person person;
    private Video video;

    @BeforeEach
    public void setup() {
        person = new Person(1, "Doe", new Date());
        video = new Video("Sample Video", "PG", "A sample video", "Action");
    }

    @Test
    public void testPersonConstructor() {
        Person person = new Person();
        Assertions.assertNotNull(person);
    }

    @Test
    public void testPersonConstructorWithParameters() {
        Person person = new Person(1, "Doe", new Date());
        Assertions.assertEquals(1, person.getId());
        Assertions.assertEquals("Doe", person.getSurname());
        Assertions.assertNotNull(person.getBirthdate());
    }

    @Test
    public void testPersonGetBirthdate() {
        Date birthdate = new Date();
        person.setBirthdate(birthdate);

        Date retrievedBirthdate = person.getBirthdate();
        Assertions.assertEquals(birthdate, retrievedBirthdate);
    }

    @Test
    public void testPersonSetSurname() {
        String surname = "Smith";
        person.setSurname(surname);

        String retrievedSurname = person.getSurname();
        Assertions.assertEquals(surname, retrievedSurname);
    }

    @Test
    public void testPersonSetId() {
        long id = 2;
        person.setId(id);

        long retrievedId = person.getId();
        Assertions.assertEquals(id, retrievedId);
    }

    @Test
    public void testVideoConstructor() {
        Video video = new Video();
        Assertions.assertNotNull(video);
    }

    @Test
    public void testVideoConstructorWithParameters() {
        Video video = new Video("Sample Video", "PG", "A sample video", "Action");
        Assertions.assertEquals("Sample Video", video.getTitle());
        Assertions.assertEquals("PG", video.getAgeRating());
        Assertions.assertEquals("A sample video", video.getDescription());
        Assertions.assertEquals("Action", video.getGenre());
    }

    @Test
    public void testVideoGetPersonFavorites() {
        List<Person> personFavorites = new ArrayList<>();
        personFavorites.add(person);

        video.setPersonFavorites(personFavorites);

        List<Person> retrievedFavorites = video.getPersonFavorites();
        Assertions.assertEquals(personFavorites, retrievedFavorites);
    }

    @Test
    public void testVideoSetTitle() {
        String title = "Updated Video";
        video.setTitle(title);

        String retrievedTitle = video.getTitle();
        Assertions.assertEquals(title, retrievedTitle);
    }

    @Test
    public void testVideoSetAgeRating() {
        String ageRating = "R";
        video.setAgeRating(ageRating);

        String retrievedAgeRating = video.getAgeRating();
        Assertions.assertEquals(ageRating, retrievedAgeRating);
    }

    @Test
    public void testVideoSetDescription() {
        String description = "An updated video";
        video.setDescription(description);

        String retrievedDescription = video.getDescription();
        Assertions.assertEquals(description, retrievedDescription);
    }

    @Test
    public void testVideoSetGenre() {
        String genre = "Comedy";
        video.setGenre(genre);

        String retrievedGenre = video.getGenre();
        Assertions.assertEquals(genre, retrievedGenre);
    }

    @Test
    public void testAllPersonIdsArePositive() {
        List<Person> persons = new ArrayList<>();

        for (Person person : persons) {
            long id = person.getId();
            Assertions.assertTrue(id > 0, "Invalid ID: " + id);
        }
    }

}
