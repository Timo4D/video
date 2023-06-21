package fh.aalen.video.blackbox;

import fh.aalen.video.person.Person;
import fh.aalen.video.person.PersonService;
import fh.aalen.video.video.Video;
import fh.aalen.video.video.VideoController;
import fh.aalen.video.video.VideoService;
import jakarta.transaction.Transactional;
import java.sql.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
public class BlackboxTestsBoundaryValueAnalysis {

  @Autowired
  private VideoController videoController = new VideoController();

  @Autowired
  private VideoService videoService = new VideoService();

  @Autowired
  private PersonService personService = new PersonService();

  @Test // tests the boundary value for no input at all
  void testAddVideoWithEmptyInput() {
    videoController.addVideo(new Video("", "", "", ""));
    Video video = videoController.getVideo("");
    Assertions.assertNotNull(video, "Video should not be null after adding");
  }

  @Test // tests the boundary value for extremely small input
  void testAddVideoWithMinimumLength() {
    Video video = new Video("A", "1", "A", "A");
    videoService.addVideo(video);
    Video addedVideo = videoService.getVideo("A");
    Assertions.assertNotNull(
      addedVideo,
      "Video should not be null after adding"
    );
  }

  @Test // tests the boundary value for extremely large input
  void testAddVideoWithImmenseLength() {
    String longString = new String(new char[10000000]).replace("\0", "a");

    videoController.addVideo(
      new Video(longString, longString, longString, longString)
    );

    Video video = videoController.getVideo(longString);
    Assertions.assertNotNull(video, "Video sollte nicht null sein");
  }

  @Test // tests the boundary value for no input at all
  void testAddPersonWithEmptyInput() {
    Person person = new Person();
    person.setSurname("");
    person.setBirthdate(new Date(0));

    personService.addPerson(person);

    Long id = person.getId();

    Person addedPerson = personService.getPerson(id);

    Assertions.assertNotNull(
      addedPerson,
      "Person should not be null after adding"
    );
  }

  @Test // tests the boundary value for extremely small input
  void testAddPersonWithMinimumLength() {
    Person person = new Person();
    person.setSurname("A");
    person.setBirthdate(new Date(0001 - 01 - 01));

    personService.addPerson(person);

    Long id = person.getId();

    Person addedPerson = personService.getPerson(id);

    Assertions.assertNotNull(
      addedPerson,
      "Person should not be null after adding"
    );
  }

  @Test // tests the boundary value for extremely large input
  void testAddPersonWithImmenseLength() {
    String longString = new String(new char[10000000]).replace("\0", "a");

    Person person = new Person();
    person.setSurname(longString);
    person.setBirthdate(new Date(999999 - 12 - 31));
    person.setId(0);

    personService.addPerson(person);

    Long id = person.getId();

    Person addedPerson = personService.getPerson(id);

    Assertions.assertNotNull(
      addedPerson,
      "Person should not be null after adding"
    );
  }
}
