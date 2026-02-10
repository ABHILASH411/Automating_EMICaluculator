package utilites;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtility{
    public WebDriverWait wait;

    public WaitUtility(WebDriver driver){
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement waitForVisible(WebElement ele){
        return wait.until(ExpectedConditions.visibilityOf(ele));
    }

    public WebElement waitForClickable(WebElement ele){
        return wait.until(ExpectedConditions.elementToBeClickable(ele));
    }

}
