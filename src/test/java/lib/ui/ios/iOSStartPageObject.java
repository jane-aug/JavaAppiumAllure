package lib.ui.ios;

import lib.ui.StartPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSStartPageObject extends StartPageObject
{

    static {
        START_SKIP_BUTTON = "xpath://XCUIElementTypeButton[@name='Пропустить']";
    }
    public iOSStartPageObject(RemoteWebDriver driver){
        super(driver);
    }

}
