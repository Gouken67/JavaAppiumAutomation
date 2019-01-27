package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT;

    public SearchPageObject (AppiumDriver driver) {
        super(driver);
    }

    /*TEMPLATE METHODS*/
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchByTitleAndDescription(String substring) {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{SUBSTRING}", substring);
    }
    /*TEMPLATE METHODS*/

    public void initSearchInput(){
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                60
        );
        this.waitForElementPresent(
                SEARCH_INPUT,
                "Cannot find search input after clicking search init element"
        );
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button",
                60
        );
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                60
        );
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                60
        );
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input",
                60
                );
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
          search_result_xpath,
          "Cannot find search result with substring " + substring
        );
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                60
        );
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by request",
                60
        );

        return this.getAmountOfElements(
                SEARCH_RESULT_ELEMENT
        );
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty results label",
                60
        );
    }

    public void assertThereIsNoResultsOfSearch(){
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We suppose not to find any results"
        );
    }

    public void waitForSearchResultToDisappear(String search_result) {
        String search_result_xpath = getResultSearchElement(search_result);
        this.waitForElementNotPresent(
                search_result_xpath,
                "The articles is still on the page",
                60
        );
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String search_result_by_title_xpath = getResultSearchByTitleAndDescription(title);
        String search_result_by_description_xpath = getResultSearchByTitleAndDescription(description);

        this.waitForElementPresent(
                search_result_by_title_xpath,
                "The article with such title doesn't present in the search results",
                60
        );

        this.waitForElementPresent(
                search_result_by_description_xpath,
                "The article with such description doesn't present in the search results",
                60
        );
    }
}
