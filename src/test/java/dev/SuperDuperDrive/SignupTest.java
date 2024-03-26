package dev.SuperDuperDrive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import dev.SuperDuperDrive.pages.LoginPage;
import dev.SuperDuperDrive.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = {
        "server.port=8080" })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SignupTest {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private String baseURL;
    private SignupPage signupPage;

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
        if (driver != null) {
            driver.get(baseURL + "/signup");
            signupPage = new SignupPage(driver);
        }
    }

    @Test
    @Order(1)
    void testGetSignupPage() {
        assertEquals("Signup", driver.getTitle());
    }

    @Test
    @Order(2)
    void testSuccessfulRegistration() {
        String email = "neuertrg@gmail.com";
        String password = "1234";
        String firstName = "tran";
        String lastName = "truong";

        signupPage.doSignup(email, password, firstName, lastName);

        assertEquals("Login", driver.getTitle());

        WebElement titleToastEl = driver.findElement(By.id("title-toast-message"));
        WebElement bodyToastEl = driver.findElement(By.id("body-toast-message"));

        assertEquals(titleToastEl.getText(), "Sign Up Success");
        assertEquals(bodyToastEl.getText(), "Hello. You have successfully registered, please log in.");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin(email, password, false);

        String expectedRedirectUrl = baseURL + "/home";
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedRedirectUrl, actualUrl);
    }

    @Test
    @Order(3)
    void testInvalidEmail() {
        String email = "neuertrg@@gmail.com";
        String password = "1234";
        String firstName = "tran";
        String lastName = "truong";

        signupPage.doSignup(email, password, firstName, lastName);

        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-email")));
        WebElement errorEmailEl = driver.findElement(By.id("error-email"));
        assertEquals("Invalid email", errorEmailEl.getText());
    }

    @Test
    @Order(4)
    void testMissingFirstNameInformation() {
        String email = "neuertrg@gmail.com";
        String password = "1234";
        String firstName = "";
        String lastName = "truong";

        signupPage.doSignup(email, password, firstName, lastName);

        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-firstname")));
        WebElement errorFirstNameEl = driver.findElement(By.id("error-firstname"));
        assertEquals("Invalid email", errorFirstNameEl.getText());

    }

}
