package application_ecommerce;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriverException;
import taps.InteractionsWithElements;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class ECommerceTests extends  BaseEcommerceTests {

    private final AndroidDriver<AndroidElement> androidDriver = getCapabilities();
    private final TouchAction touchAction = new TouchAction(androidDriver);

    InteractionsWithElements interactionsWithElements = new InteractionsWithElements(androidDriver, touchAction);

    @Test
    public void openingTheApp() {

    }


    @Test
    public void fillInitialFormTest() throws InterruptedException {

        androidDriver.findElementById("com.androidsample.generalstore:id/nameField").sendKeys("Your Name");
        androidDriver.findElementById("com.androidsample.generalstore:id/radioFemale").click();
        androidDriver.findElementById("com.androidsample.generalstore:id/spinnerCountry").click();
        interactionsWithElements.scrollDownOnAList("text", "Brazil");

        androidDriver.findElementByXPath("//android.widget.Button[@index='6']").click();
        //Thread.sleep(10000);

        String toastMessage;

        try {
            toastMessage = androidDriver.findElementByXPath("//android.widget.Toast[1]").getAttribute("name");
        } catch (WebDriverException elementDriverException) {
            toastMessage = null;
        }

        System.out.println("Toast message value: " + toastMessage);

        assertThat(toastMessage, is(not("Please enter your name")));

    }

}
