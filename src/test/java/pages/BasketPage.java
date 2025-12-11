package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.List;

public class BasketPage {
    private String resultMeanText_Xpath = "//span[@class='result-mean-word']";
    private String searchValueInput_Xpath = "//input[@id='searchData']";
    private String searchButton_Xpath = "//a[@class='searchBtn']";
    private String basketButton_Xpath = "//a[@title='Sepetim']";
    public List<String> actualNames = new ArrayList<>();

    private Page page;

    public BasketPage(Page page) {
        this.page = page;
    }

    // getting page title for logging
    public String getPageTitle() {
        return page.title();
    }

    // clicking to basket buton
    public void goingToBasket() {
        page.click(basketButton_Xpath);
    }

    // getting product names from basket
    public void getProductNameListFromBasket() {
        Locator cartItemNames = page.locator("//a[@class='prodDescription']");
        page.waitForTimeout(2000);

        for (int i = 0; i < cartItemNames.count(); i++) {
            actualNames.add(cartItemNames.nth(i).innerText().trim());
            page.waitForTimeout(500);
        }
        System.out.println("cartItemNameList: " + actualNames);
    }



}
