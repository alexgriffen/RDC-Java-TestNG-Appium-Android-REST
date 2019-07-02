package com.saucelabs.example;

import com.saucelabs.example.util.ResultReporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testobject.appium.junit.TestObjectAppiumSuite;
import org.testobject.appium.junit.TestObjectAppiumSuiteWatcher;
import org.testobject.appium.junit.TestObjectTestResultWatcher;
import org.testobject.rest.api.appium.common.TestObject;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(TestObjectAppiumSuite.class)
/**
 * Created by grago on 28/02/2017.
 */
public class TestSetup {

    private AppiumDriver driver;

    private ResultReporter reporter;

    @BeforeMethod
    @Parameters({ "device" })
    public void setUp(String device) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("testobject_api_key", "D7348099E5FC44458E807282AF87FDB4"); //0462524C86944DE39F74A680E1470099
        capabilities.setCapability("testobject_device", device);
        // capabilities.setCapability("platformVersion", "8");
        capabilities.setCapability("frameworkVersion","1.13.0");
        // capabilities.setCapability("testobject_suite_name", "allTheTests");
//        capabilities.setCapability("testobject_test_report_id", resultWatcher.getTestReportId());
        // capabilities.setCapability("testobject_test_name", "twoPlusTwoOperation_alex");
        // capabilities.setCapability("recordDeviceVitals", true);
        // capabilities.setCapability("automationName", "uiautomator2");
        capabilities.setCapability("networkSpeed", "gsm");
        // capabilities.setCapability("autoAcceptAlerts", true);

        driver = new AndroidDriver(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);
        reporter = new ResultReporter();
        // System.out.println("Desired capabilities from TestObject/RDC: " + driver.getCapabilities().asMap());

    }

    /* A simple addition, it expects the correct result to appear in the result field. */
    @Test
    public void twoPlusTwoOperation() throws MalformedURLException {

        driver.getScreenshotAs(OutputType.BASE64);
        // driver.findElement(By.id("OK")).click();
        // driver.switchTo().alert().accept
        // String cap = driver.getCapabilities().getCapability("testobject_device").toString();
        // System.out.println("*$&$*$&$*$&$*$&");
        // System.out.println("Current Device: " + cap);
        //
        //
        // if(driver.getAlertText() != null){
        //   driver.switchTo().alert().accept(); //this works, though need better if statement
        //   System.out.println("made it here");
        // };

        /* Get the elements. */
        MobileElement buttonTwo = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/digit2")));
        MobileElement buttonPlus = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/plus")));
        MobileElement buttonEquals = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/equal")));
        MobileElement resultField = (MobileElement)(driver.findElement(By.xpath("//android.widget.EditText[1]")));
        driver.getScreenshotAs(OutputType.BASE64);

        /* Add two and two. */
        buttonTwo.click();
        driver.getScreenshotAs(OutputType.BASE64);
        buttonPlus.click();
        buttonTwo.click();
        driver.getScreenshotAs(OutputType.BASE64);
        buttonEquals.click();
        driver.getScreenshotAs(OutputType.BASE64);

        /* Check if within given time the correct result appears in the designated field. */
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBePresentInElement(resultField, "4"));

    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        boolean success = result.isSuccess();
        String sessionId = driver.getSessionId().toString();
        // System.out.println("sessionId is: " + sessionId);

        reporter.saveTestStatus(sessionId, success);
        driver.quit();
    }

}
