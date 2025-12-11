package steps;

        import io.cucumber.java.en.Given;
        import io.cucumber.java.en.When;

        import org.junit.Assert;
        import pages.BasketPage;
        import pages.HomePage;
        import pages.MagazalarPage;

        import utils.PlaywrightManager;
        import com.microsoft.playwright.Page;

public class selectRandomStoreSteps {

    MagazalarPage magazalarPage;
    Page page = PlaywrightManager.getPage();

    @Given("Kullanıcı n11 mağazalar sayfasını açar")
    public void n11_magazalar_sayfasi_acilir() {
        this.page = PlaywrightManager.getPage();
        page.navigate("https://www.n11.com/magazalar");

        magazalarPage = new MagazalarPage(page);

        page.locator("#\\31 d5d7aff-8ee1-4a98-9022-f0e29a5e471c").click(); //çerez kabul et butonu. dom'un üstlerinde olduğu için seçimi zor olduğundan böyle ulaştım elemente

        System.out.println("Sayfa basligi: " + magazalarPage.getPageTitle());
    }

    @When("{string} magazasi aranir")
    public void magazasi_aranir(String storeName) throws InterruptedException {
        magazalarPage.searchStore(storeName);
    }

    @When("{string} ile başlayan mağazalar aranır")
    public void ile_baslayan_magazalar_aranir(String storeFirstLetter) throws InterruptedException {
        magazalarPage.filterStoreWithLetter(storeFirstLetter);
        page.waitForTimeout(2000);
        magazalarPage.clickRandomSeller();
        page.waitForTimeout(2000);
    }

    @When("Mağaza isim kontrolü yapılır")
    public void magaza_isim_kontrol() {
        String actualStoreName = magazalarPage.getStoreNameInStorePage();
        String randomProductName = magazalarPage.randomSellerName;

        Assert.assertEquals(actualStoreName.toLowerCase(), randomProductName.toLowerCase());
    }

}