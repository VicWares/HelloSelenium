package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 220922
 **********************************************************************************/
import org.checkerframework.checker.units.qual.K;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import java.util.HashMap;

public class CoversMatchupMainPageGetter
{
    private static WebDriver driver;
    private static Actions act;
    private String weekNumber;
    private String weekDate;
    private HashMap<String, String> weekDateMap;
    public CoversMatchupMainPageGetter() throws InterruptedException
    {
        weekNumber = "1";
        driver = new SafariDriver();
        driver.manage().window().maximize();
        act = new Actions(driver);
        weekDateMap = new WeekDateMapBuilder().WeekDateMapBuilder();
        weekDate =  weekDateMap.get(weekNumber);
        System.out.println("week date => " + weekDate);
        String s = "https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDate;
        System.out.println(s);
        driver.get(s);//Main Covers matchups page
        try//To click cookies on main Scores and Matchups page
        {
            WebElement cookieButton = driver.findElement(By.cssSelector("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));//Click cookies
            act.moveToElement(cookieButton).click().build().perform();
            System.out.println("CMPG39 Main page cookie button clicked");
        }
        catch (Exception e)
        {
            System.out.println("CMPG43 can't click cookie button on main page");
        }

        try//To click Odds on main Scores and Matchups page
        {
            WebElement oddsButton = driver.findElement(By.cssSelector("body > div:nth-child(20) > div > nav > ul.covers-CoversSubNav2-visible-links > li:nth-child(3) > a"));//Click cookies
            act.moveToElement(oddsButton).click().build().perform();
            System.out.println("CPMG58 Main page Odds button clicked");
        }
        catch (Exception e)
        {
            System.out.println("CPMG54 can't click Odds button on main page");
        }
        Thread.sleep(5000);
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
