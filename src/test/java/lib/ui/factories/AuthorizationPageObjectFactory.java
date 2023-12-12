package lib.ui.factories;

import lib.Platform;
import lib.ui.AuthorizationPageObject;
import lib.ui.mobile_web.MWAuthorizationPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObjectFactory {
    public static AuthorizationPageObject get(RemoteWebDriver driver) {
        if (Platform.getInstance().isMW()) {
            return new MWAuthorizationPageObject(driver);
        } else {
            System.out.println("Autorization now is now work for platform " + Platform.getInstance().getPlatformVar());
          return new MWAuthorizationPageObject(driver); //заглушка
        }
    }
}
