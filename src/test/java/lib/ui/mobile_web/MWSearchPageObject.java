package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_INPUT_ON_PAGE = "css:button#searchIcon]";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class,'wikidata-description')][contains(text(),'{SUBSTRING}')]";
        SEARCH_UNDO_BUTTON = "css:";
        PLACEHOLDER_SEARCH_LINE = "Поиск по Википедии";
        SEARCH_RESULT_LOCATOR = "css:ul.page-list>li.page-summary";
        EMPTY_RESULT_LABEL = "css:p.without-results";
        CANCEL_BUTTON_IN_SEARCH_LINE = "css:button.cancel";
        PLACEHOLDER_SEARCH_LINE_EN = "search";
        WELCOME_TEXT = "xpath://*[@id='Welcome_to_Wikipedia']";

    }
    public MWSearchPageObject(RemoteWebDriver driver){
        super(driver);
    }
}

