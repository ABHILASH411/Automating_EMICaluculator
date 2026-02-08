package utilites;

import TestBase.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestBase.BaseClass;
    public class ExtentReportManager implements ITestListener {
        public ExtentSparkReporter sparkReporter;
        public ExtentReports extent;
        public ExtentTest test;
        public Logger logger =  LogManager.getLogger(ExtentReportManager.class);
        String repName;

        public void onStart(ITestContext testContext) {
       /*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
       Date dt=new Date();
       String currentdatetimestamp=df.format(dt);
       */

            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
            repName = "Test-Report-" + timeStamp + ".html";
            sparkReporter = new ExtentSparkReporter(".\\Reports\\" + repName);// specify location of the report

            sparkReporter.config().setDocumentTitle("EMI Calculator Automation Report"); // Title of report
            sparkReporter.config().setReportName("EMI Calculator Testing"); // name of the report
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setOfflineMode(true);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "EMI Calculator");
            extent.setSystemInfo("Module", "CarTab");
            extent.setSystemInfo("Sub Module", "CarTab");
            extent.setSystemInfo("User Name", System.getProperty("user.name"));
            extent.setSystemInfo("Environemnt", "QA");

            String os = testContext.getCurrentXmlTest().getParameter("os");
            extent.setSystemInfo("Operating System", os);

            String browser = testContext.getCurrentXmlTest().getParameter("browser");
            extent.setSystemInfo("Browser", browser);

            List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
            if(!includedGroups.isEmpty()) {
                extent.setSystemInfo("Groups", includedGroups.toString());
            }
        }

        public void onTestSuccess(ITestResult result) {
            String methodName = result.getMethod().getMethodName();
            logger.info("Test method passed with: {}", methodName);

            test = extent.createTest(result.getTestClass().getName());
            test.assignCategory(result.getMethod().getGroups()); // to display groups in report
            test.log(Status.PASS,result.getName()+" got successfully executed");

        }

        public void onTestFailure(ITestResult result) {
            String methodName = result.getMethod().getMethodName();
            logger.error("Test method failed: {}", methodName);

            test = extent.createTest(result.getTestClass().getName());
            test.assignCategory(result.getMethod().getGroups());

            test.log(Status.FAIL,result.getName()+" got failed");
            test.log(Status.INFO, result.getThrowable().getMessage());

            try {
                String imgPath = new BaseClass().captureScreen(result.getName());
                test.addScreenCaptureFromPath(imgPath);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        public void onTestSkipped(ITestResult result) {
            String methodName = result.getMethod().getMethodName();
            logger.info("Test method skipped: {}", methodName);
            test = extent.createTest(result.getTestClass().getName());
            test.assignCategory(result.getMethod().getGroups());
            test.log(Status.SKIP, result.getName()+" got skipped");
            test.log(Status.INFO, result.getThrowable().getMessage());
        }

        public void onFinish(ITestContext testContext) {

            extent.flush();

            String pathOfExtentReport = System.getProperty("user.dir")+"\\Reports\\"+repName;
            File extentReport = new File(pathOfExtentReport);

            try {
                Desktop.getDesktop().browse(extentReport.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }


       /*  try {
            URL url = new  URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);

         // Create the email message
         ImageHtmlEmail email = new ImageHtmlEmail();
         email.setDataSourceResolver(new DataSourceUrlResolver(url));
         email.setHostName("smtp.googlemail.com");
         email.setSmtpPort(465);
         email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com","password"));
         email.setSSLOnConnect(true);
         email.setFrom("pavanoltraining@gmail.com"); //Sender
         email.setSubject("Test Results");
         email.setMsg("Please find Attached Report....");
         email.addTo("pavankumar.busyqa@gmail.com"); //Receiver
         email.attach(url, "extent report", "please check report...");
         email.send(); // send the email
         }
         catch(Exception e)
         {
            e.printStackTrace();
            }
        */

        }

    }


