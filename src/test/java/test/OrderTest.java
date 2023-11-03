package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.OrderPageForWhom;
import pages.OrderPageAboutRent;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderTest {
    WebDriver driver;
    private final int orderScenario;
    private final String orderName;
    private final String orderSurname;
    private final String orderAddress;
    private final int orderStation;
    private final String orderPhoneNumber;
    private final int orderDay;
    private final String orderPeriod;
    private final String orderColor;
    private final String orderComment;

    public OrderTest (int orderScenario, String orderName, String orderSurname, String orderAddress, int orderStation, String orderPhoneNumber, int orderDay, String orderPeriod, String orderColor, String orderComment) {
        this.orderScenario = orderScenario;
        this.orderName = orderName;
        this.orderSurname = orderSurname;
        this.orderAddress = orderAddress;
        this.orderStation = orderStation;
        this.orderPhoneNumber = orderPhoneNumber;
        this.orderDay = orderDay;
        this.orderPeriod = orderPeriod;
        this.orderColor = orderColor;
        this.orderComment = orderComment;
    }

    @Parameterized.Parameters
    public static Object[][] getInfo() {
        return new Object[][]{
                {1, "Андрей", "Макаров", "ул. Ленина, 67", 2, "+79211111111", 1, "сутки", "black", "Торопитесь, я умираю."},
                {1, "Саша", "Шугин", "ул. Основная, 16", 3, "+79311111111", 1, "трое суток", "grey", "Заходите на чай!"},
        };
    }

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @Test
    public void makeOrderScenario1 () {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        OrderPageForWhom orderPageForWhom = new OrderPageForWhom(driver);
        orderPageForWhom.fillForWhomOrderPage(orderScenario, orderName, orderSurname, orderAddress, orderStation, orderPhoneNumber);
        OrderPageAboutRent orderPageAboutRent = new OrderPageAboutRent(driver);
        orderPageAboutRent.fillAboutRentOrderPage(orderDay,orderPeriod,orderColor,orderComment);
        assertThat("Сообщение об успешном заказе не отобразилось", orderPageAboutRent.getOrderStatus(), containsString("Заказ оформлен"));
    }

    @Test
    public void makeOrderScenario2() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        OrderPageForWhom orderPageForWhom = new OrderPageForWhom(driver);
        orderPageForWhom.orderButtonClick(2);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderPageForWhom.getFormOrderFields()));
    }

    @After
    public void quitBrowser() {
        driver.quit();
    }
}
