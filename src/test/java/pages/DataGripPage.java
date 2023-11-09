package pages;

import com.codeborne.selenide.*;
import com.example.selenidejetbrains.AllureLogger;
import org.openqa.selenium.*;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Author @markuma13
 */


public class DataGripPage {
    protected static final AllureLogger LOG = new AllureLogger(LoggerFactory.getLogger(DataGripPage.class));
    private final static By DISCOVER_ALL_FEATURES_BUTTON_LOCATOR = By.xpath(
            "//a[normalize-space()='Discover all features']");
    private final static By MESSAGE_ENTERING_INVALID_EMAIL_LOCATOR = By.cssSelector("[data-test=\"error-message\"]");
    private static final By DOWNLOAD_LIST_RESULT_LOCATOR = By.xpath("//button[normalize-space()='.exe']");
    private static final By RESULTS_COMBO_BOX_LOCATOR = By.xpath("//div[@data-focus-lock-disabled='false']");
    private static final By EXE_WINDOWS_LOCATOR = By.xpath("//span[normalize-space()='.exe (Windows)']");
    private static final By EMAIL_FIELD_LOCATOR = By.xpath("//input[@placeholder='Email']");
    private static final By SUBMIT_BUTTON_LOCATOR = By.xpath("//button[normalize-space()='Submit']");
    private static final By MOVE_TOOLTIP_ELEMENT_LOCATOR = By.xpath("//*[name()='path' and contains(@d,'M15 8a7 7 ')]");
    private static final By LINK_TEXT_VSN_LOCATOR = By.xpath("//span[normalize-space()='View sample newsletter']");
    private static final SelenideElement GET_TEXT_VIEW_SAMPLE_LOCATOR = $(By.xpath(
            "//p[contains(text(),'For DataGrip 2023.1, we focused entirely on qualit')]"));
    private static final By IMG_DATA_GRIP_RELEASE_LOCATOR = By.cssSelector("img[height='325']");
    private static final By DOWNLOAD_BUTTON_CAP_LOCATOR = By.cssSelector("div[id='js-menu-second'] a:nth-child(7)");
    private static final By PRICING_BUTTON_CAP_LOCATOR = By.xpath("(//a[@href='/datagrip/buy/'])[2]");
    private static final SelenideElement LINK_PARTY_SERVICES = $(By.xpath("//a[normalize-space()='third-party services']"));
    public ElementsCollection getComboBoxes() {
        $(DOWNLOAD_LIST_RESULT_LOCATOR).click();
        ElementsCollection comboBoxes = $$(RESULTS_COMBO_BOX_LOCATOR).shouldHave(sizeGreaterThan(0));

        for (SelenideElement comboBoxElement : comboBoxes) {
            LOG.info("ComboBox DataGrip button download: " + comboBoxElement.getText());
        }

        return comboBoxes;
    }
    public DataGripPage clickImgDataGripRelease() {
        LOG.info("Клик по изоброжению на странице релиза");
        $(IMG_DATA_GRIP_RELEASE_LOCATOR).click();
        return this;
    }
    public DataGripPage clickLinkTextVSN() {
        LOG.info("Клик по тексту View sample newsletter");
        $(LINK_TEXT_VSN_LOCATOR).click();
        return this;
    }
    public DataGripPage switchToTab(int tabIndex) {
        int tabCount = WebDriverRunner.getWebDriver().getWindowHandles().size();

        if (tabCount >= tabIndex) {
            String tabToSwitch = WebDriverRunner.getWebDriver().getWindowHandles().toArray(new String[0])[tabIndex - 1];
            Selenide.switchTo().window(tabToSwitch);
        } else {
            System.out.println("Запрошенной вкладки не существует");
        }
        return this;
    }
    public void clickDiscoverButton() {
        LOG.info("Клик на кнопку Discover all features");
        $(DISCOVER_ALL_FEATURES_BUTTON_LOCATOR).click();
    }
    public void clickPricingButton() {
        LOG.info("Клик на кнопку Pricing");
        $(PRICING_BUTTON_CAP_LOCATOR).click();
    }
    public void clickLinkPartyServices() {
        $(MOVE_TOOLTIP_ELEMENT_LOCATOR).shouldBe(visible);
        LOG.info("Прошли ожидание элементов");

        $(MOVE_TOOLTIP_ELEMENT_LOCATOR).hover().click();

        $(LINK_PARTY_SERVICES).click();
        LOG.info("Открытие страницы third-parties через Tooltip");
    }
    public void enterEmail(String email) {
        LOG.info("Ввод email");
        $(EMAIL_FIELD_LOCATOR).setValue(email);
        $(SUBMIT_BUTTON_LOCATOR).click();
    }
    public void waitSleep() {
        Selenide.sleep(50000);
    }
    public boolean checkIfDownloadButtonIsClickableCap() {
        LOG.info("Проверка активности кнопки загрузки");
        return $(DOWNLOAD_BUTTON_CAP_LOCATOR).isEnabled();
    }
    public boolean checkIfPricingButtonIsClickableCap() {
        LOG.info("Проверка активности кнопки Pricing");
        return $(PRICING_BUTTON_CAP_LOCATOR).isEnabled();
    }
    public boolean checkButtonExeWindowsComboBox() {
        LOG.infoWithScreenshot("Проверка активаности кнопки exe.Windows в чекбоксе");
        return $(EXE_WINDOWS_LOCATOR).isEnabled();
    }
    public String checkGetTextViewSampleOpen() {
        LOG.info("Проверка наличия текста");
        String value = $(GET_TEXT_VIEW_SAMPLE_LOCATOR).getText();
        LOG.info("Получен текст: \"" + value + "\"");
        return value;
    }
    public String enterInvalidEmailGetText(String email) {
        enterEmail(email);
        LOG.infoWithScreenshot("Получение сообщения при вводе не валидного email");
        return $(MESSAGE_ENTERING_INVALID_EMAIL_LOCATOR).getText();
    }
}
