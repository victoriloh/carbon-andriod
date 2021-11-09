package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.testinium.deviceinformation.helper.ProcessHelper;
import enums.TargetTypeEnum;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestBase {

    public static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    public static ExtentReports reports;
    public static ExtentHtmlReporter htmlReporter;
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();

    public static AndroidDriver getDriver() {
        return driver.get();
    }

    String devices;
    static String[] udid;
    public static String Id = "";

    @Parameters({"groupReport", "testEnv"})
    @BeforeSuite
    public void setUp(String groupReport, String testEnv) throws IOException, ParseException {

        htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir") + groupReport));
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);


    }

        @BeforeMethod(description = "fetch test cases name")
    public void register(Method method) {

        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        ExtentTest child = parentTest.get().createNode(method.getName());
        testInfo.set(child);
    }
    @AfterClass
    public void closeApp() {
        getDriver().quit();
    }

    @BeforeClass
    public void startApp() throws IOException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("deviceName", "R9DN602NB3J");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.lenddo.mobile.paylater.staging");
        capabilities.setCapability("appActivity", "com.lenddo.mobile.paylater.home.activity.SplashScreenActivity");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);

        driver.set(new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities));
        System.out.println("++++++++++UIAUTOMATOR 2 DRIVER INSTANCE RUNNING++++++++++++");


        ExtentTest parent = reports.createTest(getClass().getName());
        parentTest.set(parent);




    }

    public static String addScreenshot() {

        TakesScreenshot ts = getDriver();
        File scrFile = ts.getScreenshotAs(OutputType.FILE);

        String encodedBase64 = null;
        FileInputStream fileInputStreamReader;
        try {
            fileInputStreamReader = new FileInputStream(scrFile);
            byte[] bytes = new byte[(int) scrFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "data:image/png;base64," + encodedBase64;
    }

    @AfterMethod(description = "to display the result after each test method")
    public void captureStatus(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = addScreenshot();
            testInfo.get().addScreenCaptureFromBase64String(screenshotPath);
            testInfo.get().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP)
            testInfo.get().skip(result.getThrowable());
        else
            testInfo.get().pass(result.getName() + " Test passed");

        reports.flush();
    }
    @Parameters({"testEnv"})
    @Test

    public void Login(String testEnv) throws InterruptedException, IOException, ParseException, NullPointerException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 90);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/staging.json"));
        JSONObject envs = (JSONObject) config.get("SignIn");
        String valid_phone = (String) envs.get("valid_phone");
        String valid_pin = (String) envs.get("valid_pin");
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_pin_placeholder1"))));
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_pin_placeholder1")).click();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_pin_placeholder1")).click();
        Thread.sleep(5000);
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_pin_placeholder1")).sendKeys("1");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_pin_placeholder2")).sendKeys("2");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_pin_placeholder3")).sendKeys("3");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_pin_placeholder4")).sendKeys("4");

    }
    }

