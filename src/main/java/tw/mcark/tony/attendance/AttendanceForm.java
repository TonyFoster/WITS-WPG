package tw.mcark.tony.attendance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AttendanceForm {

    ChromeOptions options = new ChromeOptions();

    public AttendanceForm() {
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-software-rasterizer");
        options.addArguments("--remote-allow-origins=*");
        options.setBinary("/usr/bin/google-chrome");
    }

    public boolean check(AttendanceRequest request) {
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://forms.office.com/pages/responsepage.aspx?id=eXwE3tnU80qR3rxEsFgUkLLKmSniPx1EiibSk0QcVUpUOU9TN0FGV1BZWEczSE5XU0dFV1lXNElCTyQlQCN0PWcu");  // Open the target URL

        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name("r66a59b1f497c4b3795164ca3b0afb2bf")));

        List<WebElement> selector1 = driver.findElements(By.name("r66a59b1f497c4b3795164ca3b0afb2bf"));
        List<WebElement> selector2 = driver.findElements(By.name("r0b09f48cc5c04189a8e8a24afc0f3eaf"));
        WebElement textarea = driver.findElement(By.tagName("textarea"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[data-automation-id='submitButton']"));

        switch (request.getOffice()) {
            case "ERP-A":
                selector1.get(0).click();
                break;
            case "ERP-B":
                selector1.get(1).click();
                break;
            case "應用":
                selector1.get(2).click();
                break;
            case "LaaS":
                selector1.get(3).click();
                break;
        }

        switch (request.getType()) {
            case CHECK_IN:
                selector2.get(0).click();
                break;
            case CHECK_OUT:
                selector2.get(1).click();
                break;
            case TAKE_LEAVE:
                selector2.get(2).click();
                break;
        }

        textarea.sendKeys(request.getName());
//        submitButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Your response was submitted.')]")));

        driver.quit();
        return true;
    }

}
