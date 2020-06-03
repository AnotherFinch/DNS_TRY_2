import org.hamcrest.core.Is;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestTest extends ResourcesAndMethods {

    MainPage mainPage = new MainPage(driver);
    Product playstation = new Product("playstation");
    Product detroit = new Product("Detroit");
    ProductPage productPage = new ProductPage(driver);
    BasketPage basket = new BasketPage(driver);

    @BeforeClass
    public static void startUp() {
        String browser = System.getProperty("browser", "chrome");
        if ("chrome".equals(browser)) {
            System.setProperty("webdriver.chrome.driver", properties.getProperty("pathChromeDriver"));
            driver = new ChromeDriver();
        } else if ("firefox".equals(browser)) {
            System.setProperty("webdriver.gecko.driver", properties.getProperty("pathFirefoxDriver"));
            driver = new FirefoxDriver();
        }

        baseUrl = properties.getProperty("DNSURL");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void simpleTest() {
        driver.get(baseUrl);
        waitAndClickByElement(mainPage.inputSearch);
        mainPage.inputSearch.sendKeys("playstation");
        mainPage.inputSearch.sendKeys(Keys.ENTER);
        waitAndClickByElement(mainPage.ps4);
        productPage.savePrice(playstation);
        productPage.buy();
        waitAndClickByElement(mainPage.inputSearch);
        mainPage.inputSearch.sendKeys("Detroit");
        mainPage.inputSearch.sendKeys(Keys.ENTER);
        productPage.savePrice(detroit);
        productPage.buttonBuy.click();
        waitAndClickByElement(productPage.getCartLink());
        System.out.println("Цена на playstation : " + playstation.getPrice());
        System.out.println("Цена на detroit : " + detroit.getPrice());
        System.out.println("Общая цена : " + BasketPage.ProductTotalPrice.getTotalPriceInCart());
        basket.radioButton24.click();
        basket.addProductToCart(basket.getButtonAddToCart(),3);
        Assert.assertThat("Цена не верна", basket.getTotalPriceOfProductInMenuBar(), Is.is(92037.0));
        basket.deleteProductFromCartPage();
        basket.restoreProduct();
        basket.waitUntilClick();
        Assert.assertTrue("Товарв " + playstation.getName() + " нет в корзине ",basket.checkProduct(playstation));
        Assert.assertFalse("Гарантия не выбрана",basket.radioButtonIsChecked(basket.getRadioButtonXPath()));
    }

    @AfterClass
    public static void quit() {
        driver.quit();
    }
}


