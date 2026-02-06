
package pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import utilites.WaitUtility;

import java.util.ArrayList;
import java.util.List;

public class UICheck extends BasePage {
    WaitUtility wait;

    public UICheck(WebDriver driver) {
        super(driver);
        this.wait = new WaitUtility(driver);
    }

    @FindBy(xpath = "//a[text()='Loan Calculators & Widgets']")
    WebElement menuList;

    @FindBy(xpath = "//a[text()='Loan Calculator']")
    WebElement linkLoanCalculator;

    // Tabs
    @FindBy(css = "li#emi-calc")
    WebElement tabEmiCalculator;

    @FindBy(css = "li#loan-amount-calc")
    WebElement tabLoanAmountCalculator;

    @FindBy(css = "li#loan-tenure-calc")
    WebElement tabLoanTenureCalculator;


    @FindBy(css = "#loanamount")
    WebElement inputLoanAmountbox;

    @FindBy(css = "#loanamount")
    WebElement inputLoanAmount;



    @FindBy(css = "rect.highcharts-background")
    WebElement chartBackground;

    // Tenure toggles and term value
    @FindBy(css = "#loanyears")
    WebElement toggleYears;

    @FindBy(css = "label.btn.btn-secondary input#loanmonths")
    WebElement toggleMonths;

    @FindBy(css = "#loanterm")
    WebElement inputLoanTerm;

    // EMI Scheme
    @FindBy(css = "label.btn.btn-secondary input#emiadvance")
    WebElement radioEmiAdvance;

    // Sliders and textboxes collections
    @FindBy(xpath = "//div[@id='loancalculatorinnerform']/div[contains(@class,'toggle-visible')]//div[contains(@class,'ui-slider') and contains(@id,'slider')]")
    List<WebElement> listSliders;

    @FindBy(xpath = "//div[@id='loancalculatorinnerform']/div[contains(@class,'toggle-visible')]//input[@type='text']//ancestor::div[contains(@class,'toggle-visible')]//label[contains(@class,'control-label')]")
    List<WebElement> listTextboxes;

    // Summary - Total Payment
    @FindBy(xpath = "//div[@id='loansummary-totalamount']")
    WebElement summaryTotalPaymentP;

    @FindBy(xpath = "//div[@id='loansummary-totalinterest']")
    WebElement Total_Interest_Payable;

    @FindBy(xpath = "//div[@id='loansummary-apr']")
    WebElement Loan_APR;



    @FindBy(css = "#loanfees")
    WebElement inputLoanFees;

    // Loan Amount Calculator (specific checks)
    @FindBy(css = "#loaninterestslider")
    WebElement sliderLoanInterest;

    @FindBy(css = "#loaninterest")
    WebElement inputInterestRate;


    public boolean isMenuDisplayed() {
        try{
            // 1. Check if the Main Menu is present and visible
            wait.waitForVisible(menuList);
            menuList.click();
            return true;
        }catch (Exception e){
            logger.error("Menu list is not displayed due to -> {}",e.toString());
            return false;
        }
    }


    public void clickLoanCalculator(){
        // 2. Check if the Sub-link is present and clickable after the menu opens
        wait.waitForClickable(linkLoanCalculator);
        linkLoanCalculator.click();

    }



    public boolean isEmiCalculatorTabDisplayed() {
        try{
            wait.waitForVisible(tabEmiCalculator);

            return true;
        }catch (Exception e){
            logger.error("EMI Calculator is not visible due to -> {}",e.toString());
            return false;
        }
    }


    public boolean isLoanAmountCalculatorDisplayed() {
        try{
            wait.waitForVisible(tabLoanAmountCalculator);
            tabLoanAmountCalculator.click();
            return true;
        }catch (Exception e){
            logger.error("Loan amount Calculator is not visible due to -> {}",e.toString());
            return false;
        }
    }

    public boolean isLoanTenureTabDisplayed() {
        try{
            wait.waitForVisible(tabLoanTenureCalculator);
            tabLoanTenureCalculator.click();
            return true;
        }catch (Exception e){
            logger.error("LOAN TENURE Calculator is not visible due to -> {}",e.toString());
            return false;
        }
    }

    public boolean isEnterLoanAmount(String amount) {
        // wait for the element to be visible

        wait.waitForVisible(inputLoanAmountbox);

        // Clear the existing value (10,00,000)
        // Correct JavaScript way to clear an input
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", inputLoanAmountbox);

        // Enter the new value
        inputLoanAmount.sendKeys(amount);
        String enteramount = inputLoanAmount.getAttribute("value");
        if(enteramount.equalsIgnoreCase(amount)){
            return true;
        }else
            return false;
    }

    public boolean isFeesCharges(String amount) {
        // wait for the element to be visible

        wait.waitForVisible(inputLoanFees);

        // Clear the existing value (10,00,000)
        // Correct JavaScript way to clear an input
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", inputLoanFees);

        // Enter the new value
        inputLoanFees.sendKeys(amount);
        String enteramount = inputLoanFees.getAttribute("value");
        if(enteramount.equalsIgnoreCase(amount)){
            return true;
        }else
            return false;
    }


    public boolean isChartLoaded() {
        try {
            return chartBackground.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isLoanInterestSliderMoving(int x,int y){
        try {
            String startingAmount = inputInterestRate.getAttribute("value");

            new Actions(driver).dragAndDropBy(sliderLoanInterest, x, y).build().perform();

            String updatedAmount = inputInterestRate.getAttribute("value");

            return !startingAmount.equalsIgnoreCase(updatedAmount);

        }catch (Exception e){
            logger.error("The slider doesn't change the amount -{}",e.toString());

            return false;
        }
    }

    public boolean isTenureConversionSuccessful() {

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toggleYears);

        int years = Integer.parseInt(inputLoanTerm.getAttribute("value").replaceAll("[^0-9]", ""));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toggleMonths);

        int months = Integer.parseInt(inputLoanTerm.getAttribute("value").replaceAll("[^0-9]", ""));

        return (months == years * 12);
    }

    public boolean isEmiSchemeAdvanceClickable() {
        try {
            return radioEmiAdvance.isDisplayed() && radioEmiAdvance.isEnabled();
        } catch (NoSuchElementException e) {
            logger.error("EMI Scheme Advance radio button not found in DOM");
            return false;
        }
    }

    public boolean areAllSummaryElementsVisible() {
        try {
            return summaryTotalPaymentP.isDisplayed() &&
                    Total_Interest_Payable.isDisplayed() &&
                    Loan_APR.isDisplayed() ;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }






    public boolean validateVisibleSliders() {

        boolean flag=true;
        for (WebElement s : listSliders) {
            if (s.isDisplayed()) {
                flag=true;
            }else{
                flag=false;
            }
        }
        if(flag){
            return true;
        }else {
            return false;
        }

    }


    public boolean validateVisibleTestBoxs() {
        List<WebElement> visibleSliders = new ArrayList<>();
        int c=listTextboxes.size();
        int count=0;

        for (WebElement s : listTextboxes) {
            if (s.isDisplayed()) {
                System.out.println(s.getText());
                count++;
            }else{
                return false;
            }
        }
        if(count==4){
            return true;
        }else {
            return false;
        }
    }

}
