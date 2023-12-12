package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class  AuthorizationPageObject  extends MainPageObject {
    protected static  String
            LOGIN_BUTTON,
            LOGIN_INPUT,
            PASSWORD_INPUT,
            SUBMIT_BUTTON;
    public AuthorizationPageObject(RemoteWebDriver driver){
        super(driver);
    }

    public void clickAuthButton(){
        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find login button", 5);
        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click login button", 5);
    }

    public void enterLoginData(String login, String password){
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find and put a login to the login input",5 );
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cannot find and put a password to the password input",5 );
    }

    public void submitForm(){
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit auth button",5 );
    }

    public void checksubmitForm(){
        boolean element = this.waitForElementNotPresent(SUBMIT_BUTTON, "Find submit auth button",5 );
    }
}

