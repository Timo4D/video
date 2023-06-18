package fh.aalen.video.blackbox;

import fh.aalen.video.person.Person;
import fh.aalen.video.video.Video;
import fh.aalen.video.video.VideoController;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
public class BlackboxTests {

  @Autowired
  private VideoController videoController = new VideoController();

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
    assertThrows(DataIntegrityViolationException.class, () -> {
        // Versuche das Video hinzuzufügen
        videoController.addVideo(
          new Video(longString, longString, longString, longString)
        );
    }, "Es sollte eine DataIntegrityViolationException geworfen werden");

    // Hier ist kein Video hinzugefügt, deshalb sollte nichts zurückgegeben werden
    Video video = videoController.getVideo(longString);
    Assertions.assertNull(video, "Video sollte null sein");
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
}
