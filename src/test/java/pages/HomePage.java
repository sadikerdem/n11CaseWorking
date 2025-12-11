package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;


public class HomePage {

    private String resultMeanText_Xpath = "//span[@class='result-mean-word']";
    private String searchValueInput_Xpath = "//input[@id='searchData']";
    private String searchButton_Xpath = "//a[@class='searchBtn']";
    public List<String> addedProducts = new ArrayList<>();
    private String secondBrandFilter_Xpath = "(//input[@class='brand-checkbox'])[2]/parent::label";
    private String deliveryOption_Xpath = "//section[@data-tag-name='Delivery Option']/h2";
    private String freeDeliveryOption_Xpath = "//label[@id='freeShipmentOption']";
    private String sortingSelect_Xpath = "//div[@class='custom-select']";
    private String sortingByReviewSelect_Xpath = "//div[@class='custom-select']//div[@data-value='REVIEWS']";
    private String firstColorSelection = "//div[@data-name='Renk']/label[1]";
    private String firstMemorySelection = "//div[@data-name='Dahili Hafıza']/label[1]";
    private String addToCartButton = "//span[@id='js-addBasketSku']";
    private String firstProductBasketButton_Xpath = "(//span[@class='btnBasket'])[1]";

    private Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    // getting page title for logging
    public String getPageTitle() {
        return page.title();
    }

    // searching product name
    public void searchProduct(String productName) {
        //page.click(searchValueInput_Xpath);
        page.fill(searchValueInput_Xpath, productName);
        page.click(searchButton_Xpath);
    }

    // getting text from autocorrected search result
    public String getTextFromCorrectedResult() {
        String text = page.locator(resultMeanText_Xpath).textContent().replace("\"", "");

        return text;
    }

    public void addCartFirstProduct() throws InterruptedException {
        Locator liList = page.locator("div.catalogView ul li");

        int count = liList.count();

        if (count == 0) {
            throw new RuntimeException("sellerListHolder içinde li bulunamadı!");
        }

        //ürünün ismini alıp listeye atıyoruz, listeyi globalde tutuyorum
        String productName = liList.nth(0).locator("h3.productName").innerText().trim();
        addedProducts.add(productName);
        System.out.println("firstproduct: " + addedProducts);

        page.click(firstProductBasketButton_Xpath);
        page.waitForTimeout(2000);

        // Renk seçenekleri varsa ilkini seç
        if (page.locator(firstColorSelection).isVisible()) {
            page.click(firstColorSelection);
            page.waitForTimeout(2000);
        }
        // Hafıza seçenekleri varsa ilkini seç
        if (page.locator(firstMemorySelection).isVisible()) {
            page.click(firstMemorySelection);
            page.waitForTimeout(2000);
        }
        page.click(addToCartButton);
        page.waitForTimeout(2000);
    }

    public void addCartLastProduct() throws InterruptedException {
        Locator liList = page.locator("div.catalogView ul li");

        Locator feedbackArea = page.locator("//div[@class='feedback-area']");
        Locator pagination = page.locator("//div[@class='pagination']");

        // 1) Feedback-area görene kadar scroll
        while (!feedbackArea.isVisible()) {
            page.evaluate("window.scrollBy(0, 300)");
            page.waitForTimeout(400);
        }

        // 2) Feedback-area görünür olduktan sonra pagination görünene kadar küçük scroll
        while (!pagination.isVisible()) {
            page.evaluate("window.scrollBy(0, 200)");
            page.waitForTimeout(600);
        }

        // 3) Son ürün sayısını al
        int count = liList.count();
        Locator lastLi = liList.nth(count - 1);
        lastLi.scrollIntoViewIfNeeded();

        // 4) Ürünün adını al
        String productName = lastLi.locator("h3.productName").innerText().trim();
        addedProducts.add(productName);
        System.out.println("Seçilen son ürün: " + productName);

        // 5) Sepete ekle butonuna tıklama
        page.waitForTimeout(2000);
        String lastBasketXpath = "(//span[@class='btnBasket'])[" + count + "]";
        page.click(lastBasketXpath);
        page.waitForTimeout(2000);

        // Renk seçimi varsa
        if (page.locator(firstColorSelection).isVisible()) {
            page.click(firstColorSelection);
            page.waitForTimeout(2000);
        }

        // Hafıza seçimi varsa
        if (page.locator(firstMemorySelection).isVisible()) {
            page.click(firstMemorySelection);
            page.waitForTimeout(2000);
        }
        page.click(addToCartButton);
        page.waitForTimeout(2000);
    }

    // brand filtering
    public void brandFilter() {
        page.locator(secondBrandFilter_Xpath).scrollIntoViewIfNeeded();
        page.click(secondBrandFilter_Xpath);
        page.waitForTimeout(2000);
    }

    // delivery filtering
    public void freeDeliverySelection() {
        page.locator(deliveryOption_Xpath).scrollIntoViewIfNeeded();
        page.click(deliveryOption_Xpath);
        page.click(freeDeliveryOption_Xpath);
        page.waitForTimeout(2000);
    }

    // review sorting
    public void sortingByReview() {
        page.click(sortingSelect_Xpath);
        page.waitForTimeout(2000);
        page.click(sortingByReviewSelect_Xpath);
        page.waitForTimeout(2000);
    }

    // sorting filtering validation
    public void afterSortingReviewCountValidation() {
        Locator commentCounts = page.locator("div.ratingCont span.ratingText");

        List<Integer> counts = new ArrayList<>();

        int size = commentCounts.count();

        for (int i = 0; i < size; i++) {
            String text = commentCounts.nth(i).innerText().replace(".", "").replace("(", "")
                    .replace(")", "")
                    .trim();
            counts.add(Integer.parseInt(text));
        }

        // Asıl doğrulama: liste zaten büyükten küçüğe sıralı mı?
        for (int i = 0; i < counts.size() - 1; i++) {
            Assert.assertTrue("Sıralama hatalı: " + counts.get(i) + " > " + counts.get(i + 1),
                    counts.get(i) >= counts.get(i + 1));
        }
        System.out.println("Ürünlerin azalan yorum sayısı kontrolleri tamamlandı: BAŞARILI! ");
    }

    // sorting filtering validation
    public void afterFilteringFreeDeliveryValidation() {
        Locator products = page.locator("div.catalogView ul li");

        int count = products.count();

        for (int i = 0; i < count; i++) {
            Assert.assertTrue(
                    "Ürün " + i + " ücretsiz kargo içermiyor!",
                    products.nth(i).getByText("ÜCRETSİZ KARGO").count() > 0
                        );
        }
        System.out.println("Ürünlerin ücretsiz kargo bage kontrolleri tamamlandı: BAŞARILI! ");
    }






}
