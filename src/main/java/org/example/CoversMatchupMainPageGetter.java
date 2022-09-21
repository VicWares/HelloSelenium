package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 220921
 **********************************************************************************/
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
public class CoversMatchupMainPageGetter
{
    private static WebDriver driver;
    private static Actions act;
    public CoversMatchupMainPageGetter() throws InterruptedException
    {
        driver = new SafariDriver();
        driver.manage().window().maximize();
        act = new Actions(driver);
        driver.get("https://www.covers.com/sports/nfl/matchups");//Main Covers matchups page
        try//To click cookies on main Scores and Matchups page
        {
            WebElement cookieButton = driver.findElement(By.cssSelector("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));//Click cookies
            act.moveToElement(cookieButton).click().build().perform();
            System.out.println("CMPG21 Main page cookie button clicked");
        }
        catch (Exception e)
        {
            System.out.println("CMPG25 can't click cookie button on main page");
        }
        Thread.sleep(5000);
        try//To select weeks dropdown button on main Scores and Matchups page
        {
            WebElement weeksButton = driver.findElement(By.cssSelector("#select2-cmg_week_filter_dropdown-container"));//Click weeks dropdown button
            act.moveToElement(weeksButton).click().build().perform();
            System.out.println("CMPG32 Main page weeks dropdown button clicked");
        }
        catch (Exception e)
        {
            System.out.println("CMPG36 can't click weeks dropdown button on main page");
        }
        Thread.sleep(5000);
        try//To select this week on main Scores and Matchups page
        {
            WebElement thisWeekButton = driver.findElement(By.cssSelector("#select2-cmg_week_filter_dropdown-result-gix2-2022-10-13"));//Click this week button e.g week 6
            act.moveToElement(thisWeekButton).click().build().perform();
            System.out.println("CMPG43 Main page this week button clicked");
        }
        catch (Exception e)
        {
            System.out.println("CMPG47 can't click this week button on main page");
        }
    }
    public static WebDriver getDriver()
    {
        return driver;
    }
    public static Actions getAct()
    {
        return act;
    }
}
