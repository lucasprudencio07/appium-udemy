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

    private final String jordan6Rings = "Jordan 6 Rings";
    private final String jordanSE = "Air Jordan 1 Mid SE";

    private String shoeName;

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

        interactionsWithElements.scrollDownOnAList("text", jordan6Rings);

        Integer numberOfShoes = androidDriver.findElementsById("com.androidsample.generalstore:id/productName").size();

        for (int index = 0; index < numberOfShoes; index++) {
            shoeName = androidDriver.findElementsById("com.androidsample.generalstore:id/productName").get(index).getText();

            if (shoeName.equals(jordan6Rings)) {
                assertThat(shoeName, is(equalTo(jordan6Rings)));

                androidDriver.findElementsById("com.androidsample.generalstore:id/productAddCart").get(index).click();
                break;
            }

        }

        androidDriver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();

        /*

        interactionsWithElements.scrollDownOnAList("text", jordan6Rings);

        AndroidElement product = androidDriver.findElementById("com.androidsample.generalstore:id/productName");

        String actualShoes = product.findElementByXPath("//android.widget.TextView[@text='Jordan 6 Rings']").getText();

        //System.out.println(actualShoes);
        assertThat(actualShoes, is(equalTo("Jordan 6 Rings")));

        androidDriver.findElementsById("com.androidsample.generalstore:id/productAddCart").get(0).click();

        Integer numberOfProductOnCart = Integer.parseInt(androidDriver.findElementById("com.androidsample.generalstore:id/counterText")
                                                                      .getAttribute("text"));

        assertThat(numberOfProductOnCart, is(equalTo(1)));
        Thread.sleep(10000);
         */

        Thread.sleep(3000);
    }


    @Test
    public void goBackAndAddAnotherShoeTest() throws InterruptedException {

        insertShoesOnTheCartTest();

        androidDriver.findElementById("com.androidsample.generalstore:id/appbar_btn_back").click();

        interactionsWithElements.scrollDownOnAList("text", jordanSE);

        Integer numberOfShoes = androidDriver.findElementsById("com.androidsample.generalstore:id/productName").size();

        for (int index = 0; index < numberOfShoes; index++) {
            shoeName = androidDriver.findElementsById("com.androidsample.generalstore:id/productName").get(index).getText();

            if (shoeName.equals(jordanSE)) {
                assertThat(shoeName, is(equalTo(jordanSE)));

                androidDriver.findElementsById("com.androidsample.generalstore:id/productAddCart").get(index).click();
                break;
            }

        }
        androidDriver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();

    }


    @Test
    public void verifyShoesNameAndPriceOnCartTest() throws InterruptedException {

        goBackAndAddAnotherShoeTest();

        Double j6Price = Double.parseDouble(androidDriver.findElementsById("com.androidsample.generalstore:id/productPrice")
                                                         .get(0).getText().replace("$", ""));
        Double jSEPrice = Double.parseDouble(androidDriver.findElementsById("com.androidsample.generalstore:id/productPrice")
                                                          .get(1).getText().replace("$", ""));

        Thread.sleep(5000);

        Double shoesSumActual = j6Price +jSEPrice;
        Double shoesSumExpected = Double.parseDouble(androidDriver.findElementById("com.androidsample.generalstore:id/totalAmountLbl")
                                                                  .getText().replace("$", ""));

        assertThat(shoesSumActual, is(equalTo(shoesSumExpected)));
    }

}
