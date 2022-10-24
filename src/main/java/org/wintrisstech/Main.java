package org.wintrisstech;
/**********************************************************************************
 * Must be run before Selenium for initial setup
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 221024 HelloSeleniumx
 **********************************************************************************/
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
public class Main
{
    public static String weekDate;
    public static HashMap<String, String> homeCompleteNameMap = new HashMap<>();
    private static XSSFWorkbook sportDataWorkbook;
    private static String dataEventId;
    private static int excelLineNumberIndex = 3;//Start filling excel sheet after header
    private static String version = "GreatCovers 221018A";
    public static String season = "2022";
    public static String weekNumber = "7";
    public static WebDriver driver = new SafariDriver();
    static ExcelWriter excelWriter = new ExcelWriter();
    static ExcelReader excelReader = new ExcelReader();
    static DataCollector dataCollector = new DataCollector();
    public static JavascriptExecutor js = (JavascriptExecutor) driver;
    private static int game;
    private Actions act = new Actions(driver);
    public static HashMap<String, String> MLATSawayMap = new HashMap<>();
    public static HashMap<String, String> xRefMap = new HashMap<>();
    public static HashMap<String, String> ouHomeMap = new HashMap<>();
    public static HashMap<String, String> atsHomeMap = new HashMap<>();
    public static HashMap<String, String> atsAwayMap = new HashMap<>();
    public static HashMap<String, String> ouAwayMap = new HashMap<>();
    public static HashMap<String, String> weekDateMap = new HashMap<String, String>();//Constructor builds HashMap of NFL week calendar dates (e.g 2022-09-08) referenced by NFL week number (e.g. 4)
    public static HashMap<String, String> cityNameMap = new HashMap<String, String>();//Constructor builds HashMap of NFL team city names referenced by bogus Covers city names (e.g East Rutherford) referenced by NFL city names (e.g. New York)
    public static HashMap<String, Integer> excelRowIndexMap = new HashMap<String, Integer>();//HashMap references excel row numbers (e.g. 5) by dataEventId (e.g. 87409).  Each excel row has a specific data-event-id
    public Main()
    {
        driver = new SafariDriver();
    }
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2022 Dan Farris");
        weekDate = weekDateMap.get(weekNumber);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        new CityNameMapBuilder();//Builds full city name map to correct for Covers variations in team city names
        new WeekDateMapBuilder();//Builds Game dates for current week
        weekDate = weekDateMap.get(weekNumber);
        Main.driver.get("https://www.covers.com/sports/nfl/matchups?selectedDate=" + Main.weekDate);//Main Covers page
        List<WebElement> events = driver.findElements(By.cssSelector("div.cmg_game_data"));//Determine how many games to index this week
        xRefMap = buildXref(events);//Cross-reference from dava-event-id to data-game e.g. 87700=265355.  Both are used for referencing matchups at various times!!
        System.out.println("Main60 This is week " + weekNumber + ", " + weekDateMap.get(weekNumber) + ", " + events.size() + " games this week.");
        excelRowIndexMap = buildExcelRowIndexMap();
        System.out.println("Main65...Excel Row index Map => " + excelRowIndexMap);
        sportDataWorkbook = excelReader.readSportData();
        dataCollector.setSportDataWorkbook(sportDataWorkbook);
        Main.driver.get("https://www.covers.com/sports/nfl/matchups?selectedDate=" + Main.weekDate);//Driver now holds Current week scores & matchups page
        dataCollector.collectTeamDataForThisWeek();
        Main.driver.get("https://www.covers.com/sports/nfl/matchups?selectedDate=" + Main.weekDate);//Driver now holds Current week scores & matchups page
//        driver.findElement(By.cssSelector("a[href='/sport/football/nfl/odds']")).click();//Select Odds button")).click();
//        dataCollector.collectOddsData();
        //******************************************************************************************************************<START>*********************************************************************************************************************************************************************
        for (Map.Entry<String, String> entry : xRefMap.entrySet())//START MAIN LOOP//////////////////////////////////////<START>//////////////////////////////////////////////////////////////////////////////////////////////START MAIN LOOP
        {
            dataEventId = entry.getKey();//Matchup index used almost everywhere...
            String dataGame = xRefMap.get(dataEventId);//Used sometimes to index matchups
            System.out.println("Main77 START MAIN LOOP////////////////// Start Game => " + game + "//////////////////////////////////////////START MAIN LOOP FOR dataEventId/dataGame " + dataEventId + "/" + dataGame + "////////////////////////////////////////////////////////////////////START MAIN LOOP");
            Main.driver.get("https://contests.covers.com/consensus/matchupconsensusdetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + dataEventId);
            dataCollector.collectConsensusData(dataEventId);
            System.out.println("Main88 END MAIN LOOP//////// End Game =>>>>>>>>>>>> " + game++ + "/////////////////////////////" + dataEventId + " " + dataGame + " -----------------<=====================>----------------------------------END MAIN LOOP FOR dataEventId/dataGame " + dataEventId + "/" + xRefMap.get(dataEventId) + "-------------------------------------------------------------------------------------------END MAIN LOOP");
        }//END MAIN LOOP///////////////////////////////////////////////////////////////////////////<END>///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////END MAIN LOOP
        //******************************************************************************************************************<END>*********************************************************************************************************************************************************************
        try
        {
            Main.driver.close();
        }
        catch (Exception e)
        {
            System.out.println("Main96 Safari web driver close exception");
        }
        excelWriter.writeSportData(sportDataWorkbook);
        System.out.println("Main90......Completed GreatCovers Successfully...Hooray...");
    }
    private static HashMap<String, Integer> buildExcelRowIndexMap()
    {
        int row = 3;//First entry to Excel sheet below header
        for (String dataEventId : xRefMap.keySet())
        {
            excelRowIndexMap.put(dataEventId, Integer.valueOf(row++));
        }
        return excelRowIndexMap;
    }
    public static void clickCookies(String sourceLineNumber)
    {
        try
        {
            WebElement cookieButton = driver.findElement(By.cssSelector("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));//Click Cookies button
            js.executeScript("arguments[0].click();", cookieButton);
            System.out.println("Clicked on Cookie Button, line# " + sourceLineNumber);
        }
        catch (Exception e)
        {
            System.out.println("NO...Click on Cookie Button, line# " + sourceLineNumber);
        }
    }
    public static class CityNameMapBuilder
    {
        public CityNameMapBuilder()
        {
            cityNameMap.put("Minneapolis", "Minnesota");//Minnesota Vikings
            cityNameMap.put("Tampa", "Tampa Bay");//Tampa Bay Buccaneers
            cityNameMap.put("Tampa Bay", "Tampa Bay");//Tampa Bay Buccaneers
            cityNameMap.put("Arlington", "Dallas");//Dallas Cowboys
            cityNameMap.put("Dallas", "Dallas");//Dallas Cowboys
            cityNameMap.put("Orchard Park", "Buffalo");//Buffalo Bills
            cityNameMap.put("Buffalo", "Buffalo");//Buffalo Bills
            cityNameMap.put("Charlotte", "Carolina");//Carolina Panthers
            cityNameMap.put("Carolina", "Carolina");//Carolina Panthers
            cityNameMap.put("Arizona", "Arizona");//Arizona Cardinals
            cityNameMap.put("Tempe", "Arizona");//Arizona Cardinals
            cityNameMap.put("Foxborough", "New England");//New England Patriots
            cityNameMap.put("New England", "New England");//New England Patriots
            cityNameMap.put("East Rutherford", "New York");//New York Giants and New York Jets
            cityNameMap.put("New York", "New York");//New York Giants and New York Jets
            cityNameMap.put("Landover", "Washington");//Washington Football Team
            cityNameMap.put("Washington", "Washington");//Washington Football Team
            cityNameMap.put("Nashville", "Tennessee");//Tennessee Titans
            cityNameMap.put("Miami", "Miami");//Miami Dolphins
            cityNameMap.put("Baltimore", "Baltimore");//Baltimore Ravens
            cityNameMap.put("Cincinnati", "Cincinnati");//Cincinnati Bengals
            cityNameMap.put("Cleveland", "Cleveland");//Cleveland Browns
            cityNameMap.put("Pittsburgh", "Pittsburgh");//Pittsburgh Steelers
            cityNameMap.put("Houston", "Houston");//Houston Texans
            cityNameMap.put("Indianapolis", "Indianapolis");//Indianapolis Colts
            cityNameMap.put("Jacksonville", "Jacksonville");//Jacksonville Jaguars
            cityNameMap.put("Tennessee", "Tennessee");//Tennessee Titans
            cityNameMap.put("Denver", "Denver");//Denver Broncos
            cityNameMap.put("Kansas City", "Kansas City");//Kansas City Chiefs
            cityNameMap.put("Las Vegas", "Las Vegas");//Los Angeles Chargers and Los Angeles Rams
            cityNameMap.put("Philadelphia", "Philadelphia");//Philadelphia Eagles
            cityNameMap.put("Chicago", "Chicago");//Chicago Bears
            cityNameMap.put("Detroit", "Detroit");//Detroit Lions
            cityNameMap.put("Green Bay", "Green Bay");//Green Bay Packers
            cityNameMap.put("Minnesota", "Minnesota");
            cityNameMap.put("Atlanta", "Atlanta");//Atlanta Falcons
            cityNameMap.put("New Orleans", "New Orleans");//New Orleans Saints
            cityNameMap.put("Los Angeles", "Los Angeles");//Los Angeles Rams
            cityNameMap.put("San Francisco", "San Francisco");//San Francisco 49ers
            cityNameMap.put("Seattle", "Seattle");//Seattle Seahawks
            System.out.println("Main157 cityNameMap => " + cityNameMap);
        }
    }
    public static class WeekDateMapBuilder
    {
        public WeekDateMapBuilder()
        {
            weekDateMap.put("1", "2022-09-08");//Season 2022 start...Week 1
            weekDateMap.put("2", "2022-09-15");//Weeks start on Thursdays
            weekDateMap.put("3", "2022-09-22");
            weekDateMap.put("4", "2022-09-29");
            weekDateMap.put("5", "2022-10-06");
            weekDateMap.put("6", "2022-10-13");
            weekDateMap.put("7", "2022-10-20");
            weekDateMap.put("8", "2022-10-27");
            weekDateMap.put("9", "2022-11-03");
            weekDateMap.put("10", "2022-11-10");
            weekDateMap.put("11", "2022-11-17");
            weekDateMap.put("12", "2022-11-24");
            weekDateMap.put("13", "2022-12-01");
            weekDateMap.put("14", "2022-12-08");
            weekDateMap.put("15", "2022-12-15");
            weekDateMap.put("16", "2022-12-22");
            weekDateMap.put("17", "2022-12-29");
            weekDateMap.put("18", "2023-01-08");
            weekDateMap.put("19", "2023-02-05");
            System.out.println("Main184 weekDateMap => " + weekDateMap);
        }
    }
    public static HashMap<String, String> buildXref(List<WebElement> events)
    {
        for (WebElement e : events)
        {
            String dataEventId = e.getAttribute("data-event-id");
            String dataGame = e.getAttribute("data-link");
            dataGame = dataGame.substring(28, 34);
            xRefMap.put(dataEventId, dataGame);
        }
        System.out.println("Main199 xRefMap => " + xRefMap);
        return xRefMap;
    }
}