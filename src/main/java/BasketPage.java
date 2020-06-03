import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

public class BasketPage extends ResourcesAndMethods {

    public BasketPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 5);
    }




    protected WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(xpath = "//div[@class='base-ui-radio-button additional-warranties-row__radio']//span[contains(text(),'+ 24  мес.')]")
    public WebElement radioButton24;
    @FindBy(xpath = "//div[@data-cart-product-id][1]//*[@class='count-buttons__icon-plus']")
    WebElement buttonAddToCart;
    @FindBy(xpath = "//div[@data-cart-product-id][2]//*[@class='count-buttons__icon-minus']")
    private WebElement buttonDeleteFromCart;
    @FindBy(xpath = "//a[contains(text(),'Вернуть удалённый товар')]")
    private WebElement restoreProduct;
    @FindBy(xpath = "//button[@class='menu-control-button' and contains(text(),'Удалить')]")
    private WebElement buttonDelete;
    @FindBy(xpath = "//span[contains(text(),'Корзина')]")
    public WebElement basketLink;

    private String radioButtonCheckedXPath = "//span[contains(@class,'base-ui-radio-button__icon_checked') and contains(text(),'24')]";
    private String productInCart = "//div[contains(text(),'%d')]/parent::div/parent::div/parent::div//span[@class='price__current']";

    public String getRadioButtonXPath() {
        return radioButtonCheckedXPath;
    }

    public WebElement getProductInCart(Product product) {
        return driver.findElement(By.xpath(String.format(productInCart, product.getId())));
    }

    public WebElement getButtonDeleteFromCart() {
        return buttonDeleteFromCart;
    }

    public WebElement getButtonAddToCart() {
        return buttonAddToCart;
    }



    public void waitToRefresh() {
        try {
            WebDriverWait wdw = new WebDriverWait(driver, 5);
            setTotalPriceInCart(getTotalPriceOfProductInMenuBar());
            wdw.until(driver -> {
                double newPriceInCart = getTotalPriceOfProductInMenuBar();
                return newPriceInCart != ProductTotalPrice.getTotalPriceInCart();
            });
        } catch (org.openqa.selenium.TimeoutException e) {
            e.printStackTrace();
        }
    }

    public Double getTotalPriceOfProductInMenuBar() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(basketLink));
            return Double.parseDouble(basketLink.getText().replaceAll(" ", ""));
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            wait.until(ExpectedConditions.elementToBeClickable(basketLink));
            return Double.parseDouble(basketLink.getText().replaceAll(" ", ""));
       }
    }

    public boolean checkProduct(Product product) {
        try {
            wait.until(ExpectedConditions.visibilityOf(getProductInCart(product)));
            return getProductInCart(product).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    public static class ProductTotalPrice {
        private static Map<String,Double> totalPrices= new HashMap<>();;
        private static Map<String,Integer> totalCounts= new HashMap<>();
        private static Double totalPriceInCart = 0.0;

        public static Double getTotalPriceInCart() {
            return totalPriceInCart;
        }






    }

    public BasketPage addProductToCart(WebElement element, int count) {
        for (int i = 0; i < count; i++) {
            element.click();
            waitToRefresh();
        }
        return this;
    }

    public BasketPage restoreProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(restoreProduct)).click();
        return this;
    }

    public BasketPage deleteProductFromCartPage() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonDelete));
        buttonDelete.click();
        return this;
    }

    public boolean radioButtonIsChecked(String XPath) {
        return driver.findElements(By.xpath(XPath)).isEmpty();
    }

    public void waitUntilClick() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='menu-control-button' and contains(text(),'Удалить')]")));
    }



    public static Map<String, Double> totalPrices = new HashMap<>();
    public static Map<String, Integer> totalCounts = new HashMap<>();
    public static Double totalPriceInCart = 0.0;


    public static void addCountAndPrice(String name, Double price) {
        totalCounts.put(name, totalCounts.getOrDefault(name, 0) + 1);
        totalPrices.put(name, totalPrices.getOrDefault(name, 0.0) + price);
    }

    public static double getTotalPriceOfAll() {
        double sum = 0.0;
        for (double d : totalPrices.values()) {
            sum += d;
        }
        return sum;
    }

    public static void setTotalPriceInCart(Double totalPriceInCart) {
        BasketPage.totalPriceInCart = totalPriceInCart;
    }

    public boolean wasPriseChangedInCart(Product product, BasketPage basketPage) {
        return (ProductTotalPrice.getTotalPriceInCart() - basketPage.getTotalPriceOfProductInMenuBar()) == product.getPrice();
    }


}


