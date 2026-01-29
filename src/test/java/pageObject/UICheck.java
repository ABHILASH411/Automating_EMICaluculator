
package pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class UICheck extends BaseClass {

    public UICheck(WebDriver driver) {
        super(driver);
    }

    // ------------------------------------------------------------
    // LOCATORS (kept EXACTLY as in your original code, no changes)
    // ------------------------------------------------------------

    // Top Menu / Navigation
    private final By menu_LoanCalculatorsAndWidgets = By.xpath("//a[text()='Loan Calculators & Widgets']");
    private final By link_LoanCalculator = By.xpath("//a[text()='Loan Calculator']");

    // Tabs
    private final By tab_EmiCalculator = By.xpath("//li[@id='emi-calc']");
    private final By tab_LoanAmountCalculator = By.xpath("//li[@id='loan-amount-calc']");
    private final By tab_LoanTenureCalculator = By.xpath("//li[@id='loan-tenure-calc']");

    // Common/EMI Calculator Elements
    private final By slider_LoanAmount = By.id("loanamountslider");
    private final By input_LoanAmount = By.id("loanamount");
    private final By input_InterestRate = By.id("loaninterest");
    private final By emiResult_Value = By.xpath("//div[@id='loansummary-emi']/p/span");
    private final By chart_Background = By.cssSelector("rect.highcharts-background");

    // Tenure toggles and term value
    private final By toggle_Years = By.xpath("//input[@id='loanyears']");
    private final By toggle_Months = By.xpath("//label[@class='btn btn-secondary']/input[@id='loanmonths']");
    private final By input_LoanTerm = By.xpath("//input[@id='loanterm']");

    // EMI Scheme
    private final By radio_EmiAdvance = By.xpath("//label[@class='btn btn-secondary']/input[@id='emiadvance']");

    // Sliders and textboxes collections
    private final By list_Sliders = By.xpath("//span[@tabindex='0']/parent::div");
    private final By list_Textboxes = By.xpath("//input[@type='text']");

    // Summary - Total Payment
    private final By summary_TotalPaymentP = By.xpath("//div[@id='loansummary-totalamount']/h4/following::p");

    // Fees controls
    private final By slider_LoanFeesHandle = By.xpath("//div[@id='loanfeesslider']/span");
    private final By input_LoanFees = By.id("loanfees");

    // Loan Amount Calculator (specific checks)
    private final By slider_LoanInterest = By.id("loaninterestslider");
    private final By input_LoanEMI = By.id("loanemi");


    // ------------------------------------------------------------
    // ACTIONS / FLOWS (Using existing locators and your original logic)
    // ------------------------------------------------------------

    public void openLoanCalculatorFromMenu() {
        driver.findElement(menu_LoanCalculatorsAndWidgets).click();
        driver.findElement(link_LoanCalculator).click();
    }

    public void openEmiCalculatorTab() {
        driver.findElement(tab_EmiCalculator).click();
    }

    public void openLoanAmountCalculatorTab() {
        driver.findElement(tab_LoanAmountCalculator).click();
    }

    public void openLoanTenureCalculatorTab() {
        driver.findElement(tab_LoanTenureCalculator).click();
    }

    public boolean isLoanAmountSliderVisible() {
        return driver.findElement(slider_LoanAmount).isDisplayed();
    }

    public boolean isInterestRateTextboxEnabled() {
        return driver.findElement(input_InterestRate).isEnabled();
    }

    public boolean isEmiResultVisible() {
        return driver.findElement(emiResult_Value).isDisplayed();
    }

    public String getEmiResultValue() {
        return driver.findElement(emiResult_Value).getText();
    }

    public void dragLoanAmountSliderBy(int xOffset, int yOffset) {
        WebElement slider = driver.findElement(slider_LoanAmount);
        new Actions(driver).dragAndDropBy(slider, xOffset, yOffset).build().perform();
    }

    public String getLoanAmountFieldValue() {
        return driver.findElement(input_LoanAmount).getAttribute("value");
    }

    public String tenderSliderCheck() {
        //--checking the year and month changes in the tender slide--//
        WebElement yearButton = driver.findElement(toggle_Years);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yearButton);
        WebElement tendervalue = driver.findElement(input_LoanTerm);
        String yearvalue = tendervalue.getAttribute("value");

        WebElement monthButton = driver.findElement(toggle_Months);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", monthButton);

        WebElement tendervalueyear = driver.findElement(input_LoanTerm);
        // Keeping your original line exactly (likely a typo)
        String monthvalue = tendervalue.getAttribute("value");

        System.out.println(yearvalue + " " + monthvalue);

        String cleanMonth = monthvalue.replaceAll("[^0-9]", "");
        String cleanYear = yearvalue.replaceAll("[^0-9]", "");
        int m = Integer.parseInt(cleanMonth);
        int y = Integer.parseInt(cleanYear);

        if (m == 60 && y == 5)
            return "Success: Values match!";
        else
            return "Error: Values are not correc";
    }

    public String InterestRateInputValueCheck() {
        WebElement interestTestBox = driver.findElement(input_InterestRate);
        interestTestBox.clear();
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='5';", interestTestBox);
        String amount = interestTestBox.getAttribute("value");
        if (amount.equalsIgnoreCase("5")) {
            return "Success: Both the input and displayed value are same";
        } else {
            return "failure : Both are not same";
        }
    }

    public boolean isChartLoaded() {
        try {
            WebElement chartBg = driver.findElement(chart_Background);
            return chartBg.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String EMIScheme() {
        WebElement element = driver.findElement(radio_EmiAdvance);
        if (element.isDisplayed() && element.isEnabled()) {
            return " EMI Schenme advance is likely clickable.";
        } else {
            return "EMI Schenme adavance  is either hidden or disabled.";
        }
    }

    public void noOfSliderandtextbox() {
        List<WebElement> slides = driver.findElements(list_Sliders);
        System.out.println("no of slider :" + slides.size());
        for (WebElement s : slides) {
            System.out.println("slider name :" + s.getAttribute("id"));
        }
        List<WebElement> textBoxs1 = driver.findElements(list_Textboxes);
        for (WebElement w : textBoxs1) {
            System.out.println(w.getAttribute("name"));
        }
    }

    public String totalpayment() {
        WebElement amountLabel = driver.findElement(summary_TotalPaymentP);
        if (amountLabel.isDisplayed()) {
            return "Amount is visible: " + amountLabel.getText();
        } else {
            return "Amount is in the code but hidden from the user.";
        }
    }

    public String feesinputandslideramountcheck() {
        WebElement feesslider = driver.findElement(slider_LoanFeesHandle);
        WebElement loanFeesInput = driver.findElement(input_LoanFees);
        loanFeesInput.click();
        new Actions(driver).dragAndDropBy(feesslider, 150, 0).build().perform();
        String amount1 = loanFeesInput.getAttribute("value");
        System.out.println("The Loan Amount is: " + amount1);
        if (amount1.equalsIgnoreCase("33,500")) {
            return "amount is same in both slider and input";
        } else {
            return "Amount is not same in slider and input";
        }
    }

    public boolean isLoanInterestSliderVisible_InLoanAmountCalcTab() {
        return driver.findElement(slider_LoanInterest).isDisplayed();
    }

    public boolean isLoanEMIInputEnabled_InLoanAmountCalcTab() {
        return driver.findElement(input_LoanEMI).isEnabled();
    }
}
