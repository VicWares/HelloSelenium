package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 220922
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
        Thread.sleep(5000);
        try//To click bet type dropdown
        {
            WebElement betTypeDropdownButton = driver.findElement(By.cssSelector("button.btn.btn-default.dropdown-toggle[type='button']#__betMenu"));//Bet type dropdown button
            act.moveToElement(betTypeDropdownButton).click().build().perform();
            System.out.println("Main53 Main page Bet type dropdown button clicked");
        }
        catch (Exception e)
        {
            System.out.println("Main57 can't click Bet type dropdown button on main page");
        }
        Thread.sleep(5000);
        try//To click Spread dropdown on main Scores and Matchups page
        {
            WebElement spreadButton = driver.findElement(By.cssSelector("#BetTypeDropdown > li:nth-child(3) > a"));//Spread button
            act.moveToElement(spreadButton).click().build().perform();
            System.out.println("Main56 spread button clicked");
        }
        catch (Exception e)
        {
            System.out.println("Main60 can't click spread button");
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
                String homeSpreadCloseOdds = (element.findElement(By.cssSelector(".__american")).getText().split(" ")[0]);
                System.out.println("SC57 homeSpreadCloseOdds => ..." + homeSpreadCloseOdds);
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
    public void setDriver(WebDriver driver, Actions act) {this.driver = driver;this.act = act;}
}
