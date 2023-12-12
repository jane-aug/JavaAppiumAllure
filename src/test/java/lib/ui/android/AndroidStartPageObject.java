package lib.ui.android;

import lib.ui.StartPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidStartPageObject extends StartPageObject {

    static {
        START_SKIP_BUTTON = "id:org.wikipedia:id/fragment_onboarding_skip_button";
    }
    public AndroidStartPageObject(RemoteWebDriver driver){
        super(driver);
    }
}
