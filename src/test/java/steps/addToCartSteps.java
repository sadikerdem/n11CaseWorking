package steps;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.BasketPage;
import pages.HomePage;
import pages.MagazalarPage;
import utils.PlaywrightManager;

import java.util.Collections;
import java.util.List;

public class addToCartSteps {

    MagazalarPage magazalarPage;
    HomePage homePage;
    BasketPage basketPage;
    Page page;

    @Given("Kullanıcı n11 ana sayfasını açar")
    public void n11_ana_sayfasi_acilir() {
        this.page = PlaywrightManager.getPage();
        page.navigate("https://www.n11.com");

        magazalarPage = new MagazalarPage(page);

        page.locator("#\\31 d5d7aff-8ee1-4a98-9022-f0e29a5e471c").click(); //çerez kabul et butonu. dom'un üstlerinde olduğu için seçimi zor olduğundan böyle ulaştım elemente

        System.out.println("Sayfa basligi: " + magazalarPage.getPageTitle());
    }

    @When("{string} ürünü aranir")
    public void urunu_aranir(String productName) throws InterruptedException {
        //page = PlaywrightManager.getPage();
        homePage = new HomePage(page);
        page.waitForTimeout(2000);
        homePage.searchProduct(productName);
        page.waitForTimeout(2000);
    }

    @When("Yanlış girilen kelimenin {string} olarak düzeltildiği kontrol edilir")
    public void auto_correct_kelime_kontrol_edilir(String expectedText) {
        String actualText = homePage.getTextFromCorrectedResult();
        Assert.assertEquals(expectedText, actualText);
    }

    @When("İlk ve son ürün sepete atılır")
    public void ilk_ve_son_urun_sepete_atilir() throws InterruptedException {
        homePage.addCartFirstProduct();
        homePage.addCartLastProduct();
    }

    @When("Sepete gidilir")
    public void sepete_gidilir() {
        //page = PlaywrightManager.getPage();
        basketPage = new BasketPage(page);
        basketPage.goingToBasket();
    }

    @When("Sepet kontrolü yapılır")
    public void sepet_kontrolu() throws InterruptedException {
        basketPage.getProductNameListFromBasket();

        List<String> addedProducts = homePage.addedProducts;
        page.waitForTimeout(2000);
        List<String> actualNames = basketPage.actualNames;
        System.out.println("Beklenen ürün isimleri: " + actualNames);

        page.waitForTimeout(2000);
        Collections.sort(addedProducts);
        Collections.sort(actualNames);

        page.waitForTimeout(2000);
        Assert.assertEquals(addedProducts, actualNames);
    }

}
