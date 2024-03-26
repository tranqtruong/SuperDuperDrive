package dev.SuperDuperDrive.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(css = "#floatingInputEmail")
    private WebElement emailField;

    @FindBy(css = "#floatingPassword")
    private WebElement passwordField;

    @FindBy(css = "#floatingFirstName")
    private WebElement firstNameField;

    @FindBy(css = "#floatingLastName")
    private WebElement lastNameField;

    @FindBy(css = "#signup-btn")
    private WebElement signupButton;

    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void doSignup(String email, String password, String firstName, String lastName) {
        this.emailField.sendKeys(email);
        this.passwordField.sendKeys(password);
        this.firstNameField.sendKeys(firstName);
        this.lastNameField.sendKeys(lastName);
        this.signupButton.click();
    }
}
