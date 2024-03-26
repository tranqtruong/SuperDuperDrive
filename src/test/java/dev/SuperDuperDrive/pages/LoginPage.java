package dev.SuperDuperDrive.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(css = "#floatingEmail")
    private WebElement emailField;

    @FindBy(css = "#floatingPassword")
    private WebElement passwordField;

    @FindBy(css = "#remember-me-checkbox")
    private WebElement rememberMeCheckbox;

    @FindBy(css = "#sign-in-btn")
    private WebElement signInButton;

    @FindBy(css = "#login-failed")
    private WebElement loginFailedMessage;

    @FindBy(css = "#continue-with-google")
    private WebElement continueWithGoogleBtn;

    public boolean isLoginFailedMessageDisplayed() {
        return loginFailedMessage.isDisplayed();
    }

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void doLogin(String username, String password, boolean rememberMe) {
        this.emailField.sendKeys(username);
        this.passwordField.sendKeys(password);
        if (rememberMe) {
            this.rememberMeCheckbox.click();
        }
        this.signInButton.click();
    }

    public void doLoginWithGoogle() {
        this.continueWithGoogleBtn.click();
    }

}
