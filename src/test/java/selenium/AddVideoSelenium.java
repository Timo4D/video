package selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddVideoSelenium {
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
    }

    @Test
    public void testAddVideo() {
        driver.get("http://localhost:8080/");

        // Add Video
        WebElement title = driver.findElement(By.name("title"));
        WebElement description = driver.findElement(By.name("description"));
        WebElement ageRating = driver.findElement(By.name("ageRating"));
        WebElement genre = driver.findElement(By.name("genre"));
        WebElement submit = driver.findElement(By.id("newVideoButton"));

        // Fill the form
        String testTitle = "Test Title";
        title.sendKeys(testTitle);

        String testDescription = "Test Description";
        description.sendKeys(testDescription);

        String testAgeRating = "Test Age Rating";
        ageRating.sendKeys(testAgeRating);

        String testGenre = "Test Genre";
        genre.sendKeys(testGenre);

        submit.click();

        //Wait for 1 sec
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Check if the video appears in the table
        WebElement table = driver.findElement(By.id("videotable"));
        assertTrue(table.getText().contains(testTitle));
        assertTrue(table.getText().contains(testDescription));
        assertTrue(table.getText().contains(testAgeRating));
        assertTrue(table.getText().contains(testGenre));

        driver.quit();
    }

}
