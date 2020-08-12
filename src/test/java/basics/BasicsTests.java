package basics;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;


public class BasicsTests extends BaseTests {

    private final AndroidDriver<AndroidElement> androidDriver = getCapabilities();
    private final TouchAction touchAction = new TouchAction(androidDriver);

    private static final String nineThirty = "9:30";
    private static final String minute15Path = "//*[@content-desc='15']";
    private static final String minute30Path = "//*[@content-desc='30']";
    private static final String textProperty = "text";
    private static final String textvalueWebView = "WebView";
    private static final String dragDot01 = "io.appium.android.apis:id/drag_dot_1";
    private static final String dragDot03 = "io.appium.android.apis:id/drag_dot_3";
    private static final String dragDotHidden = "io.appium.android.apis:id/drag_dot_hidden";

    @Test
    public void workingWithElementsTest() {

        androidDriver.findElementByXPath("//android.widget.TextView[@text='Preference']").click();
        androidDriver.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']").click();
        androidDriver.findElementById("android:id/checkbox").click();
        androidDriver.findElementByXPath("(//android.widget.RelativeLayout)[2]").click();

        androidDriver.findElementByClassName("android.widget.EditText").sendKeys("A Wi-Fi Network");

        androidDriver.findElementById("android:id/button1").click();
    }


    @Test
    public void workingWithFieldsValidationTest() {

        // this find you can select by an 'AttributeName("value")', using the Android UI Automator
        androidDriver.findElementByAndroidUIAutomator("text(\"Views\")").click();

        // it works similarly to the top one, except that you need to call a selector internally
        Integer numberOfClickableObjects = androidDriver.findElementsByAndroidUIAutomator("new UiSelector().clickable(true)").size();

        assertThat(numberOfClickableObjects, is(equalTo(14)));
    }


    @Test
    public void workingWithExpandableListTest() {

        String customAdapterPath = "//android.widget.TextView[@text='1. Custom Adapter']";
        String peopleNamesPath = "//android.widget.TextView[@text='People Names']";

        androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        androidDriver.findElementByAndroidUIAutomator("text(\"Views\")").click();
        androidDriver.findElementByXPath("//android.widget.TextView[@text='Expandable Lists']").click();

        tapAnElement(customAdapterPath);
        longPressOnAElement(peopleNamesPath);
    }


    @Test
    public void workingWithSwipeElementsTest() {

        androidDriver.findElementByAndroidUIAutomator("text(\"Views\")").click();
        androidDriver.findElementByXPath("//android.widget.TextView[@text='Date Widgets']").click();
        androidDriver.findElementByXPath("//android.widget.TextView[@text='2. Inline']").click();
        androidDriver.findElementByXPath("//*[@content-desc='9']").click();

//        swipeOnAElement(minute15Path, 495, 1080);
        swipeOnAElement(minute15Path, minute30Path);

        String hours = androidDriver.findElementById("android:id/hours").getText();
        String minutes = androidDriver.findElementById("android:id/minutes").getText();

        String actualClockTime = hours + ":" + minutes;

        assertThat(actualClockTime, is(equalTo(nineThirty)));
    }


    @Test
    public void workingWithScrollDownTest() {

        androidDriver.findElementByAndroidUIAutomator("text(\"Views\")").click();
        scrollDownOnAList(textProperty, textvalueWebView);
    }


    @Test
    public void workingWithDrapAndDropTest() {

        androidDriver.findElementByAndroidUIAutomator("text(\"Views\")").click();
        androidDriver.findElementByAndroidUIAutomator("text(\"Drag and Drop\")").click();

        dragAndDrop(dragDot01, dragDot03);
        dragAndDrop(dragDot03, dragDotHidden);
    }



//------------------------------------------------------------------------------------------------------------------
// Elements Interactions

    //Tap on a element
    public void tapAnElement(String webElementXPath) {
        WebElement webElement = androidDriver.findElementByXPath(webElementXPath);
        touchAction.tap(TapOptions.tapOptions().withElement(ElementOption.element(webElement))).perform();
    }


    //Tap on a specific position
    public void tapAnElement(Integer x, Integer y) {
        touchAction.tap(TapOptions.tapOptions().withPosition(PointOption.point(x, y))).perform();
    }


    //Long pressing on a element
    public void longPressOnAElement(String webElementXPath) {
        WebElement webElement = androidDriver.findElementByXPath(webElementXPath);

        touchAction.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(webElement))
                                                                 .withDuration(Duration.ofSeconds(2))).release().perform();
    }


    public void swipeOnAElement(String webElementXPath, Integer x, Integer y) {
        WebElement webElement = androidDriver.findElementByXPath(webElementXPath);

        touchAction.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(webElement))
                                                                 .withDuration(Duration.ofSeconds(5)))
                                                                 .moveTo(PointOption.point(x, y))
                                                                 .release().perform();
    }


    public void swipeOnAElement(String sourceXPath, String destinationXPath) {

        WebElement firstWebElement = androidDriver.findElementByXPath(sourceXPath);
        WebElement secondWebElement = androidDriver.findElementByXPath(destinationXPath);

        touchAction.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(firstWebElement))
                                                                 .withDuration(Duration.ofSeconds(5)))
                                                                 .moveTo(ElementOption.element(secondWebElement))
                                                                 .release().perform();
    }


    public void scrollDownOnAList(String property, String value) {
        androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(" + property + "(\"" + value + "\"))");
    }


    public void dragAndDrop(String idSource, String idDestination) {

        WebElement firstWebElement = androidDriver.findElementById(idSource);
        WebElement secondWebElement = androidDriver.findElementById(idDestination);

        touchAction.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(firstWebElement))
                                                                 .withDuration(Duration.ofSeconds(2)))
                                                                 .moveTo(ElementOption.element(secondWebElement))
                                                                 .release().perform();
    }

}
