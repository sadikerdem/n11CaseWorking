# N11 Playwright + Java + Cucumber Test Automation Project

Bu proje; **Playwright**, **Java**, **Cucumber (BDD)** ve **Allure Report** kullanılarak hazırlanmış bir
UI test otomasyonu örneğidir.  
Örnek akış: N11 üzerinde ürün arama, ilk ve son ürünün sepete eklenmesi ve sepet kontrolü.

---

## Teknolojiler

- **Java 17**
- **Playwright Java**
- **Cucumber JVM 7**
- **JUnit**
- **Allure Reports**
- **Maven**

---

## Proje Yapısı
n11CaseWorking/
│
├── src
│   ├── main
│   └── test
│       ├── java
│       │   ├── hooks/
│       │   │   └── Hooks.java
│       │   ├── steps/
│       │   │   └── StepDefinitions.java
│       │   ├── pages/
│       │   │   ├── HomePage.java
│       │   │   ├── BasketPage.java
│       │   │   └── MagazalarPage.java
│       │   └── utils/
│       │       └── PlaywrightManager.java
│       └── resources
│           └── features/
│               └── add_to_cart.feature
│
├── pom.xml
└── README.md

---

## Testleri Çalıştırma

Tüm testleri çalıştırmak için:
mvn clean test

Belirli bir feature dosyasını çalıştırmak için:
mvn clean test -Dcucumber.options=“src/test/resources/features/add_to_cart.feature”

---

## Allure Raporu Oluşturma

Önce Allure CLI kurulu olmalı:
brew install allure

Raporu çalıştır:
allure serve allure-results

---

## Öne Çıkan Özellikler

- **Playwright auto-wait** sayesinde stabil testler  
- **Page Object Model** mimarisi  
- **Cucumber BDD** yapısı  
- **Hooks ile test öncesi Playwright başlatma / kapatma**  
- **Allure Report** ile detaylı test raporları  

---

## Örnek Senaryo

```gherkin
Scenario: Arama yapılır ve ilk & son ürün sepete eklenir
    Given Kullanıcı n11 ana sayfasını açar
    When "samsung telefon" ürünü aranir
    Then İlk ve son ürün sepete atılır
    When Sepete gidilir
    Then Sepet kontrolü yapılır

Geliştirici

Sadık Erdem ŞEN
GitHub: https://github.com/sadikerdem





