package dev.SuperDuperDrive.pages;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(css = "#add-note-btn")
    private WebElement addNewNoteButton;

    @FindBy(css = "#title-note")
    private WebElement inputTitleNoteField;

    @FindBy(css = "#description-note")
    private WebElement inputDescriptionNoteField;

    @FindBy(css = "#btn-save-note")
    private WebElement saveNoteBtn;

    @FindBy(css = "#nav-notes-tab")
    private WebElement noteTab;

    @FindBy(className = "btn-edit-note")
    private List<WebElement> editNoteBtns;

    @FindBy(className = "btn-delete-note")
    private List<WebElement> deleteNoteBtns;

    @FindBy(css = "#nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(css = "#btn-add-credential")
    private WebElement addNewCredentialButton;

    @FindBy(css = "#url-credential")
    private WebElement urlFiled;

    @FindBy(css = "#username-credential")
    private WebElement usernameField;

    @FindBy(css = "#password-credential")
    private WebElement passwordField;

    @FindBy(css = "#btn-save-credential")
    private WebElement saveCredentialBtn;

    @FindBy(className = "btn-edit-credential")
    private List<WebElement> editCredentialBtns;

    @FindBy(className = "btn-delete-credential")
    private List<WebElement> deleteCredentialBtns;

    @FindBy(css = "#nav-files-tab")
    private WebElement fileTab;

    @FindBy(css = "#formFile")
    private WebElement fileUploadField;

    @FindBy(css = "#btn-upload-file")
    private WebElement uploadFileBtn;

    @FindBy(className = "btn-download-file")
    private List<WebElement> downloadFileBtns;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void createNewNote(String noteTitle, String noteDescription) {
        this.noteTab.click();
        this.addNewNoteButton.click();
        this.inputTitleNoteField.sendKeys(noteTitle);
        this.inputDescriptionNoteField.sendKeys(noteDescription);
        this.saveNoteBtn.click();
    }

    public void editNote(String noteTitle, String noteDescription) {
        this.noteTab.click();
        this.editNoteBtns.get(0).click();
        this.inputTitleNoteField.clear();
        this.inputTitleNoteField.sendKeys(noteTitle);
        this.inputDescriptionNoteField.clear();
        this.inputDescriptionNoteField.sendKeys(noteDescription);
        this.saveNoteBtn.click();
    }

    public void deleteNote() {
        this.noteTab.click();
        this.deleteNoteBtns.get(0).click();
    }

    public void createNewCredential(String url, String username, String password) {
        this.credentialTab.click();
        this.addNewCredentialButton.click();
        this.urlFiled.sendKeys(url);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.saveCredentialBtn.click();
    }

    public void editCredential(String url, String username, String password) {
        this.credentialTab.click();
        this.editCredentialBtns.get(0).click();
        this.urlFiled.clear();
        this.urlFiled.sendKeys(url);
        this.usernameField.clear();
        this.usernameField.sendKeys(username);
        this.passwordField.clear();
        this.passwordField.sendKeys(password);
        this.saveCredentialBtn.click();
    }

    public void deleteCredential() {
        this.credentialTab.click();
        this.deleteCredentialBtns.get(0).click();
    }

    public void uploadFile(String filename) {
        this.fileTab.click();
        this.fileUploadField.sendKeys(new File(filename).getAbsolutePath());
        this.uploadFileBtn.click();
    }

    public void downloadFile() {
        this.fileTab.click();
        this.downloadFileBtns.get(0).click();
    }

}
