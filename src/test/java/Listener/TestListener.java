package Listener;

import org.testng.*;
import com.aventstack.extentreports.*;
import base.BaseTest;
import utils.ExtentManager;
import utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;

public class TestListener implements ITestListener {

    ExtentReports extent = ExtentManager.getInstance();
    ThreadLocal<ExtentTest> tlTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        tlTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        capture(result, "PASS");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        capture(result, "FAIL");
        tlTest.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        capture(result, "SKIP");
    }

    public void capture(ITestResult result, String status) {

        WebDriver driver = BaseTest.getDriver(); // ✅ correct driver

        if (driver == null) {
            System.out.println("Driver is NULL ❌");
            return;
        }

        String path = ScreenshotUtils.capture(driver, result.getName());

        tlTest.get().log(Status.valueOf(status), "Test " + status);

        try {
            tlTest.get().addScreenCaptureFromPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}