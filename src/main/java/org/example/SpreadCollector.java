package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 220908
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
        System.out.println("......" + driver.findElement(By.cssSelector("#__spreadTotalDiv-nfl-265276 > table:nth-child(2) > tbody:nth-child(3) > tr:nth-child(2) > td:nth-child(9) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1) > div:nth-child(1)")).getText().split(" ")[57]);

        for (String key : xRefMap.keySet())
        {
            String dataGame = xRefMap.get(key);
            System.out.print("dataGame = " + dataGame + "=>");
           // String homeSpreadCloseOdds = driver.findElement(By.cssSelector("[data-book='bet365'][data-game='" +  dataGame + "'][data-type=spread].__awayOdds.__decimal")).getText();
            //System.out.println("++++++++++" + homeSpreadCloseOdds);

                   // homeSpreadCloseOddsMap.put(key, driver.findElement(By.cssSelector("#__spreadTotalDiv-nfl-" + dataGame + " > table:nth-child(2) > tbody:nth-child(3) > tr:nth-child(2) > td:nth-child(9) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1) > div:nth-child(1)")).getText().split(" ")[57]);
        }
        System.out.println(homeSpreadCloseOddsMap);
    }
}
