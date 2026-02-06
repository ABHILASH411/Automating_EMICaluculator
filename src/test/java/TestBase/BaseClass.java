package TestBase;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {
    public WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass()
    @Parameters({"browser", "os"})
    public void setup(@Optional("Chrome") String br, @Optional("Windows") String os) throws IOException {
        // Load properties
        FileReader file = new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());
        String executionEnv = p.getProperty("execution_env").trim().toLowerCase();

        if (executionEnv.equals("remote")) {

            DesiredCapabilities cap = new DesiredCapabilities();

            // Platform mapping
            if (os.equalsIgnoreCase("Windows")) cap.setPlatform(Platform.WIN11);
            else if (os.equalsIgnoreCase("Linux")) cap.setPlatform(Platform.LINUX);
            else if (os.equalsIgnoreCase("Mac")) cap.setPlatform(Platform.MAC);
            else cap.setPlatform(Platform.ANY);

            // Browser mapping
            switch (br.toLowerCase()) {
                case "chrome":
                    cap.setBrowserName("chrome");

                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--disable-notifications");
                    cap.setCapability(ChromeOptions.CAPABILITY, options);
                    break;

                case "edge":
                    cap.setBrowserName("MicrosoftEdge");
                    break;

                default:
                    throw new IllegalArgumentException("Invalid browser name: " + br);
            }

            // Read grid url from config if available, else default
            String hubUrl = p.getProperty("gridURL");
            driver = new RemoteWebDriver(new URL(hubUrl), cap);

        } else {
            //LOCAL execution (initialize only once)
            switch (br.toLowerCase()) {
                case "chrome":
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--disable-notifications");
                    driver = new ChromeDriver(options);
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser name: " + br);
            }
        }

        // Driver setup
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(p.getProperty("appURL"));

    }

    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath=System.getProperty("user.dir")+"\\Screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile=new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }



}
 