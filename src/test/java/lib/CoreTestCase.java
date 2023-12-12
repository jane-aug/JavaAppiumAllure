package lib;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Properties;

public class CoreTestCase  {
    protected RemoteWebDriver driver;
    @Before
    @Step("Run driver and session")
    public void setUp() throws Exception
    {
        driver = Platform.getInstance().getDriver();
        this.createAllureProperty();
        this.rotateScreenPortraint();
        this.openWikiWebPageForMobileWeb();
    }

     @After
     @Step("Remove driver and session")

     public void tearDown()
    {
        driver.quit();
    }

    @Step("Rotate sccreen to the portaint mode")
    protected void rotateScreenPortraint() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            System.out.println("Method rotateScreenPortraint() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
    protected void rotateScreenLandsCape(){
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        }
        else {
            System.out.println("Method rotateScreenLandsCape() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void backgroundApp(Duration seconds){
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(seconds);
        }
        else {
            System.out.println("Method backgroundApp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Open Wikipedia URL for mobile web (skip for ios and android)")
    protected void openWikiWebPageForMobileWeb(){
        if(Platform.getInstance().isMW()){
            driver.get("https://en.m.wikipedia.org");
        } else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform " + Platform.getInstance().getPlatformVar());

        }
    }

    public void createAllureProperty() {
        String path = System.getProperty("allure.results.directory");
            try{
        Properties props = new Properties();
        FileOutputStream fos = new FileOutputStream(path +"/environment.properties");
        props.setProperty("Environment", Platform.getInstance().getPlatformVar());
        props.store(fos, "See https://allurereport.org/docs/#_environment");
        fos.close();
        } catch (Exception e) {
            System.err.println("IO problem when writing allure properties file");
            e.printStackTrace();
        }

    }


}
