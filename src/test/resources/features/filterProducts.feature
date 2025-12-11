Feature: Ürün filtreleme işlemleri

  Scenario: Telefon ürünlerinin gelen sonuçlara göre filtrelenmesi yapılır
    Given Kullanıcı n11 ana sayfasını açar
    When "telefon" ürünü aranir
    Then En fazla yorum alan ürüne göre sıralanır
    And Marka filtrelemesi yapılır
    And Kargo filtrelemesi yapılır

    When Filtreleme sonrası ücretsiz kargo kontrol edilir
    And Sıralama sonrası yorum sayısı kontrol edilir