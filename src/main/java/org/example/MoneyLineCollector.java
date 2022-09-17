package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 220916
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

    public void collectMoneyLines(WebDriver driver, HashMap<String, String> xRefMap) throws InterruptedException
    {
        System.out.println("Collecting Monelines");
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
        WebElement spreadMenuItem = driver.findElement(By.cssSelector("#BetTypeDropdown > li:nth-child(2) > a:nth-child(1)"));//Click Moneyline dropdown item
        act.moveToElement(spreadMenuItem).click().build().perform();
        System.out.println("Clicked on Moneyline dropdown item");
        try//Get home moneyline close odds column S
        {
            for (Map.Entry<String, String> entry : xRefMap.entrySet())
            {
                String dataEventId = entry.getKey();
                String dataGame = entry.getValue();
                WebElement element = driver.findElement(By.cssSelector("[data-game='" + dataGame + "'][data-book='bet365'][data-type='moneyline']"));
                String awayMoneylineCloseOdds = (element.findElement(By.cssSelector(".__american")).getText().split(" ")[0]);
                awayMoneylineCloseOddsMap.put(dataEventId, awayMoneylineCloseOdds);
            }
        }
        catch (Exception e)
        {
            driver.close();
            throw new RuntimeException(e);
        }
        finally
        {
            driver.close();
        }
    }
    public HashMap<String, String> getAwayMoneylineCloseOddsMap()
    {
        return awayMoneylineCloseOddsMap;
    }
}
