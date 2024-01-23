package by.veremei.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {
    private final SelenideElement btnDeleteBookFromCollection = $("#delete-record-undefined"),
                                  btnConfirmDeleteBookInModal = $("#closeSmallModal-ok"),
                                  modalDeleteBook = $(".modal-content");
    public void deleteBookFromUserCollection() {
        btnDeleteBookFromCollection.click();
        btnConfirmDeleteBookInModal.click();
        modalDeleteBook.shouldNotBe(visible);
    }

    private final SelenideElement divWithReportNoRows = $(".rt-noData");
    public void checkSuccessfulBookDelete(String report) {
        divWithReportNoRows.shouldBe(visible);
        divWithReportNoRows.shouldHave(text(report));
    }
}
