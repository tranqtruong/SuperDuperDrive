package dev.SuperDuperDrive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import dev.SuperDuperDrive.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = {
        "server.port=8080" })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private String baseURL;
    private LoginPage loginPage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        // options.addArguments("--disable-infobars");
        // options.addArguments("--no-sandbox");
        // options.addArguments("--disable-web-security");
        driver = new ChromeDriver(options);
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
            driver.get(baseURL + "/login");
            loginPage = new LoginPage(driver);
        }
    }

    // Test cases

    @Test
    @Order(1)
    @Disabled
    public void getLoginPage() {
        assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(2)
    @Disabled
    public void testLoginWithInvalidUsername() {
        String email = "trantruongac2@gmail.com";
        String password = "123";
        loginPage.doLogin(email, password, false);
        assertTrue(loginPage.isLoginFailedMessageDisplayed());
    }

    @Test
    @Order(3)
    @Disabled
    public void testLoginWithInvalidPassword() {
        String email = "trantruongac1@gmail.com";
        String password = "1234";
        loginPage.doLogin(email, password, false);
        assertTrue(loginPage.isLoginFailedMessageDisplayed());
    }

    @Test
    @Order(4)
    @Disabled
    public void testLoginWithEmptyUsernameAndPassword() {
        String email = "";
        String password = "";
        loginPage.doLogin(email, password, false);
        assertTrue(loginPage.isLoginFailedMessageDisplayed());
    }

    @Test
    @Order(5)
    public void testLoginWithValidCredentials() {
        String email = "trantruongac1@gmail.com";
        String password = "123";
        loginPage.doLogin(email, password, false);

        String expectedRedirectUrl = baseURL + "/home";
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedRedirectUrl, actualUrl);

    }

    @Test
    @Order(6)
    @Disabled
    public void testLoginWithGoogle() throws InterruptedException {
        loginPage.doLoginWithGoogle();
        // Thread.sleep(10000);

        // Điền thông tin đăng nhập Google
        WebElement googleEmailField = driver.findElement(By.id("identifierId"));
        googleEmailField.sendKeys("mmeow2508@gmail.com");
        WebElement googleNextButton = driver.findElement(By.id("identifierNext"));
        googleNextButton.click();

        // Chờ trang tải xong
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(250));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));

        // Điền mật khẩu
        WebElement googlePasswordField = driver.findElement(By.name("Passwd"));
        googlePasswordField.sendKeys("P.XHs8Nyr.7D92D");
        WebElement googleSignInButton = driver.findElement(By.id("passwordNext"));
        googleSignInButton.click();

        //
        Thread.sleep(50000);

        // Kiểm tra kết quả
        String expectedRedirectUrl = baseURL + "/home";
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedRedirectUrl, actualUrl);
    }

}
