package com.library.pages;

import com.library.utility.BrowserUtil;
import com.library.utility.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class BookPage extends BasePage {

    @FindBy(xpath = "//table/tbody/tr")
    public List<WebElement> allRows;

    @FindBy(xpath = "//input[@type='search']")
    public WebElement search;

    @FindBy(id = "book_categories")
    public WebElement mainCategoryElement;

    @FindBy(name = "name")
    public WebElement bookName;


    @FindBy(xpath = "(//input[@type='text'])[4]")
    public WebElement author;

    @FindBy(xpath = "//div[@class='portlet-title']//a")
    public WebElement addBook;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveChanges;

    @FindBy(xpath = "//div[@class='toast-message']")
    public WebElement toastMessage;

    @FindBy(name = "year")
    public WebElement year;

    @FindBy(name = "isbn")
    public WebElement isbn;

    @FindBy(xpath = "//select[@id='book_categories']")
    public WebElement categoryDropdown;


    @FindBy(id = "description")
    public WebElement description;



    public WebElement editBook(String bookISBN) {
        String xpath = "//td[2][.='"+bookISBN+"']/../td/a";
        return Driver.getDriver().findElement(By.xpath(xpath));
    }

    public WebElement borrowBook(String book) {
        String xpath = "//td[3][.='" + book + "']/../td/a";
        return Driver.getDriver().findElement(By.xpath(xpath));
    }

    public WebElement getCategoryName(int categoryNum){
        Select select = new Select(categoryDropdown);
        select.selectByIndex(categoryNum);
        return select.getFirstSelectedOption();
    }

    public String getBookDescr(){
        BrowserUtil.waitForPageToLoad(5);
        return Driver.getDriver().findElement(By.xpath("//textarea[@id='description']")).getText();
    }

    public String getCellText(String content){
        for (WebElement eachCell : allCells) {
            BrowserUtil.waitFor(2);
            if(eachCell.getText().equals(content)){
                return eachCell.getText();
            }
        }
        return null;
    }
}
