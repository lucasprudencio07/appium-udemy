package application_ecommerce;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriverException;
import taps.InteractionsWithElements;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ECommerceTests extends  BaseEcommerceTests {

    private final AndroidDriver<AndroidElement> androidDriver = getCapabilities();
    private final TouchAction touchAction = new TouchAction(androidDriver);

    InteractionsWithElements interactionsWithElements = new InteractionsWithElements(androidDriver, touchAction);

    @Test
    public void openingTheApp() {

    }


    @Test
    public void fillInitialFormTest() {

        String toastMessage;

        androidDriver.findElementById("com.androidsample.generalstore:id/nameField").sendKeys("Your Name");
        androidDriver.findElementById("com.androidsample.generalstore:id/radioFemale").click();

        androidDriver.findElementById("com.androidsample.generalstore:id/spinnerCountry").click();
        interactionsWithElements.scrollDownOnAListAndClick("text", "Brazil");

        androidDriver.findElementByXPath("//android.widget.Button[@index='6']").click();

        try {
            toastMessage = androidDriver.findElementByXPath("//android.widget.Toast[1]").getAttribute("name");
        } catch (WebDriverException elementDriverException) {
            toastMessage = null;
        }

        System.out.println("Toast message value: " + toastMessage);

        assertThat(toastMessage, is(not("Please enter your name")));

    }


    @Test
    public void insertShoesOnTheCartTest() throws InterruptedException {

        fillInitialFormTest();

        interactionsWithElements.scrollDownOnAList("text", "Jordan 6 Rings");

        AndroidElement product = androidDriver.findElementById("com.androidsample.generalstore:id/productName");

        String actualShoes = product.findElementByXPath("//android.widget.TextView[@text='Jordan 6 Rings']").getText();

        //System.out.println(actualShoes);
        assertThat(actualShoes, is(equalTo("Jordan 6 Rings")));

        androidDriver.findElementsById("com.androidsample.generalstore:id/productAddCart").get(0).click();

        Integer numberOfProductOnCart = Integer.parseInt(androidDriver.findElementById("com.androidsample.generalstore:id/counterText")
                                                                      .getAttribute("text"));

        assertThat(numberOfProductOnCart, is(equalTo(1)));
        Thread.sleep(10000);
    }

}
