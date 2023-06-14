package fh.aalen.video.blackbox;


import fh.aalen.video.video.Video;
import fh.aalen.video.video.VideoController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BlackboxTests {

  @Autowired
  private VideoController videoController = new VideoController();

  @Test
  void testNumberInput() { //keine richtigen Zahlen, da als String gespeichert
    videoController.addVideo(new Video("123456789", "123456789", "123456789", "123456789"));
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

    // Versuche das Video hinzuzufügen
    videoController.addVideo(new Video(longString, longString, longString, longString));

    // Versuche das hinzugefügte Video abzurufen
    Video video = videoController.getVideo(longString);
    Assertions.assertNotNull(video, "Video sollte nicht null sein");
}
}
