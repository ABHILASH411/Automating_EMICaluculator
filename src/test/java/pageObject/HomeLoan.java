package pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilites.ExcelUtils;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class HomeLoan extends BaseClass {

    //static int row=0;
    WebDriver driver;


    public HomeLoan(WebDriver driver) {
        super(driver);
        this.driver=driver;

    }


    public void clickHomeLoanTab() {
        driver.findElement(By.xpath("//li[@id='menu-item-3293']")).click();
    }

    public void setHomePrice(String value) {
        WebElement home = driver.findElement(By.xpath("//input[@id='homeprice']"));
        home.clear();
        home.sendKeys(value);
    }

    public void setDownPaymentPercent(String value) {
        WebElement down = driver.findElement(By.xpath("//input[@id='downpayment']"));
        down.clear();
        down.sendKeys(value);
    }

    public void setLoanInsurance(String value) {
        WebElement li = driver.findElement(By.id("homeloaninsuranceamount"));
        li.clear();
        li.sendKeys(value);
    }

    public void setLoanAmount(String value) {
        WebElement amt = driver.findElement(By.id("homeloanamount"));
        amt.clear();
        amt.sendKeys(value);
    }


    public void clearInterest(String value) {
        WebElement it = driver.findElement(By.id("homeloaninterest"));
        it.click(); // focus
        it.sendKeys(Keys.chord(Keys.CONTROL, "a")); // select all (use COMMAND on macOS)
        it.sendKeys(Keys.DELETE);                   // delete selected content
        it.sendKeys(value);                         // type new value
        it.sendKeys(Keys.TAB);                      // optional: commit/blur

    }

    public void homeloanterm1(String value) {
        WebElement it = driver.findElement(By.id("homeloanterm"));
        it.click(); // focus
        it.sendKeys(Keys.chord(Keys.CONTROL, "a")); // select all (use COMMAND on macOS)
        it.sendKeys(Keys.DELETE);                   // delete selected content
        it.sendKeys(value);                         // type new value
        it.sendKeys(Keys.TAB);                      // optional: commit/blur

    }

    public void setLoanFees(String value) {
        WebElement fees = driver.findElement(By.id("loanfees"));
        fees.clear();
        fees.sendKeys(value);
    }

    public void openStartMonthYearPicker() {
        WebElement start = driver.findElement(By.xpath("//input[@id='startmonthyear']"));
        start.click();

    }

    public void pickYearThenMonth(String targetYearText, String monthShortName) {
        while (true) {
            WebElement monthYear = driver.findElement(By.xpath("//div[@class='datepicker-months']/descendant::th[@class='datepicker-switch']"));
            if (monthYear.getText().trim().equalsIgnoreCase(targetYearText.trim())) {
                break;
            }
            driver.findElement(By.xpath("//div[@class='datepicker-months']/descendant::th[@class='prev']")).click();
        }
        driver.findElement(By.xpath("//span[contains(text(), '" + monthShortName + "')] ")).click();
    }

    public void setOneTimeExpenses(String value) {
        WebElement el = driver.findElement(By.id("onetimeexpenses"));
        el.clear();
        el.sendKeys(value);
    }

    public void setPropertyTaxes(String value) {
        WebElement el = driver.findElement(By.id("propertytaxes"));
        el.clear();
        el.sendKeys(value);
    }

    public void setHomeInsurance(String value) {
        WebElement el = driver.findElement(By.id("homeinsurance"));
        el.clear();
        el.sendKeys(value);
    }

    public void setMaintenanceExpenses(String value) {
        WebElement el = driver.findElement(By.id("maintenanceexpenses"));
        el.clear();
        el.sendKeys(value);
    }
    public void moveToYearElement() {
        WebElement ct = driver.findElement(
                By.xpath("//td[contains(@class,'paymentyear') and contains(@class,'toggle')]")
        );

        Actions actions = new Actions(driver);
        actions.moveToElement(ct).click().perform();  // moves mouse to the element
    }

//public void clickOnYear() {
//    WebElement ct = driver.findElement(
//            By.xpath("//td[contains(@class,'paymentyear') and contains(@class,'toggle')]")
//    );
//
//    JavascriptExecutor js = (JavascriptExecutor) driver;
//    js.executeScript("arguments[0].click();", ct);
//}
    public void EnterYear()throws IOException{


       int excelRow=0 ;
        int column=0;

        List<WebElement> year =  driver.findElements(By.xpath("//tr[@class='row no-margin yearlypaymentdetails']//td"));
        for(WebElement s: year){
            String filepath = System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\HomeLoan_TestData.xlsx";

            s.getText();
            ExcelUtils.setCellData(filepath, "HomeLoanTestCase(output)", excelRow+1, column, s.getText());
            column++;
        }
    }

    public void getYearlyPaymentDetailsText() throws IOException {

        // Outer rows from webpage (unchanged)
        List<WebElement> rows = driver.findElements(
                By.xpath("//tr[@class='row no-margin']")
        );

        int excelRow = 0;              // Excel row to write into
        final int COLS_PER_ROW = 7;    // ⬅️  you now want 7 columns per row
        String filepath = System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\HomeLoan_TestData.xlsx";

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
                    String val = d.getText().replace("₹","");
                // Write ONLY the 7 cells that belong to this excelRow
                if (cellIdx >= rowStart && cellIdx < rowEndExclusive) {

                    ExcelUtils.setCellData(filepath, "HomeLoanTestCase(output)", excelRow+2, column, val);
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


