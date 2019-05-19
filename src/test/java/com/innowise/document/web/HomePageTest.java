package com.innowise.document.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class HomePageTest extends AbstractWebTest{

    private static final String myURL = "http://localhost:4200/";
    private static final String SHOW_LOGIN_ID = "show_login";
    private static final String LOGIN_ID = "login";
    private static final String SHOW_REGISTER_ID = "show_register";
    private static final String REGISTER_ID = "register";
    private static final String USERNAME_ID = "username";
    private static final String REGISTER_USERNAME_ID = "rusername";
    private static final String REGISTER_NAME_ID = "rname";
    private static final String PASSWORD_ID = "password";
    private static final String REGISTER_PASSWORD_ID = "rpassword";
    private static final String EMAIL_ID = "email";
    private static final String CONFIRM_PASSWORD_ID = "cpass";
    private static final int TIMEOUT_SECONDS = 10;


    @Before
    public void setUp(){
        driver.get(myURL);
    }

    @Test
    public void testGetTitle() throws IOException {
        String title = driver.getTitle();
        assertEquals("Document Management System", title);
    }

    @Test
    public void testLogin() throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_SECONDS);
        WebElement showLoginButtonElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(SHOW_LOGIN_ID)));
        showLoginButtonElement.click();
        WebElement usernameElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(USERNAME_ID)));
        usernameElement.sendKeys("admin");
        WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(PASSWORD_ID)));
        passwordElement.sendKeys("123456");
        WebElement loginButtonElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(LOGIN_ID)));
        loginButtonElement.click();
    }

    @Test
    public void testRegister() throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_SECONDS);
        WebElement showLoginButtonElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(SHOW_REGISTER_ID)));
        showLoginButtonElement.click();
        WebElement usernameElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(REGISTER_USERNAME_ID)));
        usernameElement.sendKeys("test_user");
        WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(REGISTER_PASSWORD_ID)));
        passwordElement.sendKeys("123456");
        WebElement cpassElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(CONFIRM_PASSWORD_ID)));
        cpassElement.sendKeys("123456");
        WebElement emailElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(EMAIL_ID)));
        emailElement.sendKeys("email@test");
        WebElement nameElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(REGISTER_NAME_ID)));
        nameElement.sendKeys("test name");
        WebElement loginButtonElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(REGISTER_ID)));
        loginButtonElement.click();
    }


}
