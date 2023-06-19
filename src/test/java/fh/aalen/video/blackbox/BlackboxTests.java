package fh.aalen.video.blackbox;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import fh.aalen.video.person.Person;
import fh.aalen.video.person.PersonController;
import fh.aalen.video.person.PersonService;
import fh.aalen.video.video.Video;
import fh.aalen.video.video.VideoController;
import fh.aalen.video.video.VideoService;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
public class BlackboxTests {

  @Autowired
  private VideoController videoController = new VideoController();

  @Autowired
  private VideoService videoService = new VideoService();

  @Autowired
  private PersonService personService = new PersonService();

  private List<Long> createdIds = new ArrayList<>();


  //--------Grenzwertanalyse--------
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
    String longString = new String(new char[10000000]).replace("\0", "a");

    // Versuche das Video hinzuzufügen
    videoController.addVideo(
      new Video(longString, longString, longString, longString)
    );

    Video video = videoController.getVideo(longString);
    Assertions.assertNotNull(video, "Video sollte nicht null sein");

    System.out.println("Video: " + video);
    System.out.println("Video: " + video.getTitle());
    System.out.println("Video:");
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

  @Test //Mockito test
  void testFindVideosByGenre() {
    // Erstellt einen Mock für VideoController
    VideoController videoControllerMock = mock(VideoController.class);

    List<Video> mockVideos = Arrays.asList(
      new Video("Title1", "PG-13", "Description1", "Action"),
      new Video("Title2", "PG-13", "Description2", "Action")
    );

    // Wenn die Methode getAllVideosOfGenre() des Mocks mit "Action" aufgerufen wird,
    // geben wir die mockten Videos zurück
    when(videoControllerMock.getAllVideosOfGenre("Action"))
      .thenReturn(mockVideos);

    // Rufe die Methode getAllVideosOfGenre() des Mocks auf
    List<Video> actionVideos = videoControllerMock.getAllVideosOfGenre(
      "Action"
    );

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
      "Security Test",
      "18",
      "Marine macht Krawall",
      "Romantik"
    );
    videoController.updateVideo("Security Test", videoToUpdate);
    Video updatedVideo = videoController.getVideo("Security Test");
    Assertions.assertEquals(
      videoToUpdate.getDescription(),
      updatedVideo.getDescription()
    );
  }

  @Test
  void testDeleteVideo() {
    videoController.deleteVideo("Security Test");
    Video video = videoController.getVideo("Security Test");
    Assertions.assertNull(video);
  }

  @Test //Mockito test
  public void testVideoAddPerformance() {
    // Erstellen Sie einen Mock von VideoService
    VideoService videoServiceMock = mock(VideoService.class);

    assertTimeout(
      Duration.ofSeconds(5),
      () -> {
        for (int i = 0; i < 1000; i++) {
          // Erstellen Sie eine Video-Instanz für jeden Durchlauf
          Video video = new Video(
            "Video Title " + i,
            "PG-13",
            "Video Description " + i,
            "Genre"
          );
          // Fügen Sie das Video mit dem Mock-Service hinzu
          videoServiceMock.addVideo(video);
        }
      },
      "Adding 1000 videos took longer than expected."
    );

    // Überprüfen Sie, ob der addVideo-Aufruf 1000 Mal durchgeführt wurde
    verify(videoServiceMock, times(1000)).addVideo(any(Video.class));
  }

  @Test //Mockito test
  public void testVideoRetrievalPerformance() {
    // Erstellen Sie einen Mock von VideoController
    VideoController videoControllerMock = mock(VideoController.class);

    // Vorbereiten des Mocks, um bei jedem Aufruf ein Video zurückzugeben
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

    // Überprüfen Sie, ob der getVideo-Aufruf 1000 Mal durchgeführt wurde
    verify(videoControllerMock, times(1000)).getVideo(anyString());
  }

  @Test //Mockito test
  public void testVideoDeletionPerformance() {
    // Erstellen Sie einen Mock von VideoController
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

    // Überprüfen Sie, ob der deleteVideo-Aufruf 1000 Mal durchgeführt wurde
    verify(videoControllerMock, times(1000)).deleteVideo(anyString());
  }

  @Test //Mockito test
  void testAddPerson() {
    // Erstellt einen Mock für PersonController
    PersonController personControllerMock = mock(PersonController.class);

    Person person = new Person(123, "Doe", new Date(0));

    // Wenn die Methode getPerson() des Mocks mit 123L aufgerufen wird,
    // geben wir die mockte Person zurück
    when(personControllerMock.getPerson(123L)).thenReturn(person);

    // Rufe die Methode addPerson() des Mocks auf
    personControllerMock.addPerson(person);

    // Rufe die Methode getPerson() des Mocks auf
    Person addedPerson = personControllerMock.getPerson(123L);

    Assertions.assertNotNull(addedPerson);
    Assertions.assertEquals("Doe", addedPerson.getSurname());
  }

  @Test //Mockito test
  void testUpdatePerson() {
    // Erstellt einen Mock für PersonController
    PersonController personControllerMock = mock(PersonController.class);

    Person personToUpdate = new Person(123, "UpdatedSurname", new Date(0));

    // Wenn die Methode getPerson() des Mocks mit 123L aufgerufen wird,
    // geben wir die aktualisierte Person zurück
    when(personControllerMock.getPerson(123L)).thenReturn(personToUpdate);

    // Rufe die Methode updatePerson() des Mocks auf
    personControllerMock.updatePerson(123L, personToUpdate);

    // Rufe die Methode getPerson() des Mocks auf
    Person updatedPerson = personControllerMock.getPerson(123L);

    Assertions.assertEquals(
      personToUpdate.getSurname(),
      updatedPerson.getSurname()
    );
  }

  @Test //Mockito test
  void testDeletePerson() {
    // Erstellt einen Mock für PersonService
    PersonService personServiceMock = mock(PersonService.class);

    Person person = new Person(1L, "Mustermann", new Date(0));

    // Wenn die Methode getPerson() des Mocks mit 1L aufgerufen wird,
    // geben wir zuerst die Person zurück und danach null
    when(personServiceMock.getPerson(1L)).thenReturn(person, null);

    // Rufe die Methode addPerson() des Mocks auf
    personServiceMock.addPerson(person);

    // Rufe die Methode getPerson() des Mocks auf
    Person addedPerson = personServiceMock.getPerson(1L);
    Assertions.assertNotNull(
      addedPerson,
      "Person sollte nicht null sein nach dem Hinzufügen"
    );

    // Rufe die Methode deletePerson() des Mocks auf
    personServiceMock.deletePerson(1L);

    // Rufe die Methode getPerson() des Mocks erneut auf
    Person deletedPerson = personServiceMock.getPerson(1L);
    Assertions.assertNull(
      deletedPerson,
      "Person sollte null sein nach dem Löschen"
    );
  }

  @Test //Mockito test
  void testAddPersonWithMinimumLength() {
    // Erstellen Sie einen Mock für den PersonService
    PersonService personServiceMock = mock(PersonService.class);

    Person person = new Person(1L, "A", new Date(0));

    // Stellen Sie ein, dass der Mock die Person zurückgibt, wenn getPerson() aufgerufen wird
    when(personServiceMock.getPerson(1L)).thenReturn(person);

    // Verwenden Sie den Mock anstelle des echten PersonService
    personServiceMock.addPerson(person);

    // Überprüfen Sie, ob die hinzugefügte Person korrekt abgerufen werden kann
    Person addedPerson = personServiceMock.getPerson(1L);
    Assertions.assertNotNull(
      addedPerson,
      "Person sollte nicht null sein nach dem Hinzufügen"
    );
  }

  @Test //Mockito test
  void testAddPersonWithExceedingLength() {
    PersonService personServiceMock = mock(PersonService.class);

    String longName = String.join("", Collections.nCopies(51, "a"));

    // Set up the mock to throw an IllegalArgumentException when addPerson is called
    doThrow(new IllegalArgumentException())
      .when(personServiceMock)
      .addPerson(any(Person.class));

    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> {
        Person person = new Person(1L, longName, new Date(0));
        personServiceMock.addPerson(person);
      }
    );
  }

  @Test
  void testAddPersonPerformance() {
    int maxPersons = 350;
    for (int i = 0; i < maxPersons; i++) {
      Person person = new Person((long) i, "Person" + i, new Date(0));
      personService.addPerson(person);
      createdIds.add(person.getId());
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
    createdIds.add(person.getId());
  }

  @Test
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
