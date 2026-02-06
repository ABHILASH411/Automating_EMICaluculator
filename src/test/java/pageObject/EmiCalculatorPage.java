package pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilites.ExcelUtils;

import java.io.IOException;
import java.time.Duration;

public class EmiCalculatorPage extends BasePage {

    private final WebDriverWait wait;

    public EmiCalculatorPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ===== Locators =====
    @FindBy(xpath = "//a[normalize-space(text())='Car Loan']")
    private WebElement carLoanTab;

    @FindBy(xpath = "//input[@name='loanamount']")
    private WebElement loanAmountInput;

    @FindBy(xpath = "//input[@name='loaninterest']")
    private WebElement interestRateInput;

    @FindBy(xpath = "//input[@name='loanterm']")
    private WebElement loanTenureInput;

    @FindBy(xpath = "//div[@id='emitotalinterest']//span")
    private WebElement totalInterestSpan;

    @FindBy(xpath = "//div[@id='emiamount']//span")
    private WebElement emiAmountSpan;

    @FindBy(xpath = "//div[@id='emitotalamount']//span")
    private WebElement totalamnt;

    // ===== Private helpers (JS) =====
    private void jsSetValue(WebElement element, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", element, value);
        // Fire input/change so the page reacts to the new value
        js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
        js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", element);
    }

    private void jsScrollBy(int x, int y) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }

    // ===== Actions =====
    public void openCarLoanTab() {
        wait.until(ExpectedConditions.elementToBeClickable(carLoanTab)).click();
    }

    public void setLoanAmount(String amount) {
        wait.until(ExpectedConditions.visibilityOf(loanAmountInput));
        loanAmountInput.clear();
        jsSetValue(loanAmountInput, amount);
    }

    public void setInterestRate(String ratePercent) {
        wait.until(ExpectedConditions.visibilityOf(interestRateInput));
        interestRateInput.clear();
        jsSetValue(interestRateInput, ratePercent);
    }

    public void setLoanTenureYears(String years) {
        wait.until(ExpectedConditions.visibilityOf(loanTenureInput));
        loanTenureInput.clear();
        jsSetValue(loanTenureInput, years);
    }

    public void scrollResultsIntoView() {
        jsScrollBy(0, 500);
    }

    public void TotalInterestAmount(int row) throws IOException {

        String path=System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\EmiData.xlsx";

            String data1 = wait.until(ExpectedConditions.visibilityOf(totalInterestSpan)).getText();
            logger.info("Got Interest Amount");
            String data2 = wait.until(ExpectedConditions.visibilityOf(emiAmountSpan)).getText();
            logger.info("Got EMI Amount");
            String data3 = wait.until(ExpectedConditions.visibilityOf(totalamnt)).getText();
            logger.info("Got Total Amount");
            ExcelUtils.setCellData(path,"Sheet2",row,0,data1);

            ExcelUtils.setCellData(path,"Sheet2",row,1,data2);
            ExcelUtils.setCellData(path,"Sheet2",row,2,data3);
            logger.info("Data added to ExcelSheet");

    }


}
