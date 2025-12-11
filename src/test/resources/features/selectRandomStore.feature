Feature: Mağaza işlemleri

  Scenario: Girilen harf ile başlayan mağazalar sıralanır
    Given Kullanıcı n11 mağazalar sayfasını açar
    When "B" ile başlayan mağazalar aranır
    Then Mağaza isim kontrolü yapılır