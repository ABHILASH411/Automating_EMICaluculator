package TestCases;
import org.openqa.selenium.WebDriver;


import org.testng.annotations.*;
import TestBase.BaseClass;
import pageObject.HomeLoan;
import utilites.DataproviderUtils;
import org.testng.annotations.Test;

public class HomeLoanTest extends BaseClass {


    HomeLoan page ;
    @Test(description = "Fill Home Loan page with fixed inputs",
            dataProvider ="HomeLoan", dataProviderClass = DataproviderUtils.class)
    public void fillHomeLoanForm(String s ,String margin ,String Loan_Insurance,
                                 String LoanAmount ,String InterestRate, String LoanTenure,
                                 String LoanFees, String Year, String Month,
                                 String one, String Property, String Insurance,
                                 String Expenses) throws Exception {
        page= new HomeLoan(driver);
          // use driver from BaseClass
        logger.info("**Started TestCase_001: Home Loan Form Filling**");

        logger.info("Clicking Home Loan tab");
        page.clickHomeLoanTab();

        logger.info("Entering Home Price: " + s);
        page.setHomePrice(s);

        logger.info("Entering Down Payment Percent: " + margin);
        page.setDownPaymentPercent(margin);

        logger.info("Entering Loan Insurance: " + Loan_Insurance);
        page.setLoanInsurance(Loan_Insurance);

        logger.info("Entering Loan Amount: " + LoanAmount);
        page.setLoanAmount(LoanAmount);

        logger.info("Clearing and setting Interest Rate: " + InterestRate);
        page.clearInterest(InterestRate);

        logger.info("Entering Loan Tenure: " + LoanTenure);
        page.homeloanterm1(LoanTenure);

        logger.info("Entering Loan Fees: " + LoanFees);
        page.setLoanFees(LoanFees);

        logger.info("Opening Start Month/Year Picker");
        page.openStartMonthYearPicker();

        logger.info("Picking Year: " + Year + " and Month: " + Month);
        page.pickYearThenMonth(Year, Month);

        logger.info("Entering One-Time Expenses: " + one);
        page.setOneTimeExpenses(one);

        logger.info("Entering Property Taxes: " + Property);
        page.setPropertyTaxes(Property);

        logger.info("Entering Home Insurance: " + Insurance);
        page.setHomeInsurance(Insurance);

        logger.info("Entering Maintenance Expenses: " + Expenses);
        page.setMaintenanceExpenses(Expenses);

        logger.info("Waiting for Year element to be clickable");
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//td[contains(@class,'paymentyear') and contains(@class,'toggle')]")
//        ));

        Thread.sleep(3000);

        logger.info("Moving to Year element");
        page.moveToYearElement();

        page.moveToNextYear();

        page.EnterYear();

        logger.info("Fetching yearly payment details text");
        page.getYearlyPaymentDetailsText();

        logger.info("**Completed TestCase_001 successfully**");
    }
}
