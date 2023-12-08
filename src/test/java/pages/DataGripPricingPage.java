package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static pages.DataGripPage.LOG;

/**
 * Author @markuma13
 */

public class DataGripPricingPage {
    private static final SelenideElement PRICE_YEARLY_BILLING_DATA_GRIP = $(By.xpath(
            "(//div[@class='nowrap rs-subtitle-2 rs-subtitle-2_theme_light'])[1]"));
    private static final SelenideElement PRICE_YEARLY_BILLING_ALL_PRODUCTS_PACK = $(By.xpath(
            "(//div[@class='nowrap rs-subtitle-2 rs-subtitle-2_theme_light'])[2]"));
    private static final SelenideElement SWITCH_PRICE_TARIFF = $(By.xpath(
            "(//span[@data-test='switcher']//button)[2]"));
    private static final SelenideElement PRICE_MONTHLY_BILLING_DATA_GRIP = $(By.xpath(
            "(//div[@class='nowrap rs-subtitle-2 rs-subtitle-2_theme_light'])[1]"));
    private static final SelenideElement PRICE_MONTHLY_BILLING_ALL_PRODUCTS_PACK = $(By.xpath(
            "(//div[@class='nowrap rs-subtitle-2 rs-subtitle-2_theme_light'])[2]"));
    private static final SelenideElement FAQ_BUTTON = $(By.xpath("//div[text()='FAQ']"));
    private static final SelenideElement LINK_TEXT_SUBSCRIPTION_OPTIONS = $(By.xpath(
            "//a[normalize-space()='Subscription Options']"));
    private static final SelenideElement LINK_TEXT_FAQ = $(By.xpath(
            "//a[@data-test='buy-page-faq-faq']"));

    public String checkPriceDataGripBasesBiling() {
        LOG.info("Проверка наличия суммы тарифа");
        String value = $(PRICE_YEARLY_BILLING_DATA_GRIP).getText();
        LOG.info("Получен текст базовой годовой суммы: \"" + value + "\"");
        return value;
    }

    public String checkPriceAllProductsPack() {
        LOG.info("Проверка наличия суммы тарифа");
        String value = $(PRICE_YEARLY_BILLING_ALL_PRODUCTS_PACK).getText();
        LOG.info("Получен текст полного пакета годовой суммы: \"" + value + "\"");
        return value;
    }

    public String checkPriceMonthlyBillingDataGrip() {
        LOG.info("Проверка суммы тарифа");
        String value = $(PRICE_MONTHLY_BILLING_DATA_GRIP).getText();
        LOG.info("Получен текст месячной базовой суммы: \"" + value + "\"");
        return value;
    }

    public String checkPriceMonthlyBillingAllProductsPack() {
        LOG.info("Проверка суммы тарифа");
        String value = $(PRICE_MONTHLY_BILLING_ALL_PRODUCTS_PACK).getText();
        LOG.info("Получен текст месячного пакета сумма: \"" + value + "\"");
        return value;
    }

    public String checkTitlePage() {
        LOG.info("Проверка наличия title");
        String actualTitle = executeJavaScript("return document.title");
        LOG.info("Получен текст title страницы: \"" + actualTitle + "\"");
        return actualTitle;
    }

    public void clickSwitchTariff() {
        LOG.info("Переключение тарифа");
        $(SWITCH_PRICE_TARIFF).click();
    }

    public void clickPricingFaq() {
        LOG.info("Клик по кнопки Faq в разделе Subscription");
        $(FAQ_BUTTON).click();
    }

    public void clickLinkSubscriptionOptions() {
        LOG.info("Клик по текстовой ссылке Subscript on Options");
        $(LINK_TEXT_SUBSCRIPTION_OPTIONS).click();
    }

    public void clickLinkFaq() {
        LOG.info("Клик по текстовой ссылке FAQ");
        $(LINK_TEXT_FAQ).click();
    }
}
