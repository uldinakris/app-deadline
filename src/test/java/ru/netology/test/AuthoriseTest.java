package ru.netology.test;

import com.codeborne.selenide.Configuration;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthoriseTest {
    @Test
    void shouldAuthorise() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.authoriseUser(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validateUser(verificationCode);
    }

    @Test
    void shouldNotAuthoriseAndBlock() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getWrongAuthInfo();
        loginPage.nonAuthoriseUser(authInfo);
        loginPage.nonAuthoriseUser(authInfo);
        loginPage.nonAuthoriseUser(authInfo);
        loginPage.blocksUser();
    }

}
