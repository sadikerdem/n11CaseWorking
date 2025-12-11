Feature: Sepete ekleme işlemleri

  Scenario: Arama yapılır ve gelen sonuçtan ilk sayfadaki birinci ve sonuncu sepete eklenir
    Given Kullanıcı n11 ana sayfasını açar
    When "samsung telefonn" ürünü aranir
    Then Yanlış girilen kelimenin "samsung telefon" olarak düzeltildiği kontrol edilir

    Given İlk ve son ürün sepete atılır
    When Sepete gidilir
    Then Sepet kontrolü yapılır