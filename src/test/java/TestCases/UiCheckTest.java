
package TestCases;

import TestBase.BaseClass;
import pageObject.UICheck;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UiCheckTest extends BaseClass {

    @Test
    public void verifyEmiCalculatorFlow() throws Exception {
        UICheck ui = new UICheck(driver);

        ui.openLoanCalculatorFromMenu();

        // EMI Calculator tab
        ui.openEmiCalculatorTab();

        logger.info("Case 1: Loan Amount Slider visible? " + ui.isLoanAmountSliderVisible());
        logger.info("Case 2: Interest Rate Textbox enabled? " + ui.isInterestRateTextboxEnabled());
        logger.info("Case 3: EMI Result visible? " + ui.isEmiResultVisible()
                + " (Value: " + ui.getEmiResultValue() + ")");

        ui.dragLoanAmountSliderBy(150, 0);
        String amount = ui.getLoanAmountFieldValue();
        logger.info("The Loan Amount is: " + amount);
        if (amount.equalsIgnoreCase("1,46,00,000")) {
            logger.info("amount is same");
        } else {
            logger.info("Amount is not same");
        }

        logger.info(ui.tenderSliderCheck());
        logger.info(ui.InterestRateInputValueCheck());
        logger.info(ui.EMIScheme());
        ui.noOfSliderandtextbox();
        logger.info(ui.totalpayment());
        logger.info(ui.feesinputandslideramountcheck());
        logger.info("Chart loaded? " + ui.isChartLoaded());

        // Loan Amount Calculator tab
        ui.openLoanAmountCalculatorTab();
        logger.info("Case 1: Loan Interest Slider visible? " + ui.isLoanInterestSliderVisible_InLoanAmountCalcTab());
        logger.info("Case 2: EMI BOX Textbox enabled? " + ui.isLoanEMIInputEnabled_InLoanAmountCalcTab());

        logger.info(ui.tenderSliderCheck());
        logger.info(ui.InterestRateInputValueCheck());
        logger.info(ui.EMIScheme());
        ui.noOfSliderandtextbox();
        logger.info(ui.totalpayment());
        logger.info(ui.feesinputandslideramountcheck());
        logger.info("Chart loaded? " + ui.isChartLoaded());

        // Loan Tenure Calculator tab
        ui.openLoanTenureCalculatorTab();
        logger.info("Case 1: Loan Amount Slider visible? " + ui.isLoanAmountSliderVisible());
        logger.info(ui.tenderSliderCheck());
        logger.info(ui.InterestRateInputValueCheck());
        logger.info(ui.EMIScheme());
        ui.noOfSliderandtextbox();
        logger.info(ui.totalpayment());
        logger.info(ui.feesinputandslideramountcheck());
        logger.info("Chart loaded? " + ui.isChartLoaded());

        // Soft sanity asserts (optional, keep minimal)
        Assert.assertTrue(ui.isLoanAmountSliderVisible(), "Loan Amount Slider should be visible on Tenure tab.");
        Assert.assertTrue(ui.isLoanEMIInputEnabled_InLoanAmountCalcTab(), "EMI input should be enabled on Loan Amount tab.");
    }
}
