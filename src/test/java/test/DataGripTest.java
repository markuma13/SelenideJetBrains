package test;


import com.example.selenidejetbrains.MyExtension;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.*;
import pages.DataGripPage;
import pages.DataGripPricingPage;

import static com.codeborne.selenide.WebDriverRunner.*;
import static com.codeborne.selenide.Selenide.*;
import static com.example.selenidejetbrains.AssertionHelper.*;

/**
 * Author @markuma13
 */

@ExtendWith(MyExtension.class)
@DisplayName("Класс тестов для проверки функционала страницы /datagrip")
public class DataGripTest extends BaseTest {
    DataGripPage dataGripPage;
    DataGripPricingPage dataGripPricingPage;

    @BeforeEach
    @Step("Переход на страницу DataGrip")
    public void setUp() {
        dataGripPage = page();
        dataGripPricingPage = page();
        open("https://www.jetbrains.com/datagrip/");
    }

    @Test
    @Step("Проверка открытия страницы /datagrip/features")
    @DisplayName("Проверка открытия страницы /datagrip/features")
    public void activeDiscoveryPage() {
        dataGripPage.clickDiscoverButton();

        String expectedUrl = "https://www.jetbrains.com/datagrip/features/";
        assertEqualsWithMessage(expectedUrl, url(), "Не верная ссылка");
    }

    @Test
    @DisplayName("Проверка ввода пустого email")
    public void invalidCheckValidateEmail() {
        String emptyEmail = dataGripPage.enterInvalidEmailGetText("");
        String actualTextValidate = "This field is required";

        assertEqualsWithMessage(emptyEmail, actualTextValidate, "Неккоректный текст сообщения валидации");
    }

    @ParameterizedTest(name = "#{index} - Проверка ввода на невалидность Email {0}")
    @CsvSource({"inside", "%%%/%%%", "pers@lmotion"})
    @DisplayName("Проверка ввода невалидного Email")
    public void checkInvalidEnterEmail(String email) {
        String entering = dataGripPage.enterInvalidEmailGetText(email);
        String actualTextValidate = "E-mail address is not correct";

        assertEqualsWithMessage(entering, actualTextValidate, "Неккоректный текст сообщения валидации");
    }

    @Test
    @DisplayName("Проверка открытия страницы third-parties через tooltip")
    public void tooltipVisibilityElementsClickPartyServices() {
        dataGripPage.clickLinkPartyServices();
        dataGripPage.switchToTab(2);

        String expectedUrl = "https://www.jetbrains.com/legal/docs/privacy/third-parties/";
        assertEqualsWithMessage(expectedUrl, url(), "Открыта неверная ссылка");
    }

    @Test
    @DisplayName("Проверка открытие страницы релиза и ожидаемого на нем текста")
    public void checkLinkTextVSN() {
        dataGripPage.clickLinkTextVSN();
        dataGripPage.switchToTab(2);
        String expectedText = "For DataGrip 2023.1, we focused entirely on quality. We’ve fixed many " +
                "bugs that had accumulated in our public issue tracker " +
                "(not the most fun part of the job, but important nonetheless).";

        assertEqualsWithMessage(expectedText, dataGripPage.checkGetTextViewSampleOpen(),
                "Ожидаемый текст не найден на странице");
    }

    @Test
    @DisplayName("Проверка активаности кнопки Download в шапке")
    public void checkActivityButtonDownloadCap() {
        assertTrueWithMessage(dataGripPage.checkIfDownloadButtonIsClickableCap(),
                "Кнопка скачивания не активна");
    }

    @Test
    @DisplayName("Проверка активаности кнопки Pricing в шапке")
    public void checkActivityButtonPricingCap() {
        assertTrueWithMessage(dataGripPage.checkIfPricingButtonIsClickableCap(),
                "Кнопка скачивания не активна");
        dataGripPage.clickPricingButton();
    }

    @Test
    @DisplayName("Проверка комбобоксов и активности кнопки exe.Windows")
    public void checkPageDataGripComboBox() {
        assertNotEmptyWithMessage(dataGripPage.getComboBoxes(),
                "Список комбобоксов пустой");
        assertTrueWithMessage(dataGripPage.checkIfDownloadButtonIsClickableCap(),
                "Кнопка скачивания не активна");
    }

    @Test
    @DisplayName("Проверка открытие страницы через баннер и активность ComboBox")
    public void checkPageWhatNewComboBox() {
        dataGripPage
                .clickLinkTextVSN()
                .switchToTab(2)
                .clickImgDataGripRelease()
                .switchToTab(3);

        assertEqualsWithMessage(
                "https://www.jetbrains.com/datagrip/whatsnew/?utm_source=marketo&utm_medium=email&" +
                        "utm_campaign=datagrip_2023_1_newsletter&utm_content=newsletter&p=banner",
                url(), "Открыта неверная ссылка");

        assertNotEmptyWithMessage(dataGripPage.getComboBoxes(), "Список комбобоксов пустой");
        assertTrueWithMessage(dataGripPage.checkButtonExeWindowsComboBox(), "Кнопка не активна");
    }

    @Test
    @DisplayName("Проверка сумм продуктов на страницы цен")
    public void checkPricesPageDataGrip() {
        dataGripPage.clickPricingButton();
        assertEqualsWithMessage("https://www.jetbrains.com/datagrip/buy/#commercial", url(),
                "Не удалось перейти на ожидаемую страницу!");

        String priceAllPackYears = "US $779.00";
        assertEqualsWithMessage(priceAllPackYears, dataGripPricingPage.checkPriceAllProductsPack(),
                "Не верная сумма AllPackYears");

        String priceDataGripBase = "US $229.00";
        assertEqualsWithMessage(priceDataGripBase, dataGripPricingPage.checkPriceDataGripBasesBiling(),
                "Не верная YearsBases сумма");

        dataGripPricingPage.clickSwitchTariff();

        String priceMonthlyDataGripBase = "US $22.90";
        assertEqualsWithMessage(priceMonthlyDataGripBase, dataGripPricingPage.checkPriceMonthlyBillingDataGrip(),
                "Не верная сумма MonthlyBase");

        String priceMonthlyAllProductsPack = "US $77.90";
        assertEqualsWithMessage(priceMonthlyAllProductsPack, dataGripPricingPage.checkPriceMonthlyBillingAllProductsPack(),
                "Не верная сумма MonthlyAllPack");
    }

    @Test
    @DisplayName("Проверка заголовка открытой страницы Subscription Options через FAQ в разделе Pricing")
    public void checkSubscriptionOptions() {
        dataGripPage.clickPricingButton();
        dataGripPricingPage.clickPricingFaq();
        dataGripPricingPage.clickLinkSubscriptionOptions();
        dataGripPage.switchToTab(2);

        String expectedTitle = "Compare Subscription Options - JetBrains Toolbox";
        assertEqualsWithMessage(expectedTitle, dataGripPricingPage.checkTitlePage(),
                "Заголовок страницы не совпадает с ожидаемым");
    }

    @Test
    @DisplayName("Проверка заголовка открытой страницы Licensing And Purchasing через FAQ в разделе Pricing")
    public void checkLicensingAndPurchasing() {
        dataGripPage.clickPricingButton();
        dataGripPricingPage.clickPricingFaq();
        dataGripPricingPage.clickLinkFaq();
        dataGripPage.switchToTab(2);
        waitSleep(50000);

        String expectedTitle = "Licensing and Purchasing FAQ";
        assertEqualsWithMessage(expectedTitle, dataGripPricingPage.checkTitlePage(),
                "Заголовок страницы не совпадает с ожидаемым");
    }
}
