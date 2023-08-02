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

    @Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPage.class);
    }

    @Когда("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        verificationPage = loginPage.validLogin(login, password);
    }

    @И("пользователь вводит проверочный код из смс {string}")
    public void setValidCode(String verificationCode) {
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @И("пользователь нажимает пополнить баланс карты {string}")
    public void selectCardToTransfer(String cardNumber) {
        transferPage = dashboardPage.selectCardToTransfer(cardNumber);
    }

    @И("пользователь делает перевод с карты {string} на сумму {string}")
    public void makeTransfer(String cardNumber, String amount) {
        dashboardPage = transferPage.makeValidTransfer(amount, cardNumber);
    }

    @Тогда("пользователь попадает на страницу со списком карт.На первой карте {string}, на второй {string}")
    public void shouldHaveSuccessTransfer(String expectedBalanceOfFirstCard, String expectedBalanceOfSecondCard) {
        var actualBalanceOfFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardNumber());
        var actualBalanceOfSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardNumber());
        System.out.println(expectedBalanceOfFirstCard);
        System.out.println(expectedBalanceOfSecondCard);
        System.out.println(actualBalanceOfFirstCard);
        System.out.println(actualBalanceOfSecondCard);
        assertEquals(Integer.parseInt(expectedBalanceOfFirstCard), actualBalanceOfFirstCard);
        assertEquals(Integer.parseInt(expectedBalanceOfSecondCard), actualBalanceOfSecondCard);
    }

}
