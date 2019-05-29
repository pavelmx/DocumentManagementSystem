package com.innowise.document.web;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class ProfilePageTest extends AbstractWebTest {

    private static final String myURL = "http://localhost:4200/profile";
    private static final int TIMEOUT_SECONDS = 10;
    private static final String NAME_ID = "name";
    private static final String ADRESS_ID = "adress";
    private static final String NEW_PASSWORD_ID = "pass";
    private static final String BUTTON_UPDATE_ID = "update";
    private WebDriverWait wait;


    @Before
    public void setUp(){
        driver.get(myURL);
        wait = new WebDriverWait(driver, TIMEOUT_SECONDS);
    }

    @Test
    public void testChangeProfile() throws IOException{
        WebElement nameElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(NAME_ID)));
        nameElement.sendKeys(" new");
        WebElement adressElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(ADRESS_ID)));
        adressElement.sendKeys(" new");
        WebElement newPasswordElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(NEW_PASSWORD_ID)));
        newPasswordElement.sendKeys("1234567");
        WebElement updateElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(BUTTON_UPDATE_ID)));
        updateElement.click();
    }
}
