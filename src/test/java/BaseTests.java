import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseTests {

    private static final String debugApkLocation = System.getProperty("user.dir") + "//src//main//resources//apk//ApiDemos-debug.apk";
    private static AndroidDriver<AndroidElement> androidDriver;


    public static AndroidDriver<AndroidElement> getCapabilities() {

        try {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 3a Android 9");
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
            desiredCapabilities.setCapability(MobileCapabilityType.APP, debugApkLocation);

            androidDriver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);
            androidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            return androidDriver;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @AfterAll
    public static void tearDown() {
        androidDriver.closeApp();
    }

}
