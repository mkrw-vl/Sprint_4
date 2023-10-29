package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPageForWhom {
    private final WebDriver driver;
    //Хедер страницы
    private final By headerBlock = By.xpath(".//div[contains(@class , 'Header_Header')]");
    //Кнопка "Да все привыкли" в сообщении об использовании куки
    private final By cookieButton = By.xpath(".//button[contains(@class , 'App_Cookie') and (text() = 'да все привыкли')]");
    //Блок с флоу заказа самоката
    private final By orderFlowBlock = By.xpath(".//div[contains(@class , 'Home_ThirdPart')]");
    //Кнопка "Заказать" в хедере
    private final By headerPageOrderButton = By.xpath(".//button[contains(@class,'Button_Button')]");
    //Кнопка "Заказать" в футере
    private final By footerPageOrderButton = By.xpath(".//div[contains(@class, 'Home_ThirdPart')]//button[text()='Заказать']");
    //Поле имя вкладки "Для кого самокат"
    private final By nameField = By.xpath(".//input[contains(@placeholder, 'Имя')]");
    //Поле фамилия вкладки "Для кого самокат"
    private final By surnameField = By.xpath(".//input[contains(@placeholder, 'Фамилия')]");
    //Поле Адрес: куда привезти заказ вкладки "Для кого самокат"
    private final By addressField = By.xpath(".//input[contains(@placeholder, 'Адрес: куда привезти заказ')]");
    //Поле Станция метро вкладки "Для кого самокат"
    private final By metroStationField = By.xpath(".//input[contains(@placeholder, '* Станция метро')]");
    //Выпадающий список станций метро вкладки "Для кого самокат"
    private final By metroStationSelector = By.className("select-search__row");
    //Поле Телефон: на него позвонит курьер вкладки "Для кого самокат"
    private final By phoneNumberField = By.xpath(".//input[contains(@placeholder, 'Телефон: на него позвонит курьер')]");
    //Кнопка "Далее" вкладки "Для кого самокат"
    private final By nextButton = By.xpath(".//button[contains(text(), 'Далее')]");

    public OrderPageForWhom(WebDriver driver) {
        this.driver = driver;
    }
    //Нажимаем на кнопку с куки
    public void CookieButtonClick() {
        driver.findElement(cookieButton).click();
    }

    //Нажимаем на кнопку Заказать в зависимости от сценария
    public void orderButtonClick(int scenario) {
        if (scenario == 1) {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(headerBlock));
            driver.findElement(headerPageOrderButton).click();
        } else {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(orderFlowBlock));
            WebElement element = driver.findElement(footerPageOrderButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
            driver.findElement(footerPageOrderButton).click();
        }
    }

    //Вводим имя
    public void setName (String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    //Вводим фамилию
    public void setSurname (String surname) {
        driver.findElement(surnameField).sendKeys(surname);
    }

    //Вводим адрес доставки
    public void setAddress (String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    //Открываем список станций метро и выбираем нужную
    public void setMetroStation (int station) {
        driver.findElement(metroStationField).click();
        WebElement metroStation = driver.findElements(metroStationSelector).get(station);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(metroStationSelector));
        metroStation.click();
    }

    //Вводим номер телефона
    public void setPhoneNumber (String phoneNumber) {
        driver.findElement(phoneNumberField).sendKeys(phoneNumber);
    }

    //Вводим контактные данные для заказа и нажимаем кнопку "Далее"
    public void fillForWhomOrderPage(int scenario, String name, String surname, String address, int station, String phoneNumber) {
        CookieButtonClick();
        orderButtonClick(scenario);
        setName(name);
        setSurname(surname);
        setAddress(address);
        setMetroStation(station);
        setPhoneNumber(phoneNumber);
        driver.findElement(nextButton).click();
    }
}
