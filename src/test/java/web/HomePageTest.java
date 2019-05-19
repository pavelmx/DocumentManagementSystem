package web;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class HomePageTest extends AbstractWebTest{



    @Test
    public void test() throws IOException {
        driver.get("https://www.yandex.ru/");
        screenshotHelper.saveScreenshot("home_screenshot.png");
        String title = driver.getTitle();
        //screenshotHelper.saveScreenshot("invalid_screenshot.png");
        assertEquals("Яндекс", title);
    }
}
