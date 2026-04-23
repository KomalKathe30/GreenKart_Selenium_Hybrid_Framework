package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    By searchBox = By.className("search-keyword");
    By products = By.cssSelector(".product");
    By topDeals = By.linkText("Top Deals");
    By dealsDropdown = By.id("page-menu");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void searchItem(String item) {
        WebElement search = wait.until(
            ExpectedConditions.visibilityOfElementLocated(searchBox));
        search.clear();
        search.sendKeys(item);

        // wait for filtered results
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(products, 0));
    }

    public void clickAddToCart(String product) {

        // ✅ Fix case issue
        String formatted = product.substring(0,1).toUpperCase() + product.substring(1);

        By addToCart = By.xpath(
            "//h4[contains(text(),'" + formatted + "')]/parent::div//button");

        wait.until(ExpectedConditions.visibilityOfElementLocated(products));
        wait.until(ExpectedConditions.elementToBeClickable(addToCart)).click();
    }

    public void clickTopDeals() {
        wait.until(ExpectedConditions.elementToBeClickable(topDeals)).click();
    }

    public void switchToChildWindow() {
        String parent = driver.getWindowHandle();
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(parent)) {
                driver.switchTo().window(win);
            }
        }
    }

    public void selectTopDealsDropdown(String value) {
        WebElement dropDown = wait.until(
            ExpectedConditions.visibilityOfElementLocated(dealsDropdown));

        new Select(dropDown).selectByVisibleText(value);
    }
}