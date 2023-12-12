package lib.ui;

import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SavedArticlePageObject extends MainPageObject{
    protected static  String
            SAVED,
            EXPLORE,
            MENU_BUTTON ,
            CRATE_NEW_LIST_BUTTON ,
            INPUT_FIELD_FOR_NEW_LIST,
            FIELD_NAME_FOR_LIST ,
            OK_BUTTON ,
            ARTICLE_IN_LUST_RESULT_BY_SUBSTRING_TPL,
            ITEM_TITLE,
            CLOSE_SINCH_X_BUTTON,
            REMOVE_FROM_SAVED_BUTTON,
            DELETE_BUTTON_ON_SAVED_PAGE;




    public SavedArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }

    private static String getResultArticleElement(String subString){
        return ARTICLE_IN_LUST_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", subString);
    }
    private static String getRemoveButtonByTitle(String article_title){
        return REMOVE_FROM_SAVED_BUTTON.replace("TITLE", article_title);
    }

    public void clickByArticleInListWithSubstring(String subString){
        String search_result_id = getResultArticleElement(subString);
        this.waitForElementAndClick(search_result_id,"Cannot find and click in article list result with substring " + subString,10);
    }

    public  void clickByArticleInList(){
        this.waitForElementAndClick(ITEM_TITLE,"Cannot find and click article on list result!",5);
       // this.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/item_title']"),"Cannot find and click article on list result!",5);

    }

    public void getSavedAriclePage(){
        this.waitForElementAndClick(SAVED, "Cannot find nav_tab_reading_lists",5);
    }

    public void openMenu(){
        this.waitForElementAndClick(MENU_BUTTON,"Cannot find menu_overflow_button", 5);
    }

    public void createNewList(String nameForList ){
        openMenu();
        this.waitForElementAndClick(CRATE_NEW_LIST_BUTTON,"Cannot find create_new_list", 5);
        this.waitForElementAndSendKeys(INPUT_FIELD_FOR_NEW_LIST, nameForList ,"Cannot find text input",5 );
        this.waitForElementAndClick(OK_BUTTON,"Cannot find OK button",5);
        this.assertElementHasText(FIELD_NAME_FOR_LIST, nameForList, "Cannot find new list" + nameForList,5);
        this.waitForElementAndClick(EXPLORE, "Cannot find explore", 5);

    }

    public void closeSinchXButton (){
        waitForElementAndClick(CLOSE_SINCH_X_BUTTON,"Cannont find and click X button",5);
    }
    public void deleteArticleOnSavedPage(){
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
        waitForElementAndClick(DELETE_BUTTON_ON_SAVED_PAGE,"Cannont find and click delete button",5);
    } else {
            waitForElementAndClick("xpath://div[@id='mw-content-text']/ul/li[@title='Appium']/a[contains(@class, 'watch-this-article watched')]","Cannont find and click delete button",5);


//            String remove_locator = getRemoveButtonByTitle(article_title);
//            this.waitForElementAndClick(
//                    remove_locator,
//                    "Cannot click button to remove article from saved",
//                    10
//            );

        }
    }

    public WebElement assertSavedArticleHasNotText(String loccator, String value, String error_massage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(loccator, error_massage, timeoutInSeconds);
        String element_name;
        element_name = element.getAttribute("title");
        System.out.println(element_name);
        Assert.assertNotEquals(
                error_massage,
                value,
                element_name

        );
        return element;
    }
    public WebElement assertSavedArticleHasText(String loccator, String value, String error_massage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(loccator, error_massage, timeoutInSeconds);
        String element_name;
        element_name = element.getAttribute("title");
        System.out.println(element_name);
        Assert.assertEquals(
                error_massage,
                value,
                element_name

        );
        return element;
    }

}
