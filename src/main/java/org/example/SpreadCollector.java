package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 220921
 **********************************************************************************/
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.Map;

import static org.example.Main.act;
public class SpreadCollector
{
    private HashMap<String, String> homeSpreadCloseOddsMap = new HashMap<>();
    private HashMap<String, String> homeSpreadOpenOddsMap = new HashMap<>();
    private WebDriver driver;
    private Actions act;
    public void collectSpreads(HashMap<String, String> xRefMap, WebDriver driver) throws InterruptedException
    {
        //        System.out.println("Collecting Spreads");
        //        System.out.println("clicking cookie button");
        //        WebElement cookieButton = driver.findElement(By.cssSelector("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));//GetCookies button
        //        act.moveToElement(cookieButton).click().build().perform();
        //        System.out.println("Spread Cookie Button clicked");
        //        Thread.sleep(5000);
        System.out.println("Clicking the odds menu button");
        //WebElement oddsButton = driver.findElement(By.cssSelector("body > div:nth-child(12) > div > nav > ul.covers-CoversSubNav2-visible-links > li.covers-CoversSubNav-highlight > a"));//Click Odds button
        //act.moveToElement(oddsButton).click().build().perform();
        System.out.println("Odds Button clicked");
        Thread.sleep(5000);
        try
        {
            System.out.println("SC36 Trying to click on the spread dropdown item");
            driver.manage().window().maximize();
            WebElement spreadMenuItem = driver.findElement(By.cssSelector("a.dropdown-item[data-value='Spread']"));//Click spread dropdown item
            act.moveToElement(spreadMenuItem).click().build().perform();
            driver.manage().window().minimize();
        }
        catch (Exception e)
        {
            System.out.println("SC42 Couldn't find spread dropdown item to click");
        }
        Thread.sleep(5000);
        String dataGame = null;
        try//Get home spread close odds column O15
        {
            for (Map.Entry<String, String> entry : xRefMap.entrySet())
            {
                String dataEventId = entry.getKey();
                dataGame = entry.getValue();
                System.out.println("Collecting Spread data for => " + dataEventId);
                WebElement element = driver.findElement(By.cssSelector("[data-game='" + dataGame + "'][data-book='bet365'][data-type='spread']"));
                System.out.println("SC57..." + element);
                String homeSpreadCloseOdds = (element.findElement(By.cssSelector(".__american")).getText().split(" ")[0]);
                homeSpreadCloseOddsMap.put(dataEventId, homeSpreadCloseOdds);//close in O15, open in N14
                System.out.println("SC59..... " + homeSpreadCloseOdds);
            }
            System.out.println("SC54 Finished iterating through games.");
        }
        catch (Exception e)
        {
            System.out.println("SC65 exception iterating through games");
        }
        System.out.println("SC69 Exiting collectSpreads()");
    }
    public HashMap<String, String> getHomeSpreadCloseOddsMap()
    {
        return homeSpreadCloseOddsMap;
    }
    public HashMap<String, String> getHomeSpreadOpenOddsMap()
    {
        return homeSpreadOpenOddsMap;
    }
    public void setDriver(WebDriver driver, Actions act)
    {
        this.driver = driver;
        this.act = act;
    }
}
