package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.*;
import lib.ui.mobile_web.MWSavedArticlePageObject;
import org.junit.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyListTest extends CoreTestCase
{
    private  static final String login = "Shalanova";
    private  static final String password = "Mem6rana1";
    @Test
    public void testEx5() throws InterruptedException {
        // сохраняем 2 статьи в одну папку, 1 удаляем и проверяем что осталась вторая по названию
        MainPageObject MainPageObject = new MainPageObject(driver);
        StartPageObject StartPageObject = StartPageObjectFactory.get(driver);
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        SavedArticlePageObject SavedArticlePageObject = SavedArticlePageObjectFactory.get(driver);
        AuthorizationPageObject AuthorizationPageObject = AuthorizationPageObjectFactory.get(driver);



        StartPageObject.skipOnboardingButton();

        //Авторизация для сайта
        if (Platform.getInstance().isMW()) {
            //Авторизация для сайта
            driver.get("https://en.m.wikipedia.org/w/index.php?title=Special:UserLogin&returnto=Main+Page");
            AuthorizationPageObject.enterLoginData(login, password);
            Thread.sleep(2000);
            AuthorizationPageObject.submitForm();
        } else {
            System.out.println("Skip authorization for mobile");
        }

        // 1 статья
        SearchPageObject.initSearchInput();

        SearchPageObject.typeSearchLine("Java");
        if (Platform.getInstance().isIOS()) {
            SearchPageObject.clickByArticleWithSubstring("зык программирования");
        } else {
            SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
            Thread.sleep(3000);
        }
        if (Platform.getInstance().isAndroid()){ ArticlePageObject.changeMenu();}
        else {System.out.println("Skip changeMenu for iOS and web");}
         ArticlePageObject.saveArticle();
        Thread.sleep(2000);


        // 2 статья
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()){
            SearchPageObject.initSearchInput();}
        else {   driver.navigate().back();
            SearchPageObject.initSearchInput();}
        SearchPageObject.clearSearchText();
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()){
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Automation for Apps");
        Thread.sleep(2000);}
        else {
            SearchPageObject.typeSearchLine("Appium");
            SearchPageObject.clickByArticleWithSubstring("Вид растений");
        }
        ArticlePageObject.saveArticle();

        //удаляем одну из списка
        if (Platform.getInstance().isAndroid()) {
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
            SavedArticlePageObject.getSavedAriclePage();
        }
        else if (Platform.getInstance().isIOS()) {
            driver.navigate().back();
            SearchPageObject.cancelSearch();
            driver.navigate().back();
            driver.navigate().back();
            driver.navigate().back();
            SavedArticlePageObject.getSavedAriclePage();

        } else {
            driver.get("https://en.m.wikipedia.org/wiki/Special:EditWatchlist");
        }

        //SavedArticlePageObject.clickByArticleInListWithSubstring("Saved");
        if (Platform.getInstance().isAndroid()) {
            SavedArticlePageObject.clickByArticleInList();

            MainPageObject.swipeElementToLeft("xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']", "Cannot find saved article");
            MainPageObject.assertElementHasNotText("id:org.wikipedia:id/page_list_item_description", "Object-oriented programming language", "Cannot  delete article", 5);
            MainPageObject.assertElementHasText("id:org.wikipedia:id/page_list_item_description", "Automation for Apps", "Second article is still in list", 5);
        } else if (Platform.getInstance().isIOS()) {
            SavedArticlePageObject.closeSinchXButton();
            MainPageObject.swipeElementToLeftv3("xpath://XCUIElementTypeStaticText[@name='Вид растений']","Cannot find saved article");
            SavedArticlePageObject.deleteArticleOnSavedPage();
            MainPageObject.assertElementHasNotText("xpath://XCUIElementTypeStaticText","Вид растений","Cannot  delete article",5);
            MainPageObject.assertElementHasText("xpath://XCUIElementTypeStaticText[contains(@name,'Язык программирования')]","Язык программирования","Second article is still in list",5);

        }
        else {
            driver.navigate().refresh();
            Thread.sleep(2000);
            SavedArticlePageObject.deleteArticleOnSavedPage();
            Thread.sleep(2000);
            driver.navigate().refresh();
           SavedArticlePageObject.assertSavedArticleHasNotText(
                    "xpath://div[@id='mw-content-text']/ul/li[@title='Appium']",
                    "Appium",
                    "Cannot  delete article",
                    5);
            SavedArticlePageObject.assertSavedArticleHasText(
                    "xpath://div[@id='mw-content-text']/ul/li[@title='Java (programming language)']",
                    "Java (programming language)",
                    "Second article is still in list",
                    5);


        }

    }


    // создания списка
    @Test
    public void testCreateList() {
        StartPageObject StartPageObject = StartPageObjectFactory.get(driver);
        SavedArticlePageObject SavedArticlePageObject = SavedArticlePageObjectFactory.get(driver);

        StartPageObject.skipOnboardingButton();
        SavedArticlePageObject.getSavedAriclePage();
        SavedArticlePageObject.createNewList("First");
    }

    @Test
    public void testAuthForWeb() throws InterruptedException {
        AuthorizationPageObject AuthorizationPageObject = AuthorizationPageObjectFactory.get(driver);

        //Авторизация для сайта
            driver.get("https://en.m.wikipedia.org/w/index.php?title=Special:UserLogin&returnto=Main+Page");
            AuthorizationPageObject.enterLoginData(login, password);
            Thread.sleep(5000);
            AuthorizationPageObject.submitForm();
            AuthorizationPageObject.checksubmitForm();




    }
}
