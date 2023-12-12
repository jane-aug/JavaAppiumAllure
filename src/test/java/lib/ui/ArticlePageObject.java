package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class ArticlePageObject extends MainPageObject{

    protected static  String
            TITLE ,
            FOOTER_ELEMENT,
            MENU_BUTTON ,
            CUSTOMIZE_BUTTON ,
            SAVE_MENU_IN_SETTINGS ,
            UNDO_BUTTON,
            SAVE_MENU ,
            SNACKBAR_ACTION ,
            FIRST_LIST ,
            ARTICLE_RESULT_BY_SUBSTRING_TPL,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }
    private static String getResultListElement(String subString){
        return ARTICLE_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", subString);
    }
    /*public void waitForSearchResult(String subString){
        String list_result_xpath = getResultListElement(subString);
        this.waitForElementPresent(By.xpath(list_result_xpath),"Cannot find search result with substring" + subString,5);
    }*/
    @Step("Ожидаем название статьи")
    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE,"Cannot find article title", 10);
    }

    @Step("Получаем назваание статьи")
    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        screenshot(this.takeScreenShot("aricle_title"));
        if (Platform.getInstance().isAndroid()) {
        return  title_element.getAttribute("text");}
        else if (Platform.getInstance().isIOS()){
            return  title_element.getAttribute("name");}
        else {
            return title_element.getText();
        }
    }

    @Step("Проверка названия статьи")
    public void assertArticleTitlePresent(){
        if (Platform.getInstance().isMW()) {
            waitForElementPresent(TITLE,"Cannot find article title "+ TITLE, 10);
        }
        else {
            List<WebElement> elements = driver.findElements(By.id(TITLE));
            if (elements.isEmpty()) {
                throw new AssertionError("Article name is did not loaded");
            }
        }
    }

    @Step("Скролл до футера")
    public void swipeToFooter(){
        if (Platform.getInstance().isAndroid()){
        this.swipeUpToFindElement(FOOTER_ELEMENT,"Did not find footer on Android", 40);}
        else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT, "Did not find footer on iOS", 40);
        } else {
            this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT,"Did not find footer on web page", 40);
        }
    }

    @Step("Открываем меню на странице статьи")
    public void openMenu(){
        this.waitForElementAndClick(MENU_BUTTON, "Cannot find menu_overflow_button", 5);
    }


    @Step("Меняем расположение кнопки сохранить")
    public void dragAndDrop(){
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver)driver);
            // action.longPress(999, 627).moveTo(9, 1640).release().perform(); // поднятие версии
            action.longPress(PointOption.point(999, 627)).moveTo(PointOption.point(999, 1640)).release().perform();
        }
        else {
            System.out.println("Method dragAndDrop() does nothing for platform " + Platform.getInstance().getPlatformVar());

        }
    }


    @Step("Заходим в меню и меняем кнопку сохранить")
    public void changeMenu(){
        openMenu();
        this.waitForElementAndClick(CUSTOMIZE_BUTTON,"Cannot find customize_toolbar",5);
        this.waitForElementPresent(SAVE_MENU_IN_SETTINGS,"Cannot find save menu", 5);
        dragAndDrop();
        driver.navigate().back();
    }
    @Step("Сохраняем статью")
    public void saveArticle(){
        if (Platform.getInstance().isMW()){
            removeArticleFromSavedIfItAdded();
        } else {
            System.out.println("Method removeArticleFromSavedIfItAdded(); does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
        if (Platform.getInstance().isAndroid()) {
            openMenu();
        } else if (Platform.getInstance().isMW()) {
            waitForElementAndClick(SAVE_MENU,"Cannot find save button on web page",5);
        } else this.waitForElementAndClick(SAVE_MENU,"Cannot find save button in list",5);

    }

    @Step("Удалем статью из сохраненных")
    public void removeArticleFromSavedIfItAdded () {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from save list",
                    5);
            this.waitForElementPresent(
                    SAVE_MENU,
                    "Cannot find button to add an article to saved list after removing it from this list before",
                    5);
        }

    }
}
