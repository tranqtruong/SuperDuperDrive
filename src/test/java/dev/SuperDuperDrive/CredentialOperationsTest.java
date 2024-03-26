package dev.SuperDuperDrive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
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
public class CredentialOperationsTest {
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
    void testCreateNewCredential() {
        // login
        driver.get(baseURL + "/login");
        loginPage = new LoginPage(driver);
        loginPage.doLogin("trantruongac1@gmail.com", "123", false);

        driver.get(baseURL + "/home");
        homePage = new HomePage(driver);

        String url = "google.com";
        String username = "trg";
        String password = "123";
        homePage.createNewCredential(url, username, password);

        WebElement titleMessage = driver.findElement(By.id("title-toast-mes"));
        WebElement bodyMessage = driver.findElement(By.id("body-toast-mes"));

        assertEquals("Success.", titleMessage.getText());
        assertEquals("Yoo hoo!", bodyMessage.getText());
    }

    @Test
    @Order(2)
    void testReadCredentials() {
        driver.get(baseURL + "/home");
        List<WebElement> credentialRows = driver.findElements(By.className("credential-row"));
        assertNotNull(credentialRows);
    }

    @Test
    @Order(3)
    void testEditCredential() {
        driver.get(baseURL + "/home");
        homePage = new HomePage(driver);
        String url = "youtube.com";
        String username = "trg2";
        String password = "1234";

        homePage.editCredential(url, username, password);

        WebElement titleMessage = driver.findElement(By.id("title-toast-mes"));
        WebElement bodyMessage = driver.findElement(By.id("body-toast-mes"));

        assertEquals("Success.", titleMessage.getText());
        assertEquals("Yoo hoo!", bodyMessage.getText());
    }

    @Test
    @Order(4)
    void testDeleteCredential() {
        driver.get(baseURL + "/home");
        homePage = new HomePage(driver);

        homePage.deleteCredential();

        Alert alert = driver.switchTo().alert();
        alert.accept();

        WebElement titleMessage = driver.findElement(By.id("title-toast-mes"));
        WebElement bodyMessage = driver.findElement(By.id("body-toast-mes"));

        assertEquals("Delete Success.", titleMessage.getText());
        assertEquals("Yoo hoo!", bodyMessage.getText());
    }

}
