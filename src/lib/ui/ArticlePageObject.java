package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
        TITLE = "id:org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
        OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
        MY_LIST_FOLDER = "id:org.wikipedia:id/item_container",
        CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(
                TITLE,
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
                FOOTER_ELEMENT,
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToMyList(String name_of_folder){
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                60
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add articleto reading list",
                60
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                60
        );

        this.waitForElementThenClearAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot find input to set name of articles folder",
                60
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                60
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                60
        );
    }

    public void addArticleToAlreadyCreatedFolder(String name_of_folder){
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                60
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                60
        );

        this.waitForElementAndClick(
                MY_LIST_FOLDER,
                "Cannot find the folder " + name_of_folder,
                60
        );
    }

    public void assertThatTitleIsOnTheScreen() {
        this.assertElementPresent(
                TITLE,
                "There's no title for this article!"
        );
    }
}
