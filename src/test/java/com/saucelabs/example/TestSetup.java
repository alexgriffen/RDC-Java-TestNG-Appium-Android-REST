package com.saucelabs.example;

import com.saucelabs.example.util.ResultReporter;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
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

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by grago on 28/02/2017.
 */
public class TestSetup {

    private AndroidDriver driver;
    private ResultReporter reporter;

    @BeforeMethod
    @Parameters({ "device" })
    public void setUp(String device) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("testobject_api_key", System.getenv("TESTOBJECT_API_KEY"));
        capabilities.setCapability("testobject_device", device);
        capabilities.setCapability("testobject_suite_name", "allTheTests");
        capabilities.setCapability("testobject_test_name", "twoPlusTwoOperation_alex");

        driver = new AndroidDriver(new URL(System.getenv("APPIUM_URL")), capabilities);
        reporter = new ResultReporter();

    }

    /* A simple addition, it expects the correct result to appear in the result field. */
    @Test
    public void twoPlusTwoOperation() throws MalformedURLException {


        driver.getScreenshotAs(OutputType.BASE64);
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

        reporter.saveTestStatus(sessionId, success);
        driver.quit();
    }

}
