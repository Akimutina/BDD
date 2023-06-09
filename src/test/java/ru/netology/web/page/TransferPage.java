package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.valueOf;

public class TransferPage {
    private SelenideElement inputAmount = $("[data-test-id=amount] input"); //Сумма
    private SelenideElement inputFrom = $("[data-test-id=from] input"); //С какой карты
    //private SelenideElement inputTo = $(".input[data-test-id=to] .input__control"); //На какую карту
    private SelenideElement actionTransfer = $("[data-test-id=action-transfer]"); //Пополнить
    //private SelenideElement actionCancel = $(".button[data-test-id=action-cancel]"); //Отмена
    private SelenideElement errorMessage = $("[data-test-id='error-notification']"); //Сообщение об ошибке

    public void transferMoney(int amount, DataHelper.CardsInfo from) {
        inputAmount.setValue(valueOf(amount));
        inputFrom.setValue(valueOf(from));
        actionTransfer.click();
        new CardBalancePage();
    }

    public void findErrorMessage (String expectedText) {
        errorMessage
                .shouldHave(exactText(expectedText), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

}
