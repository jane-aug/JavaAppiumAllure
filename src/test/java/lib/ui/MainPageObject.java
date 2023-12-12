package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Attachment;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class MainPageObject {
    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    //Общий метод для поиска элемента с передачей времени для таймаута
    public WebElement waitForElementPresent(String loccator, String error_massage, long timeoutInSeconds)
    {
        By by = this.getLocatorByString(loccator); //определение локатора
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_massage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    //Общий метод для поиска элемента  с таймаутом 5 секунд
    public WebElement waitForElementPresent(String loccator, String error_massage)
    {
        return this.waitForElementPresent(loccator, error_massage, 5);
    }

    //Общий метод для поиска элемента с передачей времени для таймаута и клика
    public WebElement waitForElementAndClick(String loccator, String error_massage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(loccator,error_massage, timeoutInSeconds);
        element.click();
        return element;
    }


    //Общий метод для поиска элемента с передачей времени для таймаута и отправки ключей
    public WebElement waitForElementAndSendKeys(String loccator, String value, String error_massage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(loccator,error_massage, timeoutInSeconds);
        element.clear();
        element.sendKeys(value);
        return element;
    };


    //Общий метод проверяющий отсутствие элемента на странице
    public boolean waitForElementNotPresent(String loccator, String error_massage, long timeoutInSeconds) {
        By by = this.getLocatorByString(loccator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_massage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
    //  Метод ожидания элемента и его очистка
    public WebElement waitForElementAndClear(String loccator, String error_massage, long timeoutInSeconds)
    {
        WebElement element =  waitForElementPresent(loccator,error_massage, timeoutInSeconds);
        element.clear();
        return element;
    }

    //Ex 2
    public WebElement assertElementHasText(String loccator, String value, String error_massage, long timeoutInSeconds){
        WebElement element =  waitForElementPresent(loccator,error_massage, timeoutInSeconds);
        String element_name;
        if (Platform.getInstance().isAndroid()){
        element_name= element.getAttribute("text");
            System.out.println(element_name);
        }
        else if (Platform.getInstance().isIOS()){
            element_name = element.getAttribute("name");
            System.out.println(element_name);
        } else {
            element_name = element.getAttribute("placeholder");
            System.out.println(element_name);
        }
        Assert.assertEquals(
                error_massage,
                value,
                element_name

        );
        return element;
    }
    public WebElement assertElementHasNotText(String loccator, String value, String error_massage, long timeoutInSeconds){
        WebElement element =  waitForElementPresent(loccator,error_massage, timeoutInSeconds);
        String element_name;
        if (Platform.getInstance().isAndroid()){
            element_name = element.getAttribute("text");
            System.out.println(element_name);}
        else {
            element_name = element.getAttribute("name");
            System.out.println(element_name);}
        Assert.assertNotEquals(
                error_massage,
                value,
                element_name

        );
        return element;
    }

    // ex 3
    public List quantityElements(String loccator) {
        By by = this.getLocatorByString(loccator);
        List elements_search = driver.findElements(by);
        return elements_search;
    }

    // метод свайп по относительным координатам
    public void swipeUp(int timeOfSwipe){
        if (driver instanceof AppiumDriver){

            TouchAction action = new TouchAction((AppiumDriver)driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);

            //action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform(); // поднятие версии

            action.press(PointOption.point(x, start_y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe))).moveTo(PointOption.point(x, end_y)).release().perform();
        } else {
            System.out.println("Method swipeUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
   }

    public void swipeUpQuick(){
        swipeUp(200);
    }

    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor  = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0,250)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void scrollWebPageTillElementNotVisible (String loccator, String error_massage, int max_swipes){
        int already_swiped= 0;
        WebElement element = this.waitForElementPresent(loccator, error_massage);
        while (!this.isElementLocatedOnTheScreen(loccator)) {
            scrollWebPageUp();
            ++already_swiped;
            if (already_swiped > max_swipes) {
                Assert.assertTrue(error_massage, element.isDisplayed());
            }
        }
    }

    //метод свайпа v2

    public void verticalSwipeToBottom(){
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.70);
        int endY = (int) (size.height * 0.30);
        int centerX = size.width / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
        Sequence swipe = new Sequence(finger,1);

        //Двигаем палец на начальную позицию
        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0),
                PointerInput.Origin.viewport(),centerX,(int)startY));
        //Палец прикасается к экрану
        swipe.addAction(finger.createPointerDown(0));

        //Палец двигается к конечной точке
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
                PointerInput.Origin.viewport(),centerX,(int)endY));

        //Убираем палец с экрана
        swipe.addAction(finger.createPointerUp(0));

        //Выполняем действия
        driver.perform(Arrays.asList(swipe));
    }

    public void swipeUpToFindElement (String loccator, String error_massage, int max_swipes){
        By by = this.getLocatorByString(loccator); //определение локатора
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if(already_swiped > max_swipes){
                waitForElementPresent(loccator, "Cannot find element by swiping up. \n"+ error_massage, 0);
                return;
            }
            verticalSwipeToBottom();
            ++already_swiped;
        }
    }

    public void swipeElementToLeft(String loccator, String  error_massage){
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(loccator, error_massage, 10);
            int left_x = element.getLocation().getX();
            int right_x = left_x + element.getSize().getWidth();

            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;

            TouchAction action = new TouchAction((AppiumDriver)driver);
            // action.press(right_x,middle_y).waitAction(300).moveTo(left_x,middle_y).release().perform(); поднятие версии
            action
                    .press(PointOption.point(right_x, middle_y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                    .release()
                    .perform();
        }
        else {
            System.out.println("Method swipeElementToLeft() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

        // рефактор свайпа проблема с Map.of
    public void swipeElementToLeftv2 (String loсcator, String error_message) {
        RemoteWebElement carousel = (RemoteWebElement) waitForElementPresent(
                loсcator,
                error_message,
                10);
      //  driver.executeScript("gesture: swipe", Map.of("elementId", carousel.getId(), "percentage", 50, "direction", "left"));


    }

    public void swipeElementToLeftv3(String locator, String errorMessage) {
        WebElement element = waitForElementPresent(
                locator,
                errorMessage,
                10);
        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY)/2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
        Sequence swipe = new Sequence(finger,1);
        //Двигаем палец на начальную позицию
        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0),
                        PointerInput.Origin.viewport(),rightX -10,(int)middleY))
                //Палец прикасается к экрану
                .addAction(finger.createPointerDown(0))
                //Палец двигается к конечной точке
                .addAction(finger.createPointerMove(Duration.ofMillis(700),
                        PointerInput.Origin.viewport(),leftX + 10,(int)middleY))
                //Убираем палец с экрана
                .addAction(finger.createPointerUp(0));
        driver.perform(Arrays.asList(swipe));
    }



    public void tuochByCoordinate(int x,int y){
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver)driver);
            // action.press(x,y).release().perform(); // поднятие версии
            action.press(PointOption.point(x, y));
            action.release();
            action.perform();
        } else {
            System.out.println("Method tuochByCoordinate() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    // определение типа локатора
    public By getLocatorByString(String locator_with_type){
        String[] exploded_loccator;
        exploded_loccator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_loccator[0];
        String locator =exploded_loccator[1];

        if (by_type.equals("xpath")){
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else if (by_type.equals("resource-id")) {
            return By.id(locator);
        } else if (by_type.equals("css")) {
            return By.cssSelector(locator);
        }
        else {
            throw new IllegalArgumentException("Cannot get type of loccator. Loccator "+ locator_with_type);
        }
    }

// свайпаем пока не найдем элемент
    public void swipeUpTillElementAppear(String locator, String error_massage, int max_swipes){
        int already_swiped = 0;
        while (!this.isElementLocatedOnTheScreen(locator)) {
            if(already_swiped > max_swipes){
                Assert.assertTrue(error_massage,this.isElementLocatedOnTheScreen(locator));
            }
            verticalSwipeToBottom();
            ++already_swiped;
        }
    }
    // определяем есть ли элемент на экрране ( виден ли)
    public boolean isElementLocatedOnTheScreen(String locator)
    {
        int element_location_by_y = this.waitForElementPresent(locator, "Cannot find element by locator", 5).getLocation().getY();
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            element_location_by_y-= Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;
    }


    public  int getAmountOfElements(String loccator){
        By by = this.getLocatorByString(loccator);
        List elements = driver.findElements(by);
        return elements.size();
    }
    public boolean isElementPresent(String loccator) {
        return  getAmountOfElements(loccator) > 0;
    }

    public void tryClickElementWithFewAttempts (String loccator, String error_message, int amount_of_attempts){
        int current_attempts = 0;
        boolean need_more_attempts = true;

        while (need_more_attempts) {
            try {
                this.waitForElementAndClick(loccator, error_message, 3);
                need_more_attempts = false;
            } catch (Exception e) {
                if (current_attempts > amount_of_attempts) {
                    this.waitForElementAndClick(loccator, error_message, 3);
                }
            }

            ++ current_attempts;
        }
    }

    public String takeScreenShot (String name) {
        TakesScreenshot ts = (TakesScreenshot)this.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("The screenshot was taken: " + path);
        } catch (Exception e) {
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }

    @Attachment
    public static byte[] screenshot(String path){
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println( "Cannot get bytes from screenshot Error: " + e.getMessage());
        }
        return bytes;
    }
}
