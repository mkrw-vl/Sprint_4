package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.OrderPageForWhom;
import pages.OrderPageAboutRent;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class OrderTest {
    WebDriver driver;
    private final int OrderScenario;
    private final String OrderName;
    private final String OrderSurname;
    private final String OrderAddress;
    private final int OrderStation;
    private final String OrderPhoneNumber;
    private final int OrderDay;
    private final String OrderPeriod;
    private final String OrderColor;
    private final String OrderComment;


    public OrderTest (int OrderScenario, String OrderName, String OrderSurname, String OrderAddress, int OrderStation, String OrderPhoneNumber, int OrderDay, String OrderPeriod, String OrderColor, String OrderComment) {
        this.OrderScenario = OrderScenario;
        this.OrderName = OrderName;
        this.OrderSurname = OrderSurname;
        this.OrderAddress = OrderAddress;
        this.OrderStation = OrderStation;
        this.OrderPhoneNumber = OrderPhoneNumber;
        this.OrderDay = OrderDay;
        this.OrderPeriod = OrderPeriod;
        this.OrderColor = OrderColor;
        this.OrderComment = OrderComment;
    }

    @Parameterized.Parameters
    public static Object[][] getInfo() {
        return new Object[][]{
                {1, "Андрей", "Макаров", "ул. Ленина, 67", 2, "+79211111111", 1, "сутки", "black", "Торопитесь, я умираю."},
                {2, "Саша", "Шугин", "ул. Основная, 16", 3, "+79311111111", 1, "трое суток", "grey", "Заходите на чай!"},
        };
    }
    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }
    @Test
    public void makeOrder() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        OrderPageForWhom orderPageForWhom = new OrderPageForWhom(driver);
        orderPageForWhom.fillForWhomOrderPage(OrderScenario, OrderName, OrderSurname, OrderAddress, OrderStation, OrderPhoneNumber);
        OrderPageAboutRent orderPageAboutRent = new OrderPageAboutRent(driver);
        orderPageAboutRent.fillAboutRentOrderPage(OrderDay,OrderPeriod,OrderColor,OrderComment);
        assertThat("Сообщение об успешном заказе не отобразилось", orderPageAboutRent.getOrderStatus(), containsString("Заказ оформлен"));
    }
    @After
    public void quitBrowser() {
        driver.quit();
    }
}
