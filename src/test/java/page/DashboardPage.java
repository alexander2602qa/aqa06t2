package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final String balanceStringStart = "баланс: ";
    private final String balanceStringFinish = " р.";

    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list div");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(String cardNumber) {
        var text = cards.findBy(text(cardNumber.substring(15))).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(String cardNumber) {
        cards.findBy(text(cardNumber.substring(15))).$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStringStart);
        var finish = text.indexOf(balanceStringFinish);
        var value = text.substring(start + balanceStringStart.length(), finish);
        return Integer.parseInt(value);
    }

}
