package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class StartPageObject extends MainPageObject{
    protected static  String
    START_SKIP_BUTTON;
    public StartPageObject (RemoteWebDriver driver){
        super(driver);
    }
    public void skipOnboardingButton() {
        if (Platform.getInstance().isIOS()) {
            this.waitForElementAndClick(START_SKIP_BUTTON, "Cannot find and click skip button", 5);
        } else if (Platform.getInstance().isAndroid()){
            this.waitForElementAndClick(START_SKIP_BUTTON, "Cannot find and click skip button", 5);
        } else {
            System.out.println("Method skipOnboardingButton() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
        }

}
