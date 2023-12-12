package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        SAVE_MENU = "xpath://ul[contains(@id, 'p-views')]/li[contains(@id, 'page-actions-watch')]/a";
        // FIRST_LIST = "id:org.wikipedia:id/item_title_container";
        ARTICLE_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/reading_list_recycler_view']//*[@text='{SUBSTRING}']";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:#page-actions-menu li#ca-watch button"; //в уроке другая
    }
    public MWArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }

}
