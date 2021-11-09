package admin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.TestBase;

import java.io.FileReader;
import java.io.IOException;

public class SignIn extends TestBase {

    @Parameters({"testEnv"})
    @Test

    public void phoneNumberSignIn(String testEnv) throws InterruptedException, IOException, ParseException, NullPointerException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 90);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/staging.json"));
        JSONObject envs = (JSONObject) config.get("SignIn");
        String valid_phone = (String) envs.get("valid_phone");
        String valid_pin = (String) envs.get("valid_pin");
        String verification_code = (String) envs.get("verification_code");
        String valid_phone2 = (String) envs.get("valid_phone2");
        String invalid_phone = (String) envs.get("invalid_phone");
        //Login with invalid phoneNumber
        System.out.println("Login with an invalid username " + "(" + invalid_phone + ")" + " and invalid password " + "(" + valid_pin + ")");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/animation_view")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/user_type_existing")));
        if (getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/user_type_existing")).isDisplayed()) {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("com.lenddo.mobile.paylater.staging:id/user_type_existing")));
            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/user_type_existing")).click();
            //Enter Invalid details
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_phone")));

            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_phone")).clear();
            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_phone")).sendKeys(invalid_phone);
            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_pin")).clear();
            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_pin")).sendKeys(valid_pin);
            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_next")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/alertTitle")));

//        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/alertTitle")), "Error");
//        Assert.assertEquals(getDriver().findElement(By.id("android:id/message")), "Unable to log user in");
            getDriver().findElement(By.id("android:id/button1")).click();

            //Enter Valid details
            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_phone")).clear();
            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_phone")).sendKeys(valid_phone);
            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_pin")).clear();
            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_pin")).sendKeys(valid_pin);
            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_next")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/sign_up_verify_info_text")));
            getDriver().findElement(By.xpath("//android.widget.FrameLayout[@index='0']")).click();
            getDriver().findElement(By.xpath("//android.widget.FrameLayout[@index='0']")).clear();
            getDriver().findElement(By.xpath("//android.widget.FrameLayout[@index='0']")).sendKeys(verification_code);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging.carboncards:id/debitCardHeaderText")));
            getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging.carboncards:id/btnNotRightNow")).click();
            Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/title_text")), "Home");
            Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/fontTextView")), "Need cash instantly?");
        }
    }

    @Parameters({"testEnv"})
    @Test
    public void purchaseAirtime(String testEnv) throws InterruptedException, IOException, ParseException, NullPointerException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 90);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/staging.json"));
        JSONObject envs = (JSONObject) config.get("SignIn");
        String valid_phone2 = (String) envs.get("valid_phone2");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/category_label")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/text_1")));
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/text_1")), "What phone number would you like to recharge?");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_text_phone_number")).clear();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_text_phone_number")).sendKeys(valid_phone2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/fontTextView16")));
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_text_airtime_price")).clear();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_text_airtime_price")).sendKeys("1000");
        getDriver().findElement(By.id("//android.widget.FrameLayout[@index='1']")).click();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/button_next")).click();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/card_number")).click();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/confirm_payment_button")).click();
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/text_view_phone_number")), "08990001101");
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/text_view_mobile_network")), "Airtel");
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/amount_view_amount")), "1000");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/button_pay")).click();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/pin_view")).click();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/pin_view")).sendKeys("1234");

    }

    @Parameters({"testEnv"})
    @Test
    public void fundWallet(String testEnv) throws InterruptedException, IOException, ParseException, NullPointerException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 90);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/staging.json"));
        JSONObject envs = (JSONObject) config.get("SignIn");
        String valid_phone2 = (String) envs.get("valid_phone2");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/fundWalletButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/text_via_bank_transfer")));
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/text_via_bank_transfer")), "Via bank transfer");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/text_fund_with_card")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/proceedWalletFunding")));
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/walletAmountToFund")).click();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/proceedWalletFunding")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/title_text")));
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/title_text")), "Choose a payment method");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/card_item")).click();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/button_confirm_payment")).click();
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/fontTextView18")), "Account number");
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/text_view_account_number")), "1352826814");
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/text_view_account_name")), "Femi Ajayi");
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/button_text_secure_pay")), "Securely pay ₦1,010");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_pin_placeholder1")).click();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_pin_placeholder1")).clear();
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/edit_pin_placeholder1")).sendKeys("1234");

    }

    @Parameters({"testEnv"})
    @Test
    public void viewWalletTransactions(String testEnv) throws InterruptedException, IOException, ParseException, NullPointerException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 90);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/staging.json"));
        JSONObject envs = (JSONObject) config.get("SignIn");
        String valid_phone2 = (String) envs.get("valid_phone2");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/viewWalletTransactionsButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/date_title")));
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/ll")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/tvName")));
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/tvName")), "Carbon Wallet");
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/tvStatus")), "SUCCESSFUL");
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/tvTransactionDetailMessage")), "Refund for airtime_topup/PAYMENT-REF 15504166444299765451430");
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/tvPromo")), "More benefits await when you use Carbon for all your financial transactions (or should we say banking needs?). Pay bills for free and earn up to 15.5% interest on savings.");
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/ivBack")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/date_title")));
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/transactionTypeSpinner")).click();
        getDriver().findElement(By.xpath("//android.widget.TextView[@index='3']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.lenddo.mobile.paylater.staging:id/date_title")));
        getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/ll")).click();
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/tvAmount")), "₦505");
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/tvStatus")), "SUCCESSFUL");
        Assert.assertEquals(getDriver().findElement(By.id("com.lenddo.mobile.paylater.staging:id/tvPromo")), "More benefits await when you use Carbon for all your financial transactions (or should we say banking needs?). Pay bills for free and earn up to 15.5% interest on savings.");


    }
}

