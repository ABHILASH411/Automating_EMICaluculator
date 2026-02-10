package TestCases;

import TestBase.BaseClass;
import org.testng.annotations.Test;
import pageObject.EmiCalculatorPage;
import utilites.DataproviderUtils;

import java.io.IOException;


public class Test_EmiCalculator extends BaseClass {

    @Test(dataProvider = "CarData",
    dataProviderClass = DataproviderUtils.class)
    public void verifyCarLoanMonthlyBreakup(String amount,String rate ,String Tender,String row) throws IOException {

        logger.info("-----Stated CarEmiTestCase-----");
        EmiCalculatorPage page = new EmiCalculatorPage(driver);


        logger.info("-----opencarLoanTab-----");
        page.openCarLoanTab();

        logger.info("-----Starting data Entering-----");
        page.setLoanAmount(amount);

        logger.info("-----Entered Loan Amount-----");
        page.setInterestRate(rate);

        logger.info("-----Entered Interest Rate-----");
        page.setLoanTenureYears(Tender);

        logger.info("-----Entered Tenure-----");
        page.scrollResultsIntoView();

        logger.info("-----TotalAmount-----");

        page.TotalInterestAmount(Integer.parseInt(row));




    }
}