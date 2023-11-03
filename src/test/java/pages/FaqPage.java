package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.util.List;

public class FaqPage {
    private final WebDriver driver;
    private final By faqBlock = By.cssSelector(".Home_FAQ__3uVm4");
    public FaqPage(WebDriver driver) {
        this.driver = driver;
    }

    //Проверяем текст вопроса в FAQ
    public String checkAccordionQuestion(int number) {
        List<WebElement> getHeadersButtonAccordion = driver.findElements(By.cssSelector(".accordion__button"));
        return getHeadersButtonAccordion.get(number).getText();
    }

    //Получаем текст ответа из FAQ
    public String getAccordionElementText(int number) {
        List<WebElement> elementOfAccordions = driver.findElements(By.cssSelector(".accordion__panel p"));
        return elementOfAccordions.get(number).getText();
    }

    //Получаем кнопку из FAQ по заданному номеру
    public WebElement getAccordionButton(int number) {
        List<WebElement> elementOfAccordions = driver.findElements(By.cssSelector(".accordion__item"));
        return elementOfAccordions.get(number);
    }

    //Нажимаем на вопрос, получаем текст ответа
    public String clickAccordionAndRetrieveText(int number) {
        WebElement button = getAccordionButton(number);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);
        button.click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(faqBlock));
        return getAccordionElementText(number);
    }
}
