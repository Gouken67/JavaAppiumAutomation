package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject (AppiumDriver driver) {
        this.driver = driver;
    }

    public void assertElementPresent(String locator, String error_message) {
        By by = this.getLocatorByString(locator);
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements == 0) {
            String default_message = "An element '" + locator + "' suppose to be present.";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(String locator, String error_message) {
        return waitForElementPresent(locator, error_message, 60);
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementThenClearAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        while (!element.getText().isEmpty()){
            TouchAction action = new TouchAction(driver);
            action.longPress(element);
            driver.getKeyboard().sendKeys(Keys.DELETE);
        }
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void assertSearch(String locator, String expected_text) {
        WebElement element = waitForElementPresent(
                locator,
                "There's no expected element with text " + expected_text,
                60);
        String search_text = element.getAttribute("text");
        Assert.assertEquals(
                "There's no expected text",
                expected_text,
                search_text
        );
    }

    public void assertThatAllElementsContainText(String locator, String looking_text) {
        By by = this.getLocatorByString(locator);
        List<WebElement> articles = driver.findElements(by);
        int size = driver.findElements(by).size();
        int i;
        for (i = 0; i < size; i++) {
            String new_element_text = articles.get(i).getAttribute("text");
            Assert.assertTrue(
                    "There's no "+ looking_text +" in the block #" + (i + 1),
                    new_element_text.contains(looking_text));
        }
    }

    public void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int)(size.height * 0.8);
        int end_y = (int)(size.height * 0.2);

        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    public   void  swipeUpQuick(){
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String error_message, int max_swipes){
        By by = this.getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            already_swiped++;
        }
    }

    public void  swipeElementToLeft(String locator, String error_message) {
        WebElement element = waitForElementPresent(
                locator,
                error_message
        );

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator, String error_message) {
        By by = this.getLocatorByString(locator);
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + locator + "' suppose to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private By getLocatorByString(String locator_with_type){
        String[] exploded_locator =locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if(by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if(by_type.equals("id")) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
        }
    }
}
