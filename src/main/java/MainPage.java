import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    public MainPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 5);
    }

    public WebDriver driver;
    public WebDriverWait wait;

    @FindBy(xpath = "//input[@placeholder='Поиск по сайту']")
    public WebElement inputSearch;

    @FindBy(xpath = "//*[@id=\"ube6ad655a2fb4e1fa1ef1f070dbf59c8\"]/div/div[2]/span[2]")
    public WebElement inputSearchButton;


    @FindBy(xpath = "//a[contains(text(),'PlayStation 4 Slim Black')]")
    public WebElement ps4;

    @FindBy(xpath = "//a[contains(text(),'Detroit')]")
    public WebElement detroit;

    @FindBy(xpath = "//span[contains(text(),'Корзина')]")
    public WebElement basket;

}
