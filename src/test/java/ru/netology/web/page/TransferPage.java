package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.valueOf;

public class TransferPage {
    private SelenideElement inputAmount = $("[data-test-id=amount] input"); //Сумма
    private SelenideElement inputFrom = $("[data-test-id=from] input"); //С какой карты
    private SelenideElement inputTo = $(".input[data-test-id=to] .input__control"); //На какую карту
    private SelenideElement actionTransfer = $("[data-test-id=action-transfer]"); //Пополнить
    private SelenideElement actionCancel = $(".button[data-test-id=action-cancel]"); //Отмена
    private SelenideElement errorMessage = $("[data-test-id='error-notification']"); //Сообщение об ошибке

    public void transferMoney(int amount, DataHelper.CardsInfo from) {
        inputAmount.setValue(valueOf(amount));
        inputFrom.setValue(valueOf(from));
        actionTransfer.click();
        new CardBalancePage();
    }

    public void errorLimit() {
        errorMessage
                .shouldHave(Condition.text("Ошибка!"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    public void invalidCard() {
        errorMessage
                .shouldHave(Condition.text("Ошибка!"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

}
