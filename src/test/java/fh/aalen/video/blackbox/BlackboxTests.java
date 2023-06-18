package fh.aalen.video.blackbox;

import static org.junit.jupiter.api.Assertions.*;

import fh.aalen.video.person.Person;
import fh.aalen.video.person.PersonController;
import fh.aalen.video.person.PersonService;
import fh.aalen.video.video.Video;
import fh.aalen.video.video.VideoController;
import fh.aalen.video.video.VideoService;
import java.sql.Date;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
public class BlackboxTests {

  @Autowired
  private VideoController videoController = new VideoController();

  @Autowired
  private PersonController personController = new PersonController();

  @Autowired
  private VideoService videoService = new VideoService();

  @Autowired
  private PersonService personService = new PersonService();

  @Test
  void testNumberInput() { //keine richtigen Zahlen, da als String gespeichert
    videoController.addVideo(
      new Video("123456789", "123456789", "123456789", "123456789")
    );
    Video video = videoController.getVideo("123456789");
    Assertions.assertNotNull(video, "Video sollte nicht null sein");
  }

  @Test
  void testUmlautInput() {
    videoController.addVideo(new Video("üäö", "üäö", "üäö", "üäö"));
    Video video = videoController.getVideo("üäö");
    Assertions.assertNotNull(video, "Video sollte nicht null sein");
  }

  @Test
  void testSpecialCharInput() {
    videoController.addVideo(new Video("@$%^&*", "@$%^&*", "@$%^&*", "@$%^&*"));
    Video video = videoController.getVideo("@$%^&*");
    Assertions.assertNotNull(video, "Video sollte nicht null sein");
  }

  @Test
  void testEmptyInput() {
    videoController.addVideo(new Video("", "", "", ""));
    Video video = videoController.getVideo("");
    Assertions.assertNotNull(video, "Video sollte nicht null sein");
  }

  @Test
  void testLongInput() {
    String longString = new String(new char[1000]).replace("\0", "a");

    // Ausnahme wird erwartet
    assertThrows(
      DataIntegrityViolationException.class,
      () -> {
        // Versuche das Video hinzuzufügen
        videoController.addVideo(
          new Video(longString, longString, longString, longString)
        );
      },
      "Es sollte eine DataIntegrityViolationException geworfen werden"
    );

    // Hier ist kein Video hinzugefügt, deshalb sollte nichts zurückgegeben werden
    Video video = videoController.getVideo(longString);
    Assertions.assertNull(video, "Video sollte null sein");
  }

  @Test
  void testAddVideoWithMinimumLength() {
    Video video = new Video("A", "PG-13", "Short description", "Short");
    videoService.addVideo(video);
    Video addedVideo = videoService.getVideo("A");
    Assertions.assertNotNull(
      addedVideo,
      "Video should not be null after adding"
    );
  }

