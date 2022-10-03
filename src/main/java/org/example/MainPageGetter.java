package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 221003
 **********************************************************************************/
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;

import static org.example.Main.js;
public class MainPageGetter
{
    private static WebDriver driver;
    private static Actions act;
    private String weekDate;
    private HashMap<String, String> weekDateMap;
    public MainPageGetter(String weekNumber) throws InterruptedException
    {
        weekDateMap = new WeekDateMapBuilder().weekDateMapBuilder();
        weekDate =  weekDateMap.get(weekNumber);
        System.out.println("CMPG26 week number => " + weekNumber + ", week date => " + weekDate);
        String s = "https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDate;
        System.out.println("URL => " + s);
        Main.driver.get(s);//Main Covers matchups page
        try//To click cookies on main Scores and Matchups page
        {
            WebElement cookieButton = Main.driver.findElement(By.cssSelector("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));//Click cookies
            js.executeScript("arguments[0].click();", cookieButton);
            System.out.println("Main34 Main page cookie button clicked by JavaScript!!!");
        }
        catch (Exception e)
        {
            System.out.println("Main38 can't click cookie button on main page");
        }
//        Thread.sleep(5000);
//        try//To click Odds button on main Scores and Matchups page
//        {
//            WebElement oddsButton = Main.driver.findElement(By.cssSelector("body > div:nth-child(20) > div > nav > ul.covers-CoversSubNav2-visible-links > li:nth-child(3) > a"));//Click Odds button
//            js.executeScript("arguments[0].click();", oddsButton);
//            System.out.println("Main45 Main page odds button clicked with JavaScript!!");
//        }
//        catch (Exception e)
//        {
//            System.out.println("Main58 can't click odds button on main page");
//        }
//        Thread.sleep(5000);
//        try//To click bet type dropdown button
//        {
//            WebElement betTypeDropdownButton = Main.driver.findElement(By.cssSelector("button.btn.btn-default.dropdown-toggle[type='button']#__betMenu"));//Bet type dropdown button
//            js.executeScript("arguments[0].click();", betTypeDropdownButton);
//            System.out.println("Main 99 Main page Bet type dropdown button clicked by JavaScript!!");
//        }
//        catch (Exception e)
//        {
//            System.out.println("Main102 can't click Bet type dropdown button on main page");
//        }
//        Thread.sleep(5000);
    }
}
