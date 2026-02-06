
package TestCases;

import TestBase.BaseClass;
import org.testng.annotations.*;
import pageObject.UICheck;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UiCheckTest extends BaseClass {
    UICheck ui;
    @BeforeMethod
    public void setup(){
        ui = new UICheck(driver);
    }
    @Test(priority = 1)
    public void navigate_to_home_loan_page() {

        Assert.assertTrue(
                ui.isMenuDisplayed(),
                "Assertion Failed: Menu list is NOT displayed on the Home Loan Page."
        );
        ui.clickLoanCalculator();
    }
    @Test(priority = 2,groups = {"regression"})
    public void check_EMI_calculator(){
        Assert.assertTrue(ui.isEmiCalculatorTabDisplayed(),
                "Assertion Failed: EMI calculator Tap is NOT displayed .");
        Assert.assertTrue(ui.isTenureConversionSuccessful(),"Assertion Failed: Loan  is NOT displayed .");
        Assert.assertTrue(ui.isEnterLoanAmount("300000"),"Assertion Failed :Loan Input box doesn't take Amount");
        Assert.assertTrue(ui.areAllSummaryElementsVisible(),"Assertion Failed: Final Amounts are  NOT displayed .");
        Assert.assertTrue(ui.isEmiSchemeAdvanceClickable(),"Assertion Failed: EMI Scheme is NOT working .");
        Assert.assertTrue(ui.isChartLoaded(),"Assertion Failed: Chart is NOT displayed .");
        Assert.assertTrue(ui.isLoanInterestSliderMoving(150,0),"Assertion Failed: Loan Interest slider NOT Working.");
        Assert.assertTrue(ui.validateVisibleTestBoxs(),"Assertion Failed:  Some Test Boxs Are not visible.");
        Assert.assertTrue(ui.validateVisibleSliders(),"Assertion Failed:  Some sliders Are not visible.");
    }
    @Test(priority = 3,groups = {"sanity"})
    public void check_Loan_Amount_calculator(){
        Assert.assertTrue(ui.isLoanAmountCalculatorDisplayed(),
                "Assertion Failed: Loan Amount calculator Tap is NOT displayed .");
        Assert.assertTrue(ui.isTenureConversionSuccessful(),"Assertion Failed: Loan  is NOT displayed .");
        Assert.assertTrue(ui.isFeesCharges("20000"),"Assertion Failed :Fess and charge Input box doesn't take Amount");
        Assert.assertTrue(ui.areAllSummaryElementsVisible(),"Assertion Failed: Final Amounts are  NOT displayed .");
        Assert.assertTrue(ui.isEmiSchemeAdvanceClickable(),"Assertion Failed: EMI Scheme is NOT working .");
        Assert.assertTrue(ui.isChartLoaded(),"Assertion Failed: Chart is NOT displayed .");
        Assert.assertTrue(ui.isLoanInterestSliderMoving(200,0),"Assertion Failed: Loan Interest slider NOT Working.");
        Assert.assertTrue(ui.validateVisibleTestBoxs(),"Assertion Failed:  Some Test Boxs Are not visible.");
        Assert.assertTrue(ui.validateVisibleSliders(),"Assertion Failed:  Some sliders Are not visible.");

    }

    @Test(groups = {"sanity","regression"} ,priority = 4)
    public void check_Loan_tenure_calculator(){
        Assert.assertTrue(ui.isLoanTenureTabDisplayed() ,
                "Assertion Failed: Loan Amount calculator Tap is NOT displayed .");
        Assert.assertTrue(ui.isFeesCharges("20000"),"Assertion Failed :Fess and charge Input box doesn't take Amount");
        Assert.assertTrue(ui.areAllSummaryElementsVisible(),"Assertion Failed: Final Amounts are  NOT displayed .");
        Assert.assertTrue(ui.isEmiSchemeAdvanceClickable(),"Assertion Failed: EMI Scheme is NOT working .");
        Assert.assertTrue(ui.isChartLoaded(),"Assertion Failed: Chart is NOT displayed .");
        Assert.assertTrue(ui.isLoanInterestSliderMoving(100,0),"Assertion Failed: Loan Interest slider NOT Working.");
        Assert.assertTrue(ui.validateVisibleTestBoxs(),"Assertion Failed:  Some Test Boxs Are not visible.");
        Assert.assertTrue(ui.validateVisibleSliders(),"Assertion Failed:  Some sliders Are not visible.");

    }


}
