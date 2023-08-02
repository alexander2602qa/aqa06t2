package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement amountInput = $("[data-test-id=amount] input");
    private final SelenideElement fromInput = $("[data-test-id=from] input");
    private final SelenideElement transferHead = $(byText("Пополнение карты"));

    public TransferPage() {
        transferHead.shouldBe(visible);
    }

    public DashboardPage makeValidTransfer(String amountToTransfer, String cardNumber) {
        makeTransfer(amountToTransfer, cardNumber);
        return new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, String cardNumber) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardNumber);
        transferButton.click();
    }


}
