package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 221007 HelloSelenium3
 **********************************************************************************/
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.Map;
public class MoneyLineCollector
{
    private HashMap<String, String> awayMoneylineCloseOddsMap = new HashMap<String, String>();
    private HashMap<String, String> homeSMoneylineCloseOddsMap = new HashMap<>();
    private WebDriver driver;
    private HashMap<String, String> homeMoneylineCloseOddsMap;
    private String homeMoneylineCloseOdds;
    private Actions act;
    public HashMap<String, String> collectMoneyLines(HashMap<String, String> xRefMap) throws InterruptedException
    {
        System.out.println("Collecting Monelines");
        WebElement oddsButton = driver.findElement(By.cssSelector(".covers-CoversSubNav2-visible-links > li:nth-child(3) > a:nth-child(1)"));//Click Odds button
        act.moveToElement(oddsButton).click().build().perform();
        System.out.println("Clicked on Odds Button");
        Thread.sleep(2000);
        WebElement betMenu = driver.findElement(By.cssSelector("#__betMenu"));//Click bet menu
        act.moveToElement(betMenu).click().build().perform();
        System.out.println("clicked on bet menu");
        try//Clicking on Moneyline dropdown item
        {
            WebElement spreadMenuItem = driver.findElement(By.cssSelector("#BetTypeDropdown > li:nth-child(2) > a:nth-child(1)"));//Click Moneyline dropdown item
            act.moveToElement(spreadMenuItem).click().build().perform();
            System.out.println("MLC37 Clicked on Moneyline dropdown item");
        }
        catch (Exception e)//Couldn't click on Moneyline dropdown item
        {
            System.out.println("MLC41 Couldn't click on Moneyline dropdown item.");
        }
        // Get away moneyline close odds column AH
        for (Map.Entry<String, String> entry : xRefMap.entrySet())
        {
            String dataEventId = entry.getKey();
            String dataGame = entry.getValue();
            try
            {
                String s = "[data-game='" + dataGame + "'][data-book='bet365'][data-type='moneyline']";
                WebElement element = driver.findElement(By.cssSelector(s));
                String awayMoneylineCloseOdds = (element.findElement(By.cssSelector(".__american")).getText().split(" ")[0]);
                awayMoneylineCloseOddsMap.put(dataEventId, awayMoneylineCloseOdds);
                System.out.println("MLC54 awayMLCodds map => " + awayMoneylineCloseOddsMap);
            }
            catch (Exception e)
            {
                System.out.println("MLC57 Can't find  Moneyline awayCloseOdds  for dataGame => " + dataGame);
            }
        }
        //try Get home moneyline close odds column S
        {
            for (Map.Entry<String, String> entry : xRefMap.entrySet())
            {
                String dataEventId = entry.getKey();
                String dataGame = entry.getValue();
                try
                {
                    String s = "[data-game='" + dataGame + "'][data-book='bet365'][data-type='moneyline']";
                    WebElement element = driver.findElement(By.cssSelector(s));
                    act.moveToElement(element).click().build().perform();
                    homeMoneylineCloseOdds = (element.findElement(By.cssSelector(".__american")).getText().split(" ")[0]);
                    homeMoneylineCloseOddsMap.put(dataEventId, homeMoneylineCloseOdds);
                    System.out.println("MLC74 homeMLCodds map => " + homeMoneylineCloseOddsMap);
                }
                catch (Exception e)
                {
                    System.out.println("MLC78 Can't find  Moneyline home CloseOdds  for dataGame => " + dataGame);
                }
            }
        }
        driver.close();
        System.out.println("MLC83 Exiting moneyline collector");
        return awayMoneylineCloseOddsMap;
    }
    public void setDriver(WebDriver driver, Actions act)
    {
        this.driver = driver;
        this.act = act;
    }
}

