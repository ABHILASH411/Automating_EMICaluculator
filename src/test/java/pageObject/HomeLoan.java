package pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilites.ExcelUtils;

import java.io.IOException;
import java.util.List;

public class HomeLoan extends BasePage {

    private WebDriver driver;

    // ---------------------------
    // PageFactory WebElements
    // ---------------------------

    // Tab
    @FindBy(xpath = "//li[@id='menu-item-3293']")
    private WebElement homeLoanTab;

    // Inputs
    @FindBy(xpath = "//input[@id='homeprice']")
    private WebElement homePriceInput;

    @FindBy(xpath = "//input[@id='downpayment']")
    private WebElement downPaymentInput;

    @FindBy(id = "homeloaninsuranceamount")
    private WebElement loanInsuranceInput;

    @FindBy(id = "homeloanamount")
    private WebElement loanAmountInput;

    @FindBy(id = "homeloaninterest")
    private WebElement interestInput;

    @FindBy(id = "homeloanterm")
    private WebElement loanTermInput;

    @FindBy(id = "loanfees")
    private WebElement loanFeesInput;

    @FindBy(xpath = "//input[@id='startmonthyear']")
    private WebElement startMonthYearInput;

    @FindBy(id = "onetimeexpenses")
    private WebElement oneTimeExpensesInput;

    @FindBy(id = "propertytaxes")
    private WebElement propertyTaxesInput;

    @FindBy(id = "homeinsurance")
    private WebElement homeInsuranceInput;

    @FindBy(id = "maintenanceexpenses")
    private WebElement maintenanceExpensesInput;

    // Year row toggle target (kept same XPath)
    @FindBy(xpath = "//td[contains(text(),'2026')]")
    private WebElement paymentYearToggleCell;

    @FindBy(xpath = "//td[contains(text(),'2027')]")
    private WebElement paymentYearToggle;


    // ---------------------------
    // Constructor
    // ---------------------------
    public HomeLoan(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }


    // ---------------------------
    // Actions (method bodies unchanged in logic)
    // ---------------------------

    public void clickHomeLoanTab() {
        homeLoanTab.click();
    }

    public void setHomePrice(String value) {
        homePriceInput.clear();
        homePriceInput.sendKeys(value);
    }

    public void setDownPaymentPercent(String value) {
        downPaymentInput.clear();
        downPaymentInput.sendKeys(value);
    }

    public void setLoanInsurance(String value) {
        loanInsuranceInput.clear();
        loanInsuranceInput.sendKeys(value);
    }

    public void setLoanAmount(String value) {
        loanAmountInput.clear();
        loanAmountInput.sendKeys(value);
    }

    public void clearInterest(String value) {
        WebElement it = interestInput;
        it.click();                                  // focus
        it.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // select all (CMD on macOS)
        it.sendKeys(Keys.DELETE);                    // delete selected content
        it.sendKeys(value);                          // type new value
        it.sendKeys(Keys.TAB);                       // optional: commit/blur
    }

    public void homeloanterm1(String value) {
        WebElement it = loanTermInput;
        it.click();                                  // focus
        it.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // select all (CMD on macOS)
        it.sendKeys(Keys.DELETE);                    // delete selected content
        it.sendKeys(value);                          // type new value
        it.sendKeys(Keys.TAB);                       // optional: commit/blur
    }

    public void setLoanFees(String value) {
        loanFeesInput.clear();
        loanFeesInput.sendKeys(value);
    }

    public void openStartMonthYearPicker() {
        startMonthYearInput.click();
    }

    // NOTE: dynamic elements depend on input text; keeping driver.findElement(..) exactly as you had it.
    public void pickYearThenMonth(String targetYearText, String monthShortName) {
        while (true) {
            WebElement monthYear = driver.findElement(
                    By.xpath("//div[@class='datepicker-months']/descendant::th[@class='datepicker-switch']")
            );
            if (monthYear.getText().trim().equalsIgnoreCase(targetYearText.trim())) {
                break;
            }
            driver.findElement(
                    By.xpath("//div[@class='datepicker-months']/descendant::th[@class='prev']")
            ).click();
        }
        driver.findElement(By.xpath("//span[contains(text(), '" + monthShortName + "')] ")).click();
    }

    public void setOneTimeExpenses(String value) {
        oneTimeExpensesInput.clear();
        oneTimeExpensesInput.sendKeys(value);
    }

    public void setPropertyTaxes(String value) {
        propertyTaxesInput.clear();
        propertyTaxesInput.sendKeys(value);
    }

    public void setHomeInsurance(String value) {
        homeInsuranceInput.clear();
        homeInsuranceInput.sendKeys(value);
    }

    public void setMaintenanceExpenses(String value) {
        maintenanceExpensesInput.clear();
        maintenanceExpensesInput.sendKeys(value);
    }


    public void moveToYearElement() {

        Actions actions = new Actions(driver);
        WebElement ct = paymentYearToggleCell; // same XPath as original
        actions.moveToElement(ct).click().perform();
    }
        public void moveToNextYear() {
//            Actions actions = new Actions(driver);
            WebElement bt = paymentYearToggle; // same XPath as original
//            actions.moveToElement(bt).click().perform();

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", bt);


        }

    // Kept EXACT logic: list queries and Excel writing unchanged
    public void EnterYear() throws IOException {
        int excelRow = 0;
        int column = 0;

        List<WebElement> year = driver.findElements(
                By.xpath("//tr[@class='row no-margin yearlypaymentdetails']//td")
        );
        for (WebElement s : year) {
            String filepath = System.getProperty("user.dir")
                    + "\\src\\test\\resources\\TestData\\HomeLoan_TestData.xlsx";

            s.getText();
            ExcelUtils.setCellData(filepath, "HomeLoanTestCase(output)", excelRow + 1, column, s.getText());
            column++;
        }
    }

    // Kept loops, conditions, and XPaths exactly the same
    public void getYearlyPaymentDetailsText() throws IOException {

        // Outer rows from webpage (unchanged)
        List<WebElement> rows = driver.findElements(
                By.xpath("//tr[@class='row no-margin']")
        );

        int excelRow = 0;              // Excel row to write into
        final int COLS_PER_ROW = 7;    // you want 7 columns per row
        String filepath = System.getProperty("user.dir")
                + "\\src\\test\\resources\\TestData\\HomeLoan_TestData.xlsx";

        // Loop over each web row (UNCHANGED)
        for (WebElement r : rows) {

            // The slice of <td> belonging to this Excel row
            int rowStart = excelRow * COLS_PER_ROW;               // inclusive start
            int rowEndExclusive = (excelRow + 1) * COLS_PER_ROW;  // exclusive end

            int column = 0;       // Excel column index 0..6
            int cellIdx = 0;      // Running counter over ALL <td> elements

            // Your inner list (UNCHANGED)
            List<WebElement> data = driver.findElements(
                    By.xpath("//tr[@class='row no-margin']//td")
            );

            // Inner loop (UNCHANGED)
            for (WebElement d : data) {
                String val = d.getText().replace("₹", "");
                // Write ONLY the 7 cells that belong to this excelRow
                if (cellIdx >= rowStart && cellIdx < rowEndExclusive) {

                    ExcelUtils.setCellData(filepath, "HomeLoanTestCase(output)", excelRow + 2, column, val);
                    column++;

                    // Stop when 7 columns are filled
                    if (column == COLS_PER_ROW) {
                        break;
                    }
                }
                cellIdx++;
            }

            // Move to next Excel row
            excelRow++;
        }
    }
}