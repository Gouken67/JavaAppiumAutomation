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
    public void testCancelSearchAndAssertThatTheresNoArticles() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search String",
                60
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Spider-man",
                "Cannot find search input",
                60
        );

        MainPageObject.assertSearch(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='2017 superhero film produced by Marvel Studios and Columbia Pictures']"),
                "2017 superhero film produced by Marvel Studios and Columbia Pictures"
        );

        MainPageObject.assertSearch(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='2018 American 3D computer-animated superhero film']"),
                "2018 American 3D computer-animated superhero film"
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel Search",
                60
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='2018 American 3D computer-animated superhero film']"),
                "The articles is still on the page",
                60
        );
    }

    @Test
    public void testThatSearchResultsContainSearchString() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search String",
                60
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Steins;Gate",
                "Cannot find search input",
                60
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find topic searching by 'Steins;Gate'"
        );

        MainPageObject.assertThatAllElementsContainText(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Steins;Gate"
        );
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search String",
                60
        );

        String search_line = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find first article by request " + search_line,
                60
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                60
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                60
        );

        String name_of_folder = "Learning programming";
        MainPageObject.waitForElementThenClearAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search String",
                60
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Set of several computer software products and specifications']"),
                "Cannot find second article by request " + search_line,
                60
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                60
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/item_container"),
                "Cannot find the folder " + name_of_folder,
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                60
        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                60
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Java (software platform)']"),
                "Cannot find the rest article",
                60
        );

        String title_before_entering = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@text='Java (software platform)']"),
                "text",
                "Cannot find the rest article",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Java (software platform)']"),
                "Cannot find the rest article",
                60
        );

        String title_after_entering = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "There's no title for the article",
                60
        );

        assertEquals(
                "Titles of opened article doesn't match",
                title_before_entering,
                title_after_entering
        );
    }

    @Test
    public void testForTitlePresent() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search String",
                60
        );

        String search_line = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                60
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find first article by request " + search_line,
                60
        );

        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "There's no title for this article!"
        );
    }
}