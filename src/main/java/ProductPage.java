import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends ResourcesAndMethods{

    public ProductPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        ResourcesAndMethods.wait = new WebDriverWait(driver, 5);
    }

    @FindBy(xpath = "//span[@class=\"current-price-value\"]")
    private WebElement price;
    @FindBy(xpath = "//select[@class=\"form-control select\"]")
    private WebElement guarantee;
    @FindBy(xpath = "//div[contains(@class,'buttons-wrapper')]//button[contains(@class,\"buy-btn\")]")
    public WebElement buttonBuy;

    public WebElement getCartLink() {
        return cartLink;
    }


    @FindBy(xpath = "//span[@class='cart-link__price']")
    private WebElement cartLink;


    @Override
    public String toString(){
        return this.driver + " " +  this.productName;
    }


    public void setButtonBuy(WebElement buttonBuy) {
        this.buttonBuy = buttonBuy;
    }

    public WebElement getButtonBuy() {
        return buttonBuy;
    }

    public double savePrice(Product product) {
        ResourcesAndMethods.wait.until(ExpectedConditions.elementToBeClickable(buttonBuy));
        double productPrice = Double.parseDouble(price.getAttribute("data-price-value"));
        product.setPrice(productPrice);
        BasketPage.addCountAndPrice(product.getName(), productPrice);
        return productPrice;
    }


    public ProductPage buy() {
		waitElementsToBeClickable(buttonBuy);
        buttonBuy.click();
        return new ProductPage(ResourcesAndMethods.driver);
    }
}

