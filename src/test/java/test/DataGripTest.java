package test;

import com.codeborne.selenide.Configuration;
import com.example.selenidejetbrains.MyExtension;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.*;
import pages.DataGripPage;
import pages.DataGripPricingPage;

import static com.codeborne.selenide.WebDriverRunner.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Author @markuma13
 */

@ExtendWith(MyExtension.class)
public class DataGripTest extends BaseTest {
    DataGripPage dataGripPage;
    DataGripPricingPage dataGripPricingPage;
    @BeforeEach
    @Step("Переход на страницу DataGrip")
    public void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        dataGripPage = page();
        dataGripPricingPage = page();
        open("https://www.jetbrains.com/datagrip/");
    }
    @Test
    @Step("Проверка открытия страницы /datagrip/features")
    @DisplayName("Проверка открытия страницы /datagrip/features")
    public void activeDiscoveryPage() {
        dataGripPage.clickDiscoverButton();
        assertEquals("https://www.jetbrains.com/datagrip/features/", url(), "Не верная ссылка");
    }
    @Test
    @DisplayName("Проверка ввода пустого email")
    public void invalidCheckValidateEmail() {
        String emptyEmail = dataGripPage.enterInvalidEmailGetText("");
        assertEquals(emptyEmail, "This field is required.", "Неккоректный текст сообщения валидации");
    }
    @ParameterizedTest(name = "#{index} - Проверка ввода на невалидность Email {0}")
    @CsvSource({"insidi", "%%%/%%%", "pers@lmotion"})
    @DisplayName("Проверка ввода невалидного Email")
    public void checkInvalidEnterEmail(String email) {
        String entering = dataGripPage.enterInvalidEmailGetText(email);
        assertEquals(entering, "Please enter a valid email address.", "Неккоректный текст сообщения валидации");
    }
    @Test
    @DisplayName("Проверка открытия страницы third-parties через tooltip")
    public void tooltipVisabilityElementsClickPartyServices() {
        dataGripPage.clickLinkPartyServices();
        dataGripPage.switchToTab(2);

        assertEquals("https://www.jetbrains.com/legal/docs/privacy/third-parties/", url(), "Открыта неверная ссылка");
    }
    @Test
    @DisplayName("Проверка открытие страницы релиза и ожидаемого на нем текста")
    public void checkLinkTextVSN() {
        dataGripPage.clickLinkTextVSN();
        dataGripPage.switchToTab(2);
        String expectedText = "For DataGrip 2023.1, we focused entirely on quality. We’ve fixed many " +
                "bugs that had accumulated in our public issue tracker " +
                "(not the most fun part of the job, but important nonetheless).";
        assertEquals(expectedText, dataGripPage.checkGetTextViewSampleOpen(), "Ожидаемый текст не найден на странице");
    }
    @Test
    @DisplayName("Проверка активаности кнопки Download в шапке")
    public void checkActivityButtonDownloadCap() {
        assertTrue(dataGripPage.checkIfDownloadButtonIsClickableCap(), "Кнопка скачивания не активна");
    }
    @Test
    @DisplayName("Проверка активаности кнопки Pricing в шапке")
    public void checkActivityButtonPricingCap() {
        assertTrue(dataGripPage.checkIfPricingButtonIsClickableCap(), "Кнопка скачивания не активна");
        dataGripPage.clickPricingButton();
    }
    @Test
    @DisplayName("Проверка комбобоксов и активности кнопки exe.Windows")
    public void checkPageDataGripComboBox() {
        assertFalse(dataGripPage.getComboBoxes().isEmpty(), "Список комбобоксов пустой");
        assertTrue(dataGripPage.checkButtonExeWindowsComboBox(), "Кнопка не активна");
    }
    @Test
    @DisplayName("Проверка открытие страницы через баннер и активность ComboBox")
    public void checkPageWhatSnewComboBox() {
        dataGripPage
                .clickLinkTextVSN()
                .switchToTab(2)
                .clickImgDataGripRelease()
                .switchToTab(3);

        assertEquals("https://www.jetbrains.com/datagrip/whatsnew/?utm_source=marketo&utm_medium=email&" +
                        "utm_campaign=datagrip_2023_1_newsletter&utm_content=newsletter&p=banner",
                url(), "Открыта неверная ссылка");

        assertFalse(dataGripPage.getComboBoxes().isEmpty(), "Список комбобоксов пустой");
        assertTrue(dataGripPage.checkButtonExeWindowsComboBox(), "Кнопка не активна");
    }
    @Test
    @DisplayName("Проверка сумм продуктов на страницы цен")
    public void checkPricesPageDataGrip() {
        dataGripPage.clickPricingButton();
        assertEquals("https://www.jetbrains.com/datagrip/buy/#commercial", url(), "Не удалось перейти на ожидаемую страницу!");

        String priceAllPackYears = "US $779.00";
        assertEquals(priceAllPackYears, dataGripPricingPage.checkPriceAllProductsPack(), "Не верная сумма AllPackYears");

        String priceDataGripBase = "US $229.00";
        assertEquals(priceDataGripBase, dataGripPricingPage.checkPriceDataGripBasesBiling(), "Не верная YearsBases сумма");

        dataGripPricingPage.clickSwitchTariff();

        String priceMonthlyDataGripBase = "US $22.90";
        assertEquals(priceMonthlyDataGripBase, dataGripPricingPage.checkPriceMonthlyBillingDataGrip(), "Не верная сумма MonthlyBase");

        String priceMonthlyAllProductsPack = "US $77.90";
        assertEquals(priceMonthlyAllProductsPack, dataGripPricingPage.checkPriceMonthlyBillingAllProductsPack(), "Не верная сумма MonthlyAllPack");
    }
    @Test
    @DisplayName("Проверка заголовка открытой страницы Subscription Options через FAQ в разделе Pricing")
    public void checkSubscriptionOptions() {
        dataGripPage.clickPricingButton();
        dataGripPricingPage.clickPricingFaq();
        dataGripPricingPage.clickLinkSubscriptionOptions();
        dataGripPage.switchToTab(2);

        String expectedTitle = "Compare Subscription Options - JetBrains Toolbox";
        assertEquals(expectedTitle, dataGripPricingPage.checkTitlePage(),
                "Заголовок страницы не совпадает с ожидаемым");
    }
    @Test
    @DisplayName("Проверка заголовка открытой страницы Licensing And Purchasing через FAQ в разделе Pricing")
    public void checkLicensingAndPurchasing() {
        dataGripPage.clickPricingButton();
        dataGripPricingPage.clickPricingFaq();
        dataGripPricingPage.clickLinkFaq();
        dataGripPage.switchToTab(2);
        dataGripPage.waitSleep();

        String expectedTitle = "Licensing and Purchasing FAQ";
        assertEquals(expectedTitle, dataGripPricingPage.checkTitlePage(),
                "Заголовок страницы не совпадает с ожидаемым");
    }
}
