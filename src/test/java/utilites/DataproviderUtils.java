package utilites;

import org.testng.annotations.DataProvider;
import java.io.IOException;
public class DataproviderUtils {

    @DataProvider(name = "CarData")
    public String[][] individualFieldsData() throws IOException {
        String filepath = "C:\\Users\\2457327\\Downloads\\EMICalculator\\src\\test\\resources\\TestData\\EmiData.xlsx";
        return ExcelUtils.getSheetDataAsString(filepath,"Sheet1",false);
    }
    @DataProvider(name = "HomeLoan")
    public String[][] HomeLoanTestData() throws IOException {
        String filepath = "C:\\Users\\2457327\\Downloads\\EMICalculator\\src\\test\\resources\\TestData\\HomeLoan_TestData.xlsx";
        return ExcelUtils.getSheetDataAsString(filepath, "HomeLoanTestCase(Input)", false);
    }


}
 