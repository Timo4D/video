package fh.aalen.video.blackbox;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import fh.aalen.video.person.Person;
import fh.aalen.video.person.PersonService;
import fh.aalen.video.video.Video;
import fh.aalen.video.video.VideoController;
import fh.aalen.video.video.VideoService;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
public class BlackboxTestsPerformance {

  @Autowired
  private PersonService personService = new PersonService();

  //Mockito test
  @Test // tests the performance of adding 1000 videos
  public void testVideoAddPerformance() {
    VideoService videoServiceMock = mock(VideoService.class);

    assertTimeout(
      Duration.ofSeconds(5),
      () -> {
        for (int i = 0; i < 1000; i++) {
          Video video = new Video(
            "Video Title " + i,
            "PG-13",
            "Video Description " + i,
            "Genre"
          );

          videoServiceMock.addVideo(video);
        }
      },
      "Adding 1000 videos took longer than expected."
    );

    verify(videoServiceMock, times(1000)).addVideo(any(Video.class));
  }

  //Mockito test
  @Test // tests the performance of retrieving 1000 videos
  public void testVideoRetrievalPerformance() {
    VideoController videoControllerMock = mock(VideoController.class);

    when(videoControllerMock.getVideo(anyString()))
      .thenReturn(
        new Video("Mock Title", "PG-13", "Mock Description", "Genre")
      );

    assertTimeout(
      Duration.ofSeconds(5),
      () -> {
        for (int i = 0; i < 1000; i++) {
          videoControllerMock.getVideo("Video Title " + i);
        }
      },
      "Retrieving 1000 videos by title took longer than expected."
    );

    verify(videoControllerMock, times(1000)).getVideo(anyString());
  }

  // Mockito test
  @Test // tests the performance of updating 1000 videos
  public void testVideoUpdatePerformance() {
    VideoService videoServiceMock = mock(VideoService.class);

    String originalTitle = "Original Title";

    assertTimeout(
      Duration.ofSeconds(5),
      () -> {
        for (int i = 0; i < 1000; i++) {
          Video updatedVideo = new Video(
            originalTitle,
            "PG-13",
            "Updated Description " + i,
            "Updated Genre " + i
          );

          videoServiceMock.updateVideo(originalTitle, updatedVideo);
        }
      },
      "Updating 1000 videos took longer than expected."
    );

    verify(videoServiceMock, times(1000))
      .updateVideo(eq(originalTitle), any(Video.class));
  }

  //Mockito test
  @Test // tests the performance of deleting 1000 videos
  public void testVideoDeletionPerformance() {
    VideoController videoControllerMock = mock(VideoController.class);

    assertTimeout(
      Duration.ofSeconds(5),
      () -> {
        for (int i = 0; i < 1000; i++) {
          videoControllerMock.deleteVideo("Video Title " + i);
        }
      },
      "Deleting 1000 videos by title took longer than expected."
    );

    verify(videoControllerMock, times(1000)).deleteVideo(anyString());
  }

  @Test //tests the performance of adding 350 persons
  void testAddPersonPerformance() {
    int maxPersons = 350;
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

  @Test //tests the performance of retrieving 350 persons
  void testPersonRetrievalPerformance() {
    int maxPersons = 350;

    long startId = 1000000L;
    for (int i = 0; i <= maxPersons; i++) {
      Person person = new Person(startId + i, "Person" + i, new Date(0));
      personService.addPerson(person);
    }

    long startTime = System.currentTimeMillis();

    List<Person> allPersons = personService.getPersons();
    Person retrievedPerson = allPersons
      .stream()
      .filter(p -> p.getSurname().equals("Person" + maxPersons))
      .findFirst()
      .orElse(null);
    long endTime = System.currentTimeMillis();

    Assertions.assertNotNull(
      retrievedPerson,
      "Retrieved person should not be null"
    );
    Assertions.assertFalse(
      endTime - startTime < 200,
      "Retrieving a person should take less than 200 milliseconds"
    );
  }

  @Test //tests the performance of updating 350 persons
  void testPersonUpdatePerformance() {
    int maxPersons = 350;

    long startId = 1000000L;
    for (int i = 0; i <= maxPersons; i++) {
      Person person = new Person(startId + i, "Person" + i, new Date(0));
      personService.addPerson(person);
    }

    Person updatedPerson = new Person(
      startId + maxPersons,
      "UpdatedPerson",
      new Date(0)
    );

    long startTime = System.currentTimeMillis();
    personService.updatePerson(updatedPerson);
    long endTime = System.currentTimeMillis();

    List<Person> allPersons = personService.getPersons();
    Person retrievedPerson = allPersons
      .stream()
      .filter(p -> p.getSurname().equals("UpdatedPerson"))
      .findFirst()
      .orElse(null);

    Assertions.assertNotNull(
      retrievedPerson,
      "Retrieved person should not be null"
    );
    Assertions.assertEquals(
      "UpdatedPerson",
      retrievedPerson.getSurname(),
      "Person surname should be updated"
    );

    Assertions.assertTrue(
      endTime - startTime < 200,
      "Updating a person should take less than 200 milliseconds"
    );
  }

  @Test //tests the performance of deleting 350 persons
  void testDeletePersonPerformance() {
    int maxPersons = 350;
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
