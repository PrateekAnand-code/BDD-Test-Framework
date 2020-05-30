package Utilities;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.LinkedHashMap;

public  class pageObject {
    private String pageObject ;
    private String buff;

    WebDriver driver;

    public pageObject(WebDriver driver, String pageObject) {
        this.pageObject = pageObject;
        this.driver = driver;
    }

    public WebElement GetElement(String element){

        yamlReader pageobjectYaml =new yamlReader(pageObject);
        LinkedHashMap DataMap= pageobjectYaml.GetDataSet(element);


       return driver.findElement(attributes(DataMap.get("accessType").toString(),DataMap.get("accessValue").toString()));
    }

    public By attributes(String attribute,String attributevalue){
         switch (attribute.toLowerCase()){
             case "id":return By.id(attributevalue);
             case "name":return By.name(attributevalue);
             case "class":return By.className(attributevalue);
             case "xpath":return By.xpath(attributevalue);
             case "css":return By.cssSelector(attributevalue);
             case "linktext":return By.linkText(attributevalue);
             case "tagname":return By.tagName(attributevalue);
             default:return null;
        }
    }
}