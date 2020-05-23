package DriverUtil;



import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.*;
import java.io.*;



public class WebDriverUtil {

    public static WebDriver Webdriver() throws Exception {

        Properties properties =new Properties();
        properties.load(new FileInputStream("Framework.properties"));
        String browser = properties.get("Browser").toString();
        WebDriver driver = null;


        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            System.out.println("Browser selected for testing is :  firefox");
        } else if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized"); // open Browser in maximized mode
            options.addArguments("disable-infobars"); // disabling infobars
            options.addArguments("--disable-extensions"); // disabling extensions
            options.addArguments("--disable-gpu"); // applicable to windows os only
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("--no-sandbox"); // Bypass OS security model
            driver = new ChromeDriver(options);
            System.out.println("Browser selected for testing is :  Google Chrome");
        } else if (browser.equalsIgnoreCase("ie")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
            System.out.println("Browser selected for testing is :  Internet Explorer");
        }else if(browser.equalsIgnoreCase("edge")){
            WebDriverManager.edgedriver().setup();
            driver = new InternetExplorerDriver();
            System.out.println("Browser selected for testing is : edge");
        }
        else {
            System.out.println("Selected browser value should be either firefox or chrome or ie --> Update in Configuration file is required");
            System.exit(0);
        }

        return driver;

    }

}