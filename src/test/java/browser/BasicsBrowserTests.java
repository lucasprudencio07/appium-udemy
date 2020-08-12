package browser;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BasicsBrowserTests extends BaseBrowserTests {

    private final AndroidDriver<AndroidElement> androidDriver = getCapabilities();

    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) androidDriver;

    private final String googleUrl = "https://www.google.com/";
    private final String tecnoblogTitle = "Tecnoblog | tecnologia que interessa";

    @Test
    public void testingOpeningGoogleChrome() {
        assertThat(androidDriver.getCurrentUrl(), is(equalTo(googleUrl)));
    }


    @Test
    public void goingToTecnoblogSiteTest() {

        androidDriver.findElementByXPath("//div//input[@maxlength='2048']").sendKeys("Tecnoblog");
        androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.ENTER));
        androidDriver.findElementByXPath("//a[@href=\"https://tecnoblog.net/\"]").click();

        assertThat(androidDriver.getTitle().toLowerCase(), is(equalTo(tecnoblogTitle.toLowerCase())));
    }


    @Test
    public void scrollDownAndValidateAHeaderTest() {

        goingToTecnoblogSiteTest();

        scrollDownJavaScript(0, 3500);

        assertTrue(androidDriver.findElementByXPath("//h3[.='TB Responde']").getAttribute("class").contains("capa"));

    }


//----------------------------------------------------------------------------------------------------------------------
    public void scrollDownJavaScript(Integer x, Integer y) {
        javascriptExecutor.executeScript("window.scrollBy("+ x + "," + y + ")", "");
    }

}