  @Test
  void testAddVideoWithExceedingLength() {
    String longTitle = String.join("", Collections.nCopies(101, "a"));
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> {
        Video video = new Video(longTitle, "PG-13", "Long description", "Long");
        videoService.addVideo(video);
      }
    );
  }

  @Test
  public void testPersonSurname() {
    Person person = new Person();
    String surname = "Test";

    person.setSurname(surname);

    Assertions.assertEquals(
      surname,
      person.getSurname(),
      "Surname should match the set value"
    );
  }

  @Test
  public void testVideoDescription() {
    Video video = new Video();
    String description = "This is a test description";

    video.setDescription(description);

    Assertions.assertEquals(
      description,
      video.getDescription(),
      "Description should match the set value"
    );
  }

  @Test
  public void testVideoAgeRating() {
    String ageRating = "16";
    Video video = new Video("Test Title", ageRating, "A test video", "Action");

    Assertions.assertEquals(
      ageRating,
      video.getAgeRating(),
      "Age rating should match the provided value"
    );
  }

  @Test
  public void testPersonBirthdate() {
    Person person = new Person();
    Date birthdate = new Date(0);

    person.setBirthdate(birthdate);

    Assertions.assertEquals(
      birthdate,
      person.getBirthdate(),
      "Birthdate should match the set value"
    );
  }

  @Test
  void testFindVideosByGenre() {
    List<Video> actionVideos = videoController.getAllVideosOfGenre("Action");
    Assertions.assertNotNull(actionVideos);
    Assertions.assertFalse(actionVideos.isEmpty());
    actionVideos.forEach(video -> {
      Assertions.assertEquals("Action", video.getGenre());
    });
  }

  @Test
  void testAddVideo() {
    Video video = new Video("Star Wars", "PG-13", "A space opera", "Sci-Fi");
    videoService.addVideo(video);

    // Video sollte in der Liste gefunden werden
    Video addedVideo = videoService.getVideo("Star Wars");
    Assertions.assertNotNull(
      addedVideo,
      "Video sollte nicht null sein nach dem Hinzufügen"
    );
  }

  @Test
  void testUpdateVideo() {
    Video videoToUpdate = new Video(
      "Security",
      "18",
      "Marine macht Krawall",
      "Romantik"
    );
    videoController.updateVideo("Security", videoToUpdate);
    Video updatedVideo = videoController.getVideo("Security");
    Assertions.assertEquals(
      videoToUpdate.getDescription(),
      updatedVideo.getDescription()
    );
  }

  @Test
  void testDeleteVideo() {
    videoController.deleteVideo("Security");
    Video video = videoController.getVideo("Security");
    Assertions.assertNull(video);
  }

  @Test
  public void testVideoAddPerformance() {
    assertTimeout(
      Duration.ofSeconds(5),
      () -> {
        for (int i = 0; i < 1000; i++) {
          videoService.addVideo("Video Title " + i, "Video Description " + i);
        }
      },
      "Adding 1000 videos took longer than expected."
    );
  }

  @Test
  public void testVideoRetrievalPerformance() {
    // Assuming we already have 1000 videos added to the system.
    assertTimeout(
      Duration.ofSeconds(5),
      () -> {
        for (int i = 0; i < 1000; i++) {
          videoService.getVideoByTitle("Video Title " + i);
        }
      },
      "Retrieving 1000 videos by title took longer than expected."
    );
  }

  @Test
  public void testVideoDeletionPerformance() {
    // Assuming we already have 1000 videos added to the system.
    assertTimeout(
      Duration.ofSeconds(5),
      () -> {
        for (int i = 0; i < 1000; i++) {
          videoService.deleteVideoByTitle("Video Title " + i);
        }
      },
      "Deleting 1000 videos by title took longer than expected."
    );
  }

  @Test
  void testAddPerson() {
    Person person = new Person(123, "Doe", new Date(0));
    personController.addPerson(person);
    Person addedPerson = personController.getPerson(123L);
    Assertions.assertNotNull(addedPerson);
    Assertions.assertEquals("Doe", addedPerson.getSurname());
  }

  @Test
  void testUpdatePerson() {
    Person personToUpdate = new Person(123, "UpdatedSurname", new Date(0));
    personController.updatePerson(123L, personToUpdate);
    Person updatedPerson = personController.getPerson(123L);
    Assertions.assertEquals(
      personToUpdate.getSurname(),
      updatedPerson.getSurname()
    );
  }

  @Test
  void testDeletePerson() {
    Person person = new Person(1L, "Mustermann", new Date(0));
    personService.addPerson(person);

    // Person sollte in der Liste gefunden werden
    Person addedPerson = personService.getPerson(1L);
    Assertions.assertNotNull(
      addedPerson,
      "Person sollte nicht null sein nach dem Hinzufügen"
    );

    // Löschen der Person
    personService.deletePerson(1L);

    // Person sollte nun nicht mehr in der Liste gefunden werden
    Person deletedPerson = personService.getPerson(1L);
    Assertions.assertNull(
      deletedPerson,
      "Person sollte null sein nach dem Löschen"
    );
  }

  @Test
  void testAddPersonWithMinimumLength() {
    Person person = new Person((long) 1, "A", new Date(0));
    personService.addPerson(person);
    Person addedPerson = personService.getPerson((long) 1);
    Assertions.assertNotNull(
      addedPerson,
      "Person should not be null after adding"
    );
  }

  @Test
  void testAddPersonWithExceedingLength() {
    String longName = String.join("", Collections.nCopies(51, "a"));
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> {
        Person person = new Person((long) 1, longName, new Date(0));
        personService.addPerson(person);
      }
    );
  }

  @Test
  void testAddPersonPerformance() {
    int maxPersons = 10000;
    for (int i = 0; i < maxPersons; i++) {
      Person person = new Person((long) i, "Person" + i, new Date(0));
      personService.addPerson(person);
    }

    long startTime = System.currentTimeMillis();
    Person person = new Person(
      (long) maxPersons,
      "Person" + maxPersons,
      new Date(0)
    );
    personService.addPerson(person);
    long endTime = System.currentTimeMillis();

    Assertions.assertTrue(
      endTime - startTime < 200,
      "Adding a person should take less than 200 milliseconds"
    );
  }

  @Test
  void testDeletePersonPerformance() {
    int maxPersons = 10000;
    for (int i = 0; i <= maxPersons; i++) {
      Person person = new Person((long) i, "Person" + i, new Date(0));
      personService.addPerson(person);
    }

    long startTime = System.currentTimeMillis();
    personService.deletePerson((long) maxPersons);
    long endTime = System.currentTimeMillis();

    Assertions.assertTrue(
      endTime - startTime < 200,
      "Deleting a person should take less than 200 milliseconds"
    );
  }
}
