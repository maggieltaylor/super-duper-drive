package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private final String username = "maggie";
	private final String password = "password";
	private final String noteTitle = "To Do";
	private final String noteDescription = "Mow lawn, laundry, dishes";
	private final String credUrl = "/this/is/a/url";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void unauthorizedUserCanOnlyAccessLoginAndSignup() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void userCanSignUpThenLoginAndLogout() {
		driver.get("http://localhost:" + port + "/signup");
		SignupPage signupPage = new SignupPage(driver);

		signupPage.signUp("maggie", "taylor", username, password);

		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);

		loginPage.login(username, password);

		assertEquals("Home", driver.getTitle());

		HomePage homePage = getHomePageDriver();

		homePage.logout();

		driver.get("http://localhost:" + this.port + "/home");
		assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void noteIsCreatedAndDisplayed() {
		goToHomepage();
		HomePage homePage = getHomePageDriver();

		homePage.addNote(noteTitle, noteDescription);

		homePage = getHomePageDriver();

		assertEquals(noteTitle, homePage.viewNoteTitle());
		assertEquals(noteDescription, homePage.viewNoteDescription());
	}

	@Test
	public void noteIsCreatedAndEdited() {
		String newNoteTitle = "To Do for Christmas";
		String newNoteDescription = "Wrap presents";
		goToHomepage();

		HomePage homePage = getHomePageDriver();

		homePage.addNote(noteTitle, noteDescription);
		homePage = getHomePageDriver();

		homePage.editNote(newNoteTitle, newNoteDescription);
		homePage = getHomePageDriver();

		assertEquals(newNoteTitle, homePage.viewNoteTitle());
		assertEquals(newNoteDescription, homePage.viewNoteDescription());
	}

	@Test
	public void noteIsNoLongerInListWhenDeleted() {
		goToHomepage();
		HomePage homePage = getHomePageDriver();

		homePage.addNote(noteTitle, noteDescription);
		homePage = getHomePageDriver();

		homePage.deleteNote();
		homePage = getHomePageDriver();

		assertFalse(homePage.noteIsDisplayed());
	}

	@Test
	public void credentialIsCreatedAndDisplaysWithPasswordEncrypted() {
		goToHomepage();
		HomePage homePage = getHomePageDriver();

		homePage.addCredential(credUrl, username, password);
		homePage = getHomePageDriver();

		assertEquals(credUrl, homePage.viewCredUrl());
		assertEquals(username, homePage.viewCredUsername());
		assertNotEquals(password, homePage.viewCredPassword());
	}

	@Test
	public void credentialIsAddedAndEditedWithDecryptedPassword() {
		String newUrl = "new/url";
		String newUsername = "pam";
		String newPassword = "password2";

		goToHomepage();
		HomePage homePage = getHomePageDriver();

		homePage.addCredential(credUrl, username, password);
		homePage = getHomePageDriver();

		homePage.editCredential();
		assertEquals(password, homePage.viewCredPasswordInput());

		homePage.editCredential(newUrl, newUsername, newPassword);
		homePage = getHomePageDriver();

		assertEquals(newUrl, homePage.viewCredUrl());
		assertEquals(newUsername, homePage.viewCredUsername());
		assertNotEquals(newPassword, homePage.viewCredPassword());
	}

	@Test
	public void credentialIsDeleted() {
		goToHomepage();
		HomePage homePage = getHomePageDriver();

		homePage.addCredential(credUrl, username, password);
		homePage = getHomePageDriver();

		homePage.deleteCredential();
		homePage = getHomePageDriver();

		assertFalse(homePage.credentialIsDisplayed());
	}

	private void goToHomepage() {
		driver.get("http://localhost:" + port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signUp("maggie", "taylor", username, password);
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	private HomePage getHomePageDriver() {
		driver.get("http://localhost:" + port + "/home");
		return new HomePage(driver);
	}

}
