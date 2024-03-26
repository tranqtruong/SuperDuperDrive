package dev.SuperDuperDrive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import dev.SuperDuperDrive.pages.HomePage;
import dev.SuperDuperDrive.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = {
        "server.port=8080" })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileOperationsTest {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private String baseURL;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        if (driver != null || driver.getWindowHandles().size() > 0) {
            driver.close();
            driver.quit();
            driver = null;
        }
    }

    @BeforeEach
    public void beforeEach() {
        baseURL = "http://localhost:" + port;
    }

    @Test
    @Order(1)
    void testUploadValidSizeFile() {
        driver.get(baseURL + "/login");
        loginPage = new LoginPage(driver);
        loginPage.doLogin("trantruongac1@gmail.com", "123", false);

        driver.get(baseURL + "/home");
        homePage = new HomePage(driver);

        homePage.uploadFile("test.txt");

        WebElement titleMessage = driver.findElement(By.id("title-toast-mes"));
        WebElement bodyMessage = driver.findElement(By.id("body-toast-mes"));

        assertEquals("Success.", titleMessage.getText());
        assertEquals("Yoo hoo!", bodyMessage.getText());
    }

    @Test
    @Order(2)
    void testUploadLargeFile() {
        driver.get(baseURL + "/home");
        homePage = new HomePage(driver);
        homePage.uploadFile("upload5m.txt");

        WebElement titleMessage = driver.findElement(By.id("title-toast-mes"));
        WebElement bodyMessage = driver.findElement(By.id("body-toast-mes"));

        assertEquals("Upload Failed!", titleMessage.getText());
        assertEquals("File too large.", bodyMessage.getText());
    }

    @Test
    @Order(3)
    void testUploadAlreadyExistFile() {
        driver.get(baseURL + "/home");
        homePage = new HomePage(driver);
        homePage.uploadFile("test.txt");

        WebElement titleMessage = driver.findElement(By.id("title-toast-mes"));
        WebElement bodyMessage = driver.findElement(By.id("body-toast-mes"));

        assertEquals("Upload Failed!", titleMessage.getText());
        assertEquals("File already exists.", bodyMessage.getText());
    }

}
