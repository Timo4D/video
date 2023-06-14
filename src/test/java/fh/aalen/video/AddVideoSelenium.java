package fh.aalen.video;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddVideoSelenium {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
        description.sendKeys("Test Description");
        ageRating.sendKeys("Test Age Rating");
        genre.sendKeys("Test Genre");
        submit.click();

        // Wait until the table is loaded after clicking the button
        WebElement loadTable = driver.findElement(By.id("loadtable"));
        loadTable.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("videotable")));

        // Check if the video appears in the table
        WebElement table = driver.findElement(By.id("videotable"));
        assertTrue(table.getText().contains(testTitle));
        assertTrue(table.getText().contains("Test Description"));
        assertTrue(table.getText().contains("Test Age Rating"));
        assertTrue(table.getText().contains("Test Genre"));

        driver.quit();
    }

}
