package initializers;

import DriverUtil.WebDriverUtil;
import Utilities.pageObject;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class InitializerHook {

    @Before
    public void initializePageObject() throws Exception {
        WebDriverUtil.InitializeWebdriver();
        pageObject pageObject =new pageObject("src/test/resources/PageObjects");
        pageObject.initializePO();
    }

    @After
    public void killProcess() throws Exception {
        WebDriverUtil.Webdriver().quit();
    }
}
