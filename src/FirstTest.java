import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.*;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }



    @Test
    public void testThatSearchResultsContainSearchString() {
        MainPageObject.waitForElementAndClick(
                "xpath://*[contains(@text, 'Search Wikipedia')]",
                "Cannot find Search String",
                60
        );

        MainPageObject.waitForElementAndSendKeys(
                "xpath://*[contains(@text, 'Search…')]",
                "Steins;Gate",
                "Cannot find search input",
                60
        );

        MainPageObject.waitForElementPresent(
                "id:org.wikipedia:id/page_list_item_title",
                "Cannot find topic searching by 'Steins;Gate'"
        );

        MainPageObject.assertThatAllElementsContainText(
                "id:org.wikipedia:id/page_list_item_title",
                "Steins;Gate"
        );
    }


}