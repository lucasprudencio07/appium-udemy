package taps;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class InteractionsWithElements {

    private static AndroidDriver<AndroidElement> androidDriver = getCapabilities();
    private TouchAction touchAction = new TouchAction(androidDriver);

    public InteractionsWithElements(AndroidDriver<AndroidElement> androidDriver, TouchAction touchAction) {
        this.androidDriver = androidDriver;
        this.touchAction = touchAction;
    }


    public static AndroidDriver<AndroidElement> getCapabilities() {

        try {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 3a Android 9");
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
            //desiredCapabilities.setCapability(MobileCapabilityType.APP, debugApkLocation);

            androidDriver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);
            androidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            return androidDriver;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }


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

    public void longPressOnATextPopupElement(WebElement webElementXPath) {
        Point webElementLocation = webElementXPath.getLocation();
        touchAction.press(PointOption.point(webElementLocation.getX(), webElementLocation.getY()))
                   .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5))).release().perform();

    }

    //swipe on a specific element using a select element by XPath and dropping in a 'X, Y' location
    public void swipeOnAElement(String webElementXPath, Integer x, Integer y) {
        WebElement webElement = androidDriver.findElementByXPath(webElementXPath);

        touchAction.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(webElement))
                .withDuration(Duration.ofSeconds(5)))
                .moveTo(PointOption.point(x, y))
                .release().perform();
    }

    //swipe on a specific element using a select and drop element by XPath
    public void swipeOnAElement(String sourceXPath, String destinationXPath) {

        WebElement firstWebElement = androidDriver.findElementByXPath(sourceXPath);
        WebElement secondWebElement = androidDriver.findElementByXPath(destinationXPath);

        touchAction.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(firstWebElement))
                .withDuration(Duration.ofSeconds(5)))
                .moveTo(ElementOption.element(secondWebElement))
                .release().perform();
    }

    // scroll down on a scrollable list of elements
    public void scrollDownOnAListAndClick(String property, String value) {
        androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(" + property + "(\"" + value + "\"))").click();
    }

    public void scrollDownOnAList(String property, String value) {
        androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(" + property + "(\"" + value + "\"))");
    }

    // drag and drop an element based on their id
    public void dragHereAndDropThere(String idSource, String idDestination) {

        WebElement firstWebElement = androidDriver.findElementById(idSource);
        WebElement secondWebElement = androidDriver.findElementById(idDestination);

        touchAction.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(firstWebElement))
                .withDuration(Duration.ofSeconds(2)))
                .moveTo(ElementOption.element(secondWebElement))
                .release().perform();
    }


    public AndroidDriver<AndroidElement> getAndroidDriver() {
        return androidDriver;
    }

    public void setAndroidDriver(AndroidDriver<AndroidElement> androidDriver) {
        this.androidDriver = androidDriver;
    }

    public TouchAction getTouchAction() {
        return touchAction;
    }

    public void setTouchAction(TouchAction touchAction) {
        this.touchAction = touchAction;
    }

}
