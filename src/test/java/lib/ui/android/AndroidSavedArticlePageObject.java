package lib.ui.android;

import lib.ui.SavedArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidSavedArticlePageObject extends SavedArticlePageObject {
    static {
        SAVED = "id:org.wikipedia:id/nav_tab_reading_lists";
        EXPLORE = "id:org.wikipedia:id/nav_tab_explore";
        MENU_BUTTON = "id:org.wikipedia:id/menu_overflow_button";
        CRATE_NEW_LIST_BUTTON = "id:org.wikipedia:id/reading_lists_overflow_create_new_list";
        INPUT_FIELD_FOR_NEW_LIST = "id:org.wikipedia:id/text_input";
        FIELD_NAME_FOR_LIST = "id:org.wikipedia:id/item_title";
        OK_BUTTON = "id:android:id/button1";
        ARTICLE_IN_LUST_RESULT_BY_SUBSTRING_TPL = "xpath://android.widget.TextView[@text='{SUBSTRING}']";
        ITEM_TITLE = "id:org.wikipedia:id/item_title";

    }
    public AndroidSavedArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }
}
