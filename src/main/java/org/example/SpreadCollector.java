package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 220913
 **********************************************************************************/
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
public class SpreadCollector
{
    private HashMap<String, String> homeSpreadCloseOddsMap = new HashMap<>();
    public void collectSpreads(WebDriver driver, HashMap<String, String> xRefMap) throws InterruptedException
    {
        System.out.println("Collecting Spreads");

        Actions act = new Actions(driver);

        WebElement cookieButton = driver.findElement(By.cssSelector("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));//Click Cookies button
        act.moveToElement(cookieButton).click().build().perform();
        System.out.println("Clicked on Cookie Button");

        WebElement oddsButton = driver.findElement(By.cssSelector(".covers-CoversSubNav2-visible-links > li:nth-child(3) > a:nth-child(1)"));//Click Odds button
        act.moveToElement(oddsButton).click().build().perform();
        System.out.println("Clicked on Odds Button");

        Thread.sleep(2000);

        WebElement betMenu = driver.findElement(By.cssSelector("#__betMenu"));//Click bet menu
        act.moveToElement(betMenu).click().build().perform();
        System.out.println("clicked on bet menu");

        WebElement spreadMenuItem = driver.findElement(By.cssSelector("#BetTypeDropdown > li:nth-child(3) > a"));//Click spread dropdown item
        act.moveToElement(spreadMenuItem).click().build().perform();
        System.out.println("Clicked on spread dropdown item");
    }
}
