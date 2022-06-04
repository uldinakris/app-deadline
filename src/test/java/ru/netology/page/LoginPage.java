package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public VerificationPage authoriseUser(DataHelper.AuthInfo authInfo) {
        $("[data-test-id=login] input").setValue(authInfo.getLogin());
        $("[data-test-id=password] input").setValue(authInfo.getPassword());
        $("[data-test-id=action-login]").click();
        return new ru.netology.page.VerificationPage();
    }

    public void nonAuthoriseUser(DataHelper.AuthInfo authInfo) {
        $("[data-test-id=login] input").setValue(authInfo.getLogin());
        $("[data-test-id=password] input").setValue(authInfo.getPassword());
        $("[data-test-id=action-login]").click();
        SelenideElement errorMessage = $("span[contains(., 'Неверно указан логин или пароль')]");
        errorMessage.shouldBe(Condition.visible);
    }

    public void blocksUser() {
        SelenideElement blockMessage = $("span[contains(., 'Вы 3 раза ввели неверный пароль. Пользователь заблокирован')]");
        blockMessage.shouldBe(Condition.visible);
    }

}
