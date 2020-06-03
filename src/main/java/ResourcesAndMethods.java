import org.hamcrest.core.Is;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Properties;

public class ResourcesAndMethods {

    protected static Properties properties = SingleProperty.getInstance().getProperties();
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static String baseUrl;
    protected String productName;


    public static Properties getProperties() {
        return properties;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setDriver(WebDriver driver) {
        ResourcesAndMethods.driver = driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    public static WebDriver getDriver() {
        return driver;
    }




    void waitAndClickByElement(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    void waitElementsToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }


}







