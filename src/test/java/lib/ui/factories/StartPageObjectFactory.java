package lib.ui.factories;

import lib.Platform;
import lib.ui.StartPageObject;
import lib.ui.android.AndroidStartPageObject;
import lib.ui.ios.iOSStartPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class StartPageObjectFactory {

    public static StartPageObject get(RemoteWebDriver driver)
    {
        if(Platform.getInstance().isAndroid()){
            return new AndroidStartPageObject(driver);
        } else {
            return new iOSStartPageObject(driver);
        }
    }
}
