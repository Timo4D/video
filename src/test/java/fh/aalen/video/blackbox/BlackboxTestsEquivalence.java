package fh.aalen.video.blackbox;

import static org.mockito.Mockito.*;

import fh.aalen.video.person.Person;
import fh.aalen.video.person.PersonController;
import fh.aalen.video.person.PersonService;
import fh.aalen.video.video.Video;
import fh.aalen.video.video.VideoController;
import fh.aalen.video.video.VideoService;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
public class BlackboxTestsEquivalence {

  @Autowired
  private VideoController videoController = new VideoController();

  @Autowired
  private VideoService videoService = new VideoService();

  @Test // tests added video with four equal number-Strings
  void testNumberInput() {
    videoController.addVideo(
      new Video("123456789", "123456789", "123456789", "123456789")
    );
    Video video = videoController.getVideo("123456789");
    Assertions.assertNotNull(video, "Video sollte nicht null sein");
  }

  @Test // tests added video with four equal Umlaut-Strings
  void testUmlautInput() {
    videoController.addVideo(new Video("üäö", "üäö", "üäö", "üäö"));
    Video video = videoController.getVideo("üäö");
    Assertions.assertNotNull(video, "Video sollte nicht null sein");
  }

  @Test // tests added video with four equal special character-Strings
  void testSpecialCharInput() {
    videoController.addVideo(new Video("@$%^&*", "@$%^&*", "@$%^&*", "@$%^&*"));
    Video video = videoController.getVideo("@$%^&*");
    Assertions.assertNotNull(video, "Video sollte nicht null sein");
  }

  @Test // tests added video
  void testAddVideo() {
    Video video = new Video("Star Wars", "PG-13", "A space opera", "Sci-Fi");
    videoService.addVideo(video);

    Video addedVideo = videoService.getVideo("Star Wars");
    Assertions.assertNotNull(
      addedVideo,
      "Video sollte nicht null sein nach dem Hinzufügen"
    );
  }

  @Test // tests to update a video
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

  @Test // tests to delete a video
  void testDeleteVideo() {
    videoController.deleteVideo("Security Test");
    Video video = videoController.getVideo("Security Test");
    Assertions.assertNull(video);
  }

  @Test // tests to set a video title
  public void testVideoTitle() {
    Video video = new Video();
    String title = "This is a test title";

    video.setTitle(title);

    Assertions.assertEquals(
      title,
      video.getTitle(),
      "Title should match the set value"
    );
  }

  @Test //tests to set a video age rating
  public void testVideoAgeRating() {
    String ageRating = "16";
    Video video = new Video("Test Title", ageRating, "A test video", "Action");

    Assertions.assertEquals(
      ageRating,
      video.getAgeRating(),
      "Age rating should match the provided value"
    );
  }

  @Test // tests to set a video description
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

  @Test // tests to set a video genre
  public void testVideoGenre() {
    Video video = new Video();
    String genre = "This is a test genre";

    video.setGenre(genre);

    Assertions.assertEquals(
      genre,
      video.getGenre(),
      "Genre should match the set value"
    );
  }

  @Test // tests to set a person id
  public void testPersonId() {
    Person person = new Person();
    long id = 123000;

    person.setId(id);

    Assertions.assertEquals(
      id,
      person.getId(),
      "Id should match the set value"
    );
  }

  @Test // tests to set a person surname
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

  @Test // tests to set a person birthdate
  public void testPersonBirthdate() {
    Person person = new Person();
    Date birthdate = new Date(2000 - 01 - 01);

    person.setBirthdate(birthdate);

    Assertions.assertEquals(
      birthdate,
      person.getBirthdate(),
      "Birthdate should match the set value"
    );
  }

  //Mockito test
  @Test //tests to add a person
  void testAddPerson() {
    PersonController personControllerMock = mock(PersonController.class);

    Person person = new Person(123, "Doe", new Date(0));

    when(personControllerMock.getPerson(123L)).thenReturn(person);

    personControllerMock.addPerson(person);

    Person addedPerson = personControllerMock.getPerson(123L);

    Assertions.assertNotNull(addedPerson);
    Assertions.assertEquals("Doe", addedPerson.getSurname());
  }

  //Mockito test
  @Test //tests to update a person
  void testUpdatePerson() {
    PersonController personControllerMock = mock(PersonController.class);

    Person personToUpdate = new Person(123, "UpdatedSurname", new Date(0));

    when(personControllerMock.getPerson(123L)).thenReturn(personToUpdate);

    personControllerMock.updatePerson(123L, personToUpdate);

    Person updatedPerson = personControllerMock.getPerson(123L);

    Assertions.assertEquals(
      personToUpdate.getSurname(),
      updatedPerson.getSurname()
    );
  }

  //Mockito test
  @Test //tests to delete a person
  void testDeletePerson() {
    PersonService personServiceMock = mock(PersonService.class);

    Person person = new Person(1L, "Mustermann", new Date(0));

    when(personServiceMock.getPerson(1L)).thenReturn(person, null);

    personServiceMock.addPerson(person);

    Person addedPerson = personServiceMock.getPerson(1L);
    Assertions.assertNotNull(
      addedPerson,
      "Person sollte nicht null sein nach dem Hinzufügen"
    );

    personServiceMock.deletePerson(1L);

    Person deletedPerson = personServiceMock.getPerson(1L);
    Assertions.assertNull(
      deletedPerson,
      "Person sollte null sein nach dem Löschen"
    );
  }

  //Mockito test
  @Test //tests to find videos by genre
  void testFindVideosByGenre() {
    VideoController videoControllerMock = mock(VideoController.class);

    List<Video> mockVideos = Arrays.asList(
      new Video("Title1", "PG-13", "Description1", "Action"),
      new Video("Title2", "PG-13", "Description2", "Action")
    );

    when(videoControllerMock.getAllVideosOfGenre("Action"))
      .thenReturn(mockVideos);

    List<Video> actionVideos = videoControllerMock.getAllVideosOfGenre(
      "Action"
    );

    Assertions.assertNotNull(actionVideos);
    Assertions.assertFalse(actionVideos.isEmpty());
    actionVideos.forEach(video -> {
      Assertions.assertEquals("Action", video.getGenre());
    });
  }
}
