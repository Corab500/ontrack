package net.ontrack.acceptance.pages;

import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void waitForLoad() {
        waitFor("#j_username");
    }

    public void login(String user, String password) {
        $("#j_username").type(user);
        $("#j_password").type(password);
        $("#login-submit").click();
    }
}
