package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.CardBalancePage;
import ru.netology.web.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;
import static ru.netology.web.page.CardBalancePage.pushFirstCardButton;
import static ru.netology.web.page.CardBalancePage.pushSecondCardButton;

class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        val loginPage = new LoginPageV2();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferMoneyFromFirstToSecond() {
        val cardBalancePage = new CardBalancePage();
        val firstCardBalanceStart = cardBalancePage.getFirstCardBalance();
        val secondCardBalanceStart = cardBalancePage.getSecondCardBalance();
        val transferPage = pushSecondCardButton();
        val amount = generateValidAmount(firstCardBalanceStart);
        transferPage.transferMoney(amount, getFirstCardNumber());
        val firstCardBalanceFinish = firstCardBalanceStart - amount;
        val secondCardBalanceFinish = secondCardBalanceStart + amount;
        assertEquals(firstCardBalanceFinish, cardBalancePage.getFirstCardBalance());
        assertEquals(secondCardBalanceFinish, cardBalancePage.getSecondCardBalance());
    }

    @Test
    public void shouldTransferMoneyFromSecondToFirst() {
        val cardBalancePage = new CardBalancePage();
        val firstCardBalanceStart = cardBalancePage.getFirstCardBalance();
        val secondCardBalanceStart = cardBalancePage.getSecondCardBalance();
        val transactionPage = pushFirstCardButton();
        val amount = generateValidAmount(secondCardBalanceStart);
        transactionPage.transferMoney(amount, getSecondCardNumber());
        val firstCardBalanceFinish = firstCardBalanceStart + amount;
        val secondCardBalanceFinish = secondCardBalanceStart - amount;

        assertEquals(firstCardBalanceFinish, cardBalancePage.getFirstCardBalance());
        assertEquals(secondCardBalanceFinish, cardBalancePage.getSecondCardBalance());
    }

    @Test
    public void notShouldTransferExceedCardBalance() {
        val cardBalancePage = new CardBalancePage();
        val firstCardBalanceStart = cardBalancePage.getFirstCardBalance();
        val secondCardBalanceStart = cardBalancePage.getSecondCardBalance();
        val transactionPage = pushSecondCardButton();
        val amount = generateInvalidAmount(firstCardBalanceStart);
        transactionPage.transferMoney(amount, getFirstCardNumber());
        transactionPage.errorLimit();
    }

    @Test
    public void notShouldTransferFromSecondToSecondCard() {
        val cardBalancePage = new CardBalancePage();
        val firstCardBalanceStart = cardBalancePage.getFirstCardBalance();
        val secondCardBalanceStart = cardBalancePage.getSecondCardBalance();
        val transactionPage = pushSecondCardButton();
        val amount = generateValidAmount(secondCardBalanceStart);
        transactionPage.transferMoney(amount, getFirstCardNumber());
        transactionPage.invalidCard();
    }

}

