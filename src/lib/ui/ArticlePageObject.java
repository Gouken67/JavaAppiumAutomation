package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
        TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "//*[@text='OK']",
        MY_LIST_FOLDER = "org.wikipedia:id/item_container",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(
                By.id(TITLE),
                "Cannot find article title on page!",
                60
        );
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToMyList(String name_of_folder){
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                60
        );
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add articleto reading list",
                60
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay",
                60
        );

        this.waitForElementThenClearAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot find input to set name of articles folder",
                60
        );

        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press OK button",
                60
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X link",
                60
        );
    }

    public void addArticleToAlreadyCreatedFolder(String name_of_folder){
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                60
        );
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                60
        );

        this.waitForElementAndClick(
                By.id(MY_LIST_FOLDER),
                "Cannot find the folder " + name_of_folder,
                60
        );
    }

    public void assertThatTitleIsOnTheScreen() {
        this.assertElementPresent(
                By.id(TITLE),
                "There's no title for this article!"
        );
    }
}
