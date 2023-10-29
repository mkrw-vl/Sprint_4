package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPageAboutRent {
    private final WebDriver driver;

    //Поле Когда привезти самокат
    private final By rentDateField = By.xpath(".//input[contains(@placeholder, 'Когда привезти самокат')]");
    //Календарь поля когда привезти самокат
    private final By rentDayCalendar = By.className("react-datepicker__month-container");

    //День в календаре
    //private final By rentDateValue = By.className("react-datepicker__day");


    //Поле срока аренды
    private final By rentPeriodField = By.xpath(".//div[text() = '* Срок аренды']");
    //Выпадающий список с выбором срока аренды
    private final By rentPeriodValue = By.xpath(".//div[text() = 'двое суток']");
    //Поле комментария для курьера
    private final By orderComment = By.xpath(".//input[contains(@placeholder, 'Комментарий для курьера')]");
    //Кнопка Заказать
    private final By orderButton = By.xpath(".//div[contains(@class , 'Order_Buttons')]/button[text () = 'Заказать']");
    //Кнопка "Да" в окне "Хотите оформить заказ?"
    private final By yesButton = By.xpath(".//div[contains(@class, 'Order_Buttons')]/button[text() = 'Да']");
    //Статус заказа
    private final By orderStatus = By.xpath(".//div[contains(@class, 'Order_ModalHeader__3FDaJ') and (text() = 'Заказ оформлен')]");

    public OrderPageAboutRent(WebDriver driver) {
        this.driver = driver;
    }

    //Выбираем дату аренды из календаря
    public void setRentDate(int day) {
        driver.findElement(rentDateField).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(rentDayCalendar));
        By dateElement = By.xpath(String.format("//div[contains(@class,'react-datepicker__day') and text()='%d']", day));
        driver.findElement(dateElement).click();
    }

    //Устанавливаем срок аренды
    public void setRentPeriod(String period) {
        driver.findElement(rentPeriodField).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(rentPeriodValue));
        driver.findElement(By.xpath(String.format(".//div[text() = '%s']", period))).click();
    }

    //Выбираем цвет самоката
    public void setScooterColor(String color) {
        driver.findElement(By.id(String.format("%s", color))).click();
    }

    //Пишем комментарий курьеру
    public void sendComment(String comment) {
        driver.findElement(orderComment).sendKeys(comment);
    }

    //Нажимаем на кнопку заказать
    public void pushOrderButton() {
        driver.findElement(orderButton).click();
    }

    //Нажимаем да на вопрос "Хотите оформить заказ?"
    public void completeOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(yesButton));
        driver.findElement(yesButton).click();
    }

    //Получаем статус заказа
    public String getOrderStatus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderStatus));
        return driver.findElement(orderStatus).getText();
    }

    //Вводим данные аренды
    public void fillAboutRentOrderPage(int day, String period, String color, String comment) {
        setRentDate(day);
        setRentPeriod(period);
        setScooterColor(color);
        sendComment(comment);
        pushOrderButton();
        completeOrder();
    }

}
