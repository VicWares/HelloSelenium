package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 220927
 **********************************************************************************/
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;

import static org.example.Main.js;
public class mainPageGetter
{
    private static WebDriver driver;
    private static Actions act;
    private String weekDate;
    private HashMap<String, String> weekDateMap;
    public mainPageGetter(String weekNumber) throws InterruptedException
    {
        weekDateMap = new WeekDateMapBuilder().WeekDateMapBuilder();
        weekDate =  weekDateMap.get(weekNumber);
        System.out.println("CMPG28 week date => " + weekDate);
        String s = "https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDate;
        System.out.println("URL => " + s);
        Main.driver.get(s);//Main Covers matchups page
//        try//To click cookies on main Scores and Matchups page
//        {
//            WebElement cookieButton = driver.findElement(By.cssSelector("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));//Click cookies
//            js.executeScript("arguments[0].click();", cookieButton);
//            System.out.println("CMPG32 Main page cookie button clicked");
//        }
//        catch (Exception e)
//        {
//            System.out.println("CMPG36 can't click cookie button on main page");
//        }
        try//To click cookies on main Scores and Matchups page
        {
            WebElement cookieButton = Main.driver.findElement(By.cssSelector("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));//Click cookies
            js.executeScript("arguments[0].click();", cookieButton);
            System.out.println("Main76 Main page cookie button clicked by JavaScript!!!");
        }
        catch (Exception e)
        {
            System.out.println("Main42 can't click cookie button on main page");
        }
        Thread.sleep(5000);
        try//To click Odds button on main Scores and Matchups page
        {
            WebElement oddsButton = Main.driver.findElement(By.cssSelector("body > div:nth-child(20) > div > nav > ul.covers-CoversSubNav2-visible-links > li:nth-child(3) > a"));//Click Odds button
            js.executeScript("arguments[0].click();", oddsButton);
            System.out.println("Main55 Main page odds button clicked with JavaScript!!");
        }
        catch (Exception e)
        {
            System.out.println("Main55 can't click odds button on main page");
        }
        Thread.sleep(5000);
    }
}
