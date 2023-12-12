package lib.ui.ios;

import lib.ui.SavedArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSSavedArticlePageObject extends SavedArticlePageObject {
    static {
        SAVED = "xpath://XCUIElementTypeButton[@name='Сохранено']";
        EXPLORE = "xpath://XCUIElementTypeButton[@name=Лента']";
        FIELD_NAME_FOR_LIST = "id:org.wikipedia:id/item_title";
        ARTICLE_IN_LUST_RESULT_BY_SUBSTRING_TPL = "xpath://android.widget.TextView[@text='{SUBSTRING}']";
        ITEM_TITLE = "id:org.wikipedia:id/item_title";
        CLOSE_SINCH_X_BUTTON = "xpath://XCUIElementTypeButton[@name='Закрыть']";
        DELETE_BUTTON_ON_SAVED_PAGE = "xpath://XCUIElementTypeButton[@name='swipe action delete']";
    }
    public iOSSavedArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }
}
