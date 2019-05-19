package web;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class AbstractWebTest {

    protected WebDriver driver;
    protected ScreenshotHelper screenshotHelper;

    public AbstractWebTest() {
        super();
    }

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/home/oadmin/IdeaProjects/chromedriver");
        driver = new ChromeDriver();////"C:\\MaryZheng\\tools\\webdriver\\chromedriver.exe"
        screenshotHelper = new ScreenshotHelper(driver);
    }

    @After
    public void close() throws IOException {
        driver.close();
    }
}
