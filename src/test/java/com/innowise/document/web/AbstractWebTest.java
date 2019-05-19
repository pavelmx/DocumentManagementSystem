package com.innowise.document.web;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class AbstractWebTest {

    protected WebDriver driver;

    public AbstractWebTest() {
        super();
    }

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\pavek\\git\\chromedriver.exe");
        driver = new ChromeDriver();

    }

    @After
    public void close() throws IOException {
        driver.close();
    }
}
