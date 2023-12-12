package lib.ui.mobile_web;

import lib.ui.AuthorizationPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWAuthorizationPageObject extends AuthorizationPageObject {
    static {
        //LOGIN_BUTTON = "xpath://div/a[contains(@type, 'button')]";
        LOGIN_BUTTON = "xpath://div[@class='drawer drawer-container__drawer position-fixed visible']/a[contains(@type, 'button')]" ;
        LOGIN_INPUT = "css:input[name ='wpName']";
        PASSWORD_INPUT = "css:input[name ='wpPassword']";
        SUBMIT_BUTTON = "css:button[name ='wploginattempt']";

        //div[contains(@class, 'drawer')][contains(text(),'Log in']

    }

    public MWAuthorizationPageObject(RemoteWebDriver driver){
        super(driver);
    }
}
