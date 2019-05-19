package com.innowise.document.web;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class DocumentEditPageTest extends AbstractWebTest {

    private static final String myURL = "http://localhost:4200/work-edit";
    private static final int TIMEOUT_SECONDS = 20;
    private static final String OTHER_INFO_ID = "other";
    private static final String BUTTON_UPDATE_ID = "update";
    private static final String BUTTON_UPLOAD_ID = "upload";
    private static final String BUTTON_SHOW_UPLOAD_ID = "show_upload";
    private static final String SELECT_FILE_ID = "selector";
    private WebDriverWait wait;

    @Before
    public void setUp(){
        driver.get(myURL);
        wait = new WebDriverWait(driver, TIMEOUT_SECONDS);
    }

    @Test
    public void testEditWorkDoc() throws IOException{
        WebElement infoElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(OTHER_INFO_ID)));
        infoElement.sendKeys("bla bla bla");
        WebElement updateElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(BUTTON_UPDATE_ID)));
        updateElement.click();
    }

    @Test
    public void testUploadDoc() throws IOException, InterruptedException{
        WebElement showUploadElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(BUTTON_SHOW_UPLOAD_ID)));
        showUploadElement.click();
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(SELECT_FILE_ID)));
        selectElement.sendKeys("C:\\Users\\Public\\Pictures\\Sample Pictures\\Penguins.jpg");
        WebElement uploadElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(BUTTON_UPLOAD_ID)));
        uploadElement.click();
    }
}
