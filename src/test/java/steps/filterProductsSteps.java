package steps;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.When;
import pages.HomePage;
import utils.PlaywrightManager;

public class filterProductsSteps {

    HomePage homePage;
    Page page = PlaywrightManager.getPage();

    @When("En fazla yorum alan ürüne göre sıralanır")
    public void yoruma_gore_siralama() {
        homePage = new HomePage(page);
        homePage.sortingByReview();
    }

    @When("Marka filtrelemesi yapılır")
    public void marka_filtre() {
        homePage = new HomePage(page);
        homePage.brandFilter();
    }

    @When("Kargo filtrelemesi yapılır")
    public void kargo_filtre() {
        homePage = new HomePage(page);
        homePage.freeDeliverySelection();
    }

    @When("Sıralama sonrası yorum sayısı kontrol edilir")
    public void siralama_sonrasi_yorum_kontrol() {
        homePage = new HomePage(page);
        homePage.afterSortingReviewCountValidation();
    }

    @When("Filtreleme sonrası ücretsiz kargo kontrol edilir")
    public void filtreleme_sonrasi_kargo_kontrol() {
        homePage = new HomePage(page);
        homePage.afterFilteringFreeDeliveryValidation();
    }

}
