package com.innowise.document.web;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocumentCreatePageTest extends AbstractWebTest {

    private static final String myURL = "http://localhost:4200/document-add";
    private static final int TIMEOUT_SECONDS = 20;
    private WebDriverWait wait;
    private static final String LEASE_LINK_ID = "lease_tab";
    private static final String TITLE_ID = "title";
    private static final String FULL_NAME_ID = "name";
    private static final String ADRESS_ID = "adress";
    private static final String OBJECT_ID = "object";
    private static final String PRICE_ID = "price";

    private static final String START_ID = "start";
    private static final String END_ID =  "end";
    private static final String OTHER_ID = "other";
    private static final String BUTTON_ADD_ID = "add";


    @Before
    public void setUp(){
        driver.get(myURL);
        wait = new WebDriverWait(driver, TIMEOUT_SECONDS);

    }

    @Test
    public void testCreateLeaseDoc() throws IOException{
        WebElement filterActiveElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(LEASE_LINK_ID)));
        filterActiveElement.click();
        WebElement titleElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(TITLE_ID)));
        titleElement.sendKeys("test title for doc");
        WebElement nameElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(FULL_NAME_ID)));
        nameElement.sendKeys("name test user");
        WebElement adressElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(ADRESS_ID)));
        adressElement.sendKeys("test user adress");
        WebElement objectElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(OBJECT_ID)));
        objectElement.sendKeys("object test");
        WebElement priceElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(PRICE_ID)));
        priceElement.sendKeys("1522");
        WebElement startElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(START_ID)));
        startElement.sendKeys("30-05-2019");
        WebElement endElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(END_ID)));
        endElement.sendKeys("30-10-2019");
        WebElement otherElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(OTHER_ID)));
        otherElement.sendKeys("bla bla bla");
        WebElement addElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(BUTTON_ADD_ID)));
        addElement.click();
    }
}
