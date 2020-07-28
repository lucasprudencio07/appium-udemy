import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class TestingMaven {

    private static WebDriver firefoxDriver;

    @BeforeAll
    public static void setUp() {

//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");

        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\lucas\\Documents\\Drivers\\chromedriver83.exe");
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\lprudencio\\Downloads\\Webdriver\\geckodriver.exe");

        firefoxDriver = new FirefoxDriver();
        firefoxDriver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
        firefoxDriver.manage().window().maximize();

    }


    @BeforeEach
    public void loadYoutube() {
        firefoxDriver.get("https://youtube.com");
    }


    @Test
    public void testingSelenium() {
        Assertions.assertEquals(1, 1);
    }


    @AfterAll
    public static void tearDown() {
        firefoxDriver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
        firefoxDriver.quit();
    }
}
