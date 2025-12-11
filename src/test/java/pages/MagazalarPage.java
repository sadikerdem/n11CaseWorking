package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Random;

public class MagazalarPage {

    private String searchValueInput_Xpath = "//input[@id='searchData']";
    private String searchButton_Xpath = "//a[@class='searchBtn']";
    private String lettersDiv_Xpath = "//div[@class='letters']";
    private String firstLetter_Xpath;
    private String storeName = "//div[@class='resultView']//h1";
    public String randomSellerName;

    private Page page;

    public MagazalarPage(Page page) {
        this.page = page;
    }

    // getting page title for logging
    public String getPageTitle() {
        return page.title();
    }

    // searching store name
    public void searchStore(String storeName) {
        page.fill(searchValueInput_Xpath, storeName);
        page.click(searchButton_Xpath);
        page.waitForTimeout(2000);
    }

    // filtering store name
    public void filterStoreWithLetter(String storeFirstLetter) {
        this.firstLetter_Xpath = "//span[@title='"+storeFirstLetter+"']";

        page.locator(lettersDiv_Xpath).scrollIntoViewIfNeeded();
        page.click(firstLetter_Xpath);
    }

    public String clickRandomSeller() {
        page.waitForTimeout(2000);
        Locator liList = page.locator("div.sellerListHolder ul li");

        int count = liList.count();

        if (count == 0) {
            throw new RuntimeException("sellerListHolder içinde li bulunamadı!");
        }

        int randomIndex = new Random().nextInt(count);

        Locator randomLi = liList.nth(randomIndex);

        randomLi.scrollIntoViewIfNeeded();
        randomSellerName = randomLi.locator("a[title]").innerText().trim();

        System.out.println("Rastgele seçilen mağaza ismi: " + randomSellerName);
        randomLi.click();

        System.out.println("Seçilen index: " + randomIndex);

        return randomSellerName;
    }

    public String getStoreNameInStorePage() {
        String actualStoreName = page.locator(storeName).innerText().trim();
        System.out.println("Mağaza sayfasındaki mağaza ismi: " + randomSellerName);
        return actualStoreName;
    }

}