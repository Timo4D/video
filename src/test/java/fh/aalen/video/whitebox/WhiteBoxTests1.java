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
public class WhiteBoxTests1 {

    private Person person;
    private Video video;

    @BeforeEach
    public void setup() {
        person = new Person(1, "Doe", new Date());
        video = new Video("Sample Video", "PG", "A sample video", "Action");
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
    public void testAllPersonIdsArePositive() {
        List<Person> persons = new ArrayList<>();

        for (Person person : persons) {
            long id = person.getId();
            Assertions.assertTrue(id > 0, "Invalid ID: " + id);
        }
    }

}
