package fh.aalen.video.whitebox;

import fh.aalen.video.person.Person;
import fh.aalen.video.video.Video;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class WhiteboxTests {

    //Person Whitebox Tests

    @Test //PWBT1C
    public void testPersonConstructor() {
        // Arrange
        long id = 1;
        String surname = "Doe";
        Date birthdate = new Date();

        // Act
        Person person = new Person(id, surname, birthdate);

        // Assert
        Assertions.assertEquals(id, person.getId());
        Assertions.assertEquals(surname, person.getSurname());
        Assertions.assertEquals(birthdate, person.getBirthdate());
    }

    @Test //PWBT2S
    public void testSetBirthdate() {
        // Arrange
        Person person = new Person();
        Date birthdate = new Date();

        // Act
        person.setBirthdate(birthdate);

        // Assert
        Assertions.assertEquals(birthdate, person.getBirthdate());
    }

    @Test //PWBT3S
    public void testSetId() {
        // Arrange
        Person person = new Person();
        long id = 1;

        // Act
        person.setId(id);

        // Assert
        Assertions.assertEquals(id, person.getId());
    }

    @Test //PWBT4G
    public void testGetFavoriteVideos_WhenNoVideosPresent() {
        // Arrange
        Person person = new Person();

        // Assert
        Assertions.assertNotNull(person.getFavoriteVideos(), "Favorite videos should not be null");
        Assertions.assertTrue(person.getFavoriteVideos().isEmpty(), "Favorite videos should be empty");
    }

    @Test // PWBT5G
    public void testSetSurname() {
        // Arrange
        Person person = new Person();
        String surname = "Smith";

        // Act
        person.setSurname(surname);

        // Assert
        Assertions.assertEquals(surname, person.getSurname());
    }


    @Test // PWBT8GS
    public void testPersonGettersAndSetters() {
        Person person = new Person();

        long id = 1;
        String surname = "Smith";
        Date birthdate = new Date();


        person.setId(id);
        person.setSurname(surname);
        person.setBirthdate(birthdate);


        assertEquals(id, person.getId());
        assertEquals(surname, person.getSurname());
        assertEquals(birthdate, person.getBirthdate());

    }

    @Test // PWBT9A
    public void testAddVideoToFavorites() {
        Person person = new Person();
        Video video1 = new Video();
        Video video2 = new Video();

        person.addVideoToFavorites(video1);
        person.addVideoToFavorites(video2);

        List<Video> favoriteVideos = person.getFavoriteVideos();

        assertTrue(favoriteVideos.contains(video1));
        assertTrue(favoriteVideos.contains(video2));
        assertEquals(2, favoriteVideos.size());
    }

    @Test //PWBT10PVR
    public void testPersonVideoRelationship() {
        Person person = new Person();
        Video video1 = new Video();
        Video video2 = new Video();

        person.addVideoToFavorites(video1);
        person.addVideoToFavorites(video2);

        List<Video> favoriteVideos = person.getFavoriteVideos();

        assertTrue(favoriteVideos.contains(video1));
        assertTrue(favoriteVideos.contains(video2));
        assertEquals(2, favoriteVideos.size());

    }

    @Test // PWBT11PBV
    public void testPersonBoundaryValues() {
        long id = Long.MAX_VALUE;
        String surname = "";
        Date birthdate = new Date(Long.MAX_VALUE);

        Person person = new Person(id, surname, birthdate);

        assertEquals(id, person.getId());
        assertEquals(surname, person.getSurname());
        assertEquals(birthdate, person.getBirthdate());
    }


    //Video Whitebox Tests

    @Test //VWBT1GS
    public void testGettersAndSetters() {
        // Arrange
        String title = "Test Video";
        String ageRating = "PG";
        String description = "This is a test video";
        String genre = "Action";
        Video video = new Video();

        // Act
        video.setTitle(title);
        video.setAgeRating(ageRating);
        video.setDescription(description);
        video.setGenre(genre);

        // Assert
        assertEquals(title, video.getTitle());
        assertEquals(ageRating, video.getAgeRating());
        assertEquals(description, video.getDescription());
        assertEquals(genre, video.getGenre());
    }

    @Test //VWBT2C
    public void testConstructor() {
        // Arrange
        String title = "Test Video";
        String ageRating = "PG";
        String description = "This is a test video";
        String genre = "Action";

        // Act
        Video video = new Video(title, ageRating, description, genre);

        // Assert
        assertEquals(title, video.getTitle());
        assertEquals(ageRating, video.getAgeRating());
        assertEquals(description, video.getDescription());
        assertEquals(genre, video.getGenre());
    }

    @Test //VWBT3PC
    public void testPathCoverage() {
        Video video = new Video();

        // Test the first branch of the setter method for 'title'
        video.setTitle("Title");
        assertNotNull(video.getTitle());

        // Test the second branch of the setter method for 'title'
        video.setTitle(null);
        assertNull(video.getTitle());

        // Test the first branch of the setter method for 'genre'
        video.setGenre("Action");
        assertNotNull(video.getGenre());

        // Test the second branch of the setter method for 'genre'
        video.setGenre(null);
        assertNull(video.getGenre());

        // Test the first branch of the setter method for 'description'
        video.setDescription("Description");
        assertNotNull(video.getDescription());

        // Test the second branch of the setter method for 'description'
        video.setDescription(null);
        assertNull(video.getDescription());

        // Test the first branch of the setter method for 'ageRating'
        video.setAgeRating("PG-13");
        assertNotNull(video.getAgeRating());

        // Test the second branch of the setter method for 'ageRating'
        video.setAgeRating(null);
        assertNull(video.getAgeRating());
    }


    @Test // VWBT4BC
    public void testBranchCoverage() {
        Video video = new Video();

        // Test the first branch of the setter method for 'title'
        video.setTitle("Title");
        assertNotNull(video.getTitle());

        // Test the second branch of the setter method for 'title'
        video.setTitle(null);
        assertNull(video.getTitle());

        // Test the first branch of the setter method for 'genre'
        video.setGenre("Action");
        assertNotNull(video.getGenre());

        // Test the second branch of the setter method for 'genre'
        video.setGenre(null);
        assertNull(video.getGenre());

        // Test the first branch of the setter method for 'description'
        video.setDescription("Description");
        assertNotNull(video.getDescription());

        // Test the second branch of the setter method for 'description'
        video.setDescription(null);
        assertNull(video.getDescription());

        // Test the first branch of the setter method for 'ageRating'
        video.setAgeRating("PG-13");
        assertNotNull(video.getAgeRating());

        // Test the second branch of the setter method for 'ageRating'
        video.setAgeRating(null);
        assertNull(video.getAgeRating());

        // Test the second branch of the setter method for 'personFavorites'
        video.setPersonFavorites(null);
        assertNull(video.getPersonFavorites());
    }


    @Test //VWBT5PV
    public void testPersonFavorites() {
        // Arrange
        Video video = new Video();
        Person person1 = new Person();
        Person person2 = new Person();

        // Act
        video.setPersonFavorites(Arrays.asList(person1, person2));

        // Assert
        assertEquals(2, video.getPersonFavorites().size());
        assertTrue(video.getPersonFavorites().contains(person1));
        assertTrue(video.getPersonFavorites().contains(person2));
    }

    @Test //VWBT7EC
    public void testEmptyConstructor() {
        // Create a Video object using the empty constructor
        Video video = new Video();

        // Set values using setters
        video.setTitle("Sample Title");
        video.setAgeRating("PG-13");
        video.setDescription("Sample description");
        video.setGenre("Action");

        // Use getters to retrieve values and assert they match
        Assertions.assertEquals("Sample Title", video.getTitle());
        Assertions.assertEquals("PG-13", video.getAgeRating());
        Assertions.assertEquals("Sample description", video.getDescription());
        Assertions.assertEquals("Action", video.getGenre());
    }

    @Test //VWBT8DC
    public void testDefaultConstructor() {
        // Create a Video object using the default constructor
        Video video = new Video();

        // Assert that all fields are initialized to null or default values
        Assertions.assertNull(video.getTitle());
        Assertions.assertNull(video.getAgeRating());
        Assertions.assertNull(video.getDescription());
        Assertions.assertNull(video.getGenre());
        Assertions.assertNull(video.getPersonFavorites());
    }

    @Test //VWBT10NN
    public void testGenreNotNull() {
        // Create a Video object
        Video video = new Video();

        // Set the genre to null
        video.setGenre("Test Genre");

        // Assert that the genre is set to a default value
        Assertions.assertNotNull(video.getGenre());
        Assertions.assertEquals("Test Genre", video.getGenre());
    }


    @Test //VWBT11PVEL
    public void testPersonFavoritesEmptyList() {
        // Create a Video object
        Video video = new Video();

        // Create an empty list of Person objects
        List<Person> personList = new ArrayList<>();

        // Set the empty list of Person favorites using the setter
        video.setPersonFavorites(personList);

        // Retrieve the list of Person favorites using the getter and assert it is empty
        Assertions.assertTrue(video.getPersonFavorites().isEmpty());
    }

    @Test //VWBT12PVNL
    public void testPersonFavoritesNullList() {
        // Create a Video object
        Video video = new Video();

        // Set the list of Person favorites to null using the setter
        video.setPersonFavorites(null);

        // Retrieve the list of Person favorites using the getter and assert it is null
        Assertions.assertNull(video.getPersonFavorites());
    }


}
