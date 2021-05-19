package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id = "logout-btn")
    private WebElement logoutButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "note-submit-btn")
    private WebElement noteSubmit;

    @FindBy(id = "note-title-display")
    private WebElement noteTitle;

    @FindBy(id = "note-desc-display")
    private WebElement noteDescription;

    @FindBy(id = "add-note")
    private WebElement addNoteSubmit;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "note-view-btn")
    private WebElement noteViewButton;

    @FindBy(id = "delete-note")
    private WebElement deleteNoteButton;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-cred")
    private WebElement addCredButton;

    @FindBy(id = "credential-url")
    private WebElement credUrlInput;

    @FindBy(id = "credential-username")
    private WebElement credUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credPasswordInput;

    @FindBy(id = "cred-submit")
    private WebElement credSubmit;

    @FindBy(id = "cred-url-display")
    private WebElement credUrl;

    @FindBy(id = "cred-username-display")
    private WebElement credUsername;

    @FindBy(id = "cred-password-display")
    private WebElement credPassword;

    @FindBy(id = "edit-cred")
    private WebElement editCredButton;

    @FindBy(id = "delete-cred")
    private WebElement deleteCredButton;

    WebDriverWait wait;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
         wait = new WebDriverWait(webDriver, 5);
    }

    public void logout() {
        logoutButton.submit();
    }


    public void addNote(String noteTitle, String noteDescription) {
        notesTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(addNoteSubmit));
        addNoteSubmit.click();
        wait.until(ExpectedConditions.visibilityOf(noteTitleInput));
        noteTitleInput.sendKeys(noteTitle);
        noteDescriptionInput.sendKeys(noteDescription);
        noteSubmit.click();
    }

    public String viewNoteTitle() {
        notesTab.click();
        wait.until(ExpectedConditions.visibilityOf(noteTitle));
        return noteTitle.getText();
    }

    public String viewNoteDescription() {
        return noteDescription.getText();
    }

    public void editNote(String newNoteTitle, String newNoteDescription) {
        notesTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(noteViewButton));
        noteViewButton.click();
        wait.until(ExpectedConditions.visibilityOf(noteTitleInput));
        noteTitleInput.clear();
        noteTitleInput.sendKeys(newNoteTitle);
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(newNoteDescription);
        noteSubmit.click();
    }

    public void deleteNote() {
        notesTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton));
        deleteNoteButton.click();
    }

    public boolean noteIsDisplayed() {
        notesTab.click();
        try {
            return noteTitle.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void addCredential(String url, String username, String password) {
        credentialsTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(addCredButton));
        addCredButton.click();
        wait.until(ExpectedConditions.visibilityOf(credUrlInput));
        credUrlInput.sendKeys(url);
        credUsernameInput.sendKeys(username);
        credPasswordInput.sendKeys(password);
        credSubmit.click();
    }

    public String viewCredUrl() {
        credentialsTab.click();
        wait.until(ExpectedConditions.visibilityOf(credUrl));
        return credUrl.getText();
    }

    public String viewCredUsername() {
        return credUsername.getText();
    }

    public String viewCredPassword() {
        return credPassword.getText();
    }

    public void editCredential() {
        credentialsTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(editCredButton));
        editCredButton.click();
    }

    public String viewCredPasswordInput() {
        return credPasswordInput.getAttribute("value");
    }

    public void editCredential(String newUrl, String newUsername, String newPassword) {
        wait.until(ExpectedConditions.visibilityOf(credUrlInput));
        credUrlInput.clear();
        credUrlInput.sendKeys(newUrl);
        credUsernameInput.clear();
        credUsernameInput.sendKeys(newUsername);
        credPasswordInput.clear();
        credPasswordInput.sendKeys(newPassword);
        credSubmit.click();
    }

    public void deleteCredential() {
        credentialsTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteCredButton));
        deleteCredButton.click();
    }

    public boolean credentialIsDisplayed() {
        credentialsTab.click();
        try {
            return credUrl.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
