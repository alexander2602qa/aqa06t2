package steps;

import com.codeborne.selenide.Selenide;
import data.DataHelper;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import page.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static TransferPage transferPage;

    @Пусть("открыта страница с формой авторизации {String}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPage.class);
    }

    @Когда("пользователь пытается авторизоваться с именем {String} и паролем {String}")
    public void loginWithNameAndPassword(String login, String password) {
        verificationPage = loginPage.validLogin(login, password);
    }

    @И("пользователь вводит проверочный код из смс {String}")
    public void setValidCode(String verificationCode) {
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @И("пользователь нажимает пополнить баланс карты {String}")
    public void selectCardToTransfer(String cardNumber) {
        transferPage = dashboardPage.selectCardToTransfer(cardNumber);
    }

    @И("пользователь делает перевод с карты {String} на сумму {String}")
    public void makeTransfer(String cardNumber, String amount) {
        dashboardPage = transferPage.makeValidTransfer(amount, cardNumber);
    }

    @Тогда("пользователь попадает на страницу со списком карт.На первой карте {String}, на второй {String}")
    public void shouldHaveSuccessTransfer(String expectedBalanceOfFirstCard, String expectedBalanceOfSecondCard) {
        var actualBalanceOfFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardNumber());
        var actualBalanceOfSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardNumber());
        assertEquals(expectedBalanceOfFirstCard, actualBalanceOfFirstCard);
        assertEquals(expectedBalanceOfSecondCard, actualBalanceOfSecondCard);
    }

}
