package com.innowise.document.web;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class DocumentListPageTest extends AbstractWebTest {

    private static final String myURL = "http://localhost:4200/documents-list";
    private static final int TIMEOUT_SECONDS = 20;
    private static final String FILTER_ACTIVE_ID = "active_id";
    private static final String FILTER_TITLE_ID = "title";
    private static final String FILTER_MAX_SALARY_ID = "max_salary";
    private static final String FILTER_MIN_SALARY_ID = "min_salary";
    private static final String ITEMS_COUNT_ID = "item";
    private static final String NEXT_PAGE_ID = "next_page";
    private static final String PREVIOUS_PAGE_ID = "previous_page";
    private static final String BUTTON_EDIT_ID = "edit";
    private static final String BUTTON_DELETE_ID = "delete";
    private static final String WORK_LIST_ID = "work_list";
    private WebDriverWait wait;

    @Before
    public void setUp(){
        driver.get(myURL);
        wait = new WebDriverWait(driver, TIMEOUT_SECONDS);
        WebElement listElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(WORK_LIST_ID)));
        listElement.click();
    }

    @Test
    public void testFiterWorkDoc() throws IOException{
        WebElement filterActiveElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(FILTER_ACTIVE_ID)));
        filterActiveElement.click();
        WebElement filterTitleElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(FILTER_TITLE_ID)));
        filterTitleElement.click();
        WebElement filterMinSalaryElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(FILTER_MIN_SALARY_ID)));
        filterMinSalaryElement.click();
        WebElement filterMaxSalaryElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(FILTER_MAX_SALARY_ID)));
        filterMaxSalaryElement.click();
    }

    @Test
    public void testPaginationWorkDoc() throws IOException{
        WebElement previousElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(PREVIOUS_PAGE_ID)));
        previousElement.click();
        WebElement itemsElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(ITEMS_COUNT_ID)));
        itemsElement.click();
        WebElement nextElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(NEXT_PAGE_ID)));
        nextElement.click();
    }

    @Test
    public void testButtonWorkDoc() throws IOException{
        WebElement editElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(BUTTON_EDIT_ID)));
        editElement.click();
        WebElement deleteElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(BUTTON_DELETE_ID)));
        deleteElement.click();

    }
}
