package Utilities;


import DriverUtil.WebDriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class pageObject {
    private static LinkedHashMap<String, LinkedHashMap> PoMap;
    private String pageObject;

    public pageObject(String pageObject) {
        this.pageObject = pageObject;
    }

    public static WebElement GetElement(String element) throws Exception {
        LinkedHashMap<String,LinkedHashMap> pageMap = GlabalDataParams.PAGE_OBJECT.get(element.split("\\.")[0]);
        LinkedHashMap elementMap = pageMap.get(element.split("\\.")[1]);
        return WebDriverUtil.Webdriver().findElement(attributes(elementMap.get("accessType").toString(), elementMap.get("accessValue").toString()));
    }

    public static By attributes(String attribute, String attributevalue) {
        switch (attribute.toLowerCase()) {
            case "id":
                return By.id(attributevalue);
            case "name":
                return By.name(attributevalue);
            case "class":
                return By.className(attributevalue);
            case "xpath":
                return By.xpath(attributevalue);
            case "css":
                return By.cssSelector(attributevalue);
            case "linktext":
                return By.linkText(attributevalue);
            case "tagname":
                return By.tagName(attributevalue);
            default:
                return null;
        }
    }

    public void initializePO() {
        File folder = new File(pageObject);
        File[] listOfFiles = folder.listFiles();
        Arrays.asList(listOfFiles).forEach(file ->{
            GlabalDataParams.PAGE_OBJECT.put(file.getName().split("\\.")[0], new YAMLReader(file.toString()).GetDataMap());
        });
    }
}