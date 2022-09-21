package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 220921
 **********************************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class Main extends JComponent
{
    CityNameMapBuilder cityNameMapBuilder = new CityNameMapBuilder();
    public static Actions act;
    String oddsURL = "https://www.covers.com/sport/football/nfl/odds";
    private static String version = "220921";
    private XSSFWorkbook sportDataWorkbook;
    private static HashMap<String, String> weekDateMap = new HashMap<>();
    private HashMap<String, String> xRefMap = new HashMap<>();
    public WebSiteReader webSiteReader = new WebSiteReader();
    public ExcelReader excelReader = new ExcelReader();
    public ExcelBuilder excelBuilder = new ExcelBuilder();
    public ExcelWriter excelWriter = new ExcelWriter();
    public static DataCollector dataCollector = new DataCollector();
    public SpreadCollector spreadCollector = new SpreadCollector();
    public MoneyLineCollector moneyLineCollector = new MoneyLineCollector();
    private Elements consensusElements;
    private int excelLineNumberIndex = 3;//Start filling excel sheet after header
    private Elements oddsElements;
    private static String season;
    private static String weekNumber;
    public static WebDriver driver;
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        new CityNameMapBuilder();//Builds full city name map to correct for Covers variations in team city names
        new WeekDateMapBuilder();//Builds Game dates for this week
        new CoversMatchupMainPageGetter();//Gets https://www.covers.com/sports/nfl/matchups and clears cookies and selects week
        driver = CoversMatchupMainPageGetter.getDriver();
        act = CoversMatchupMainPageGetter.getAct();
        dataCollector.setCityNameMap(WeekDateMapBuilder.getWeekDateMap());
        weekNumber = "1";//Override for testing
        season = "2022";

//        try//To click weeks button on main Scores and Matchups page
//        {
//            WebElement weeksButton = driver.findElement(By.cssSelector("#select2-cmg_week_filter_dropdown-container"));//Click week button
//            act.moveToElement(weeksButton).click().build().perform();
//            System.out.println("Main page wees button clicked ");
//        }
//        catch (Exception e)
//        {
//            System.out.println("Main74 can't click weeks button on main page");
//        }

        //Thread.sleep(5000);


//        try//To click this week number on main page
//        {
//            WebElement weeksButton = driver.findElement(By.cssSelector("#content > div.cmg_sorting_row > div > div > a.cmg_active_navigation_item"));//Click week button
//            act.moveToElement(weeksButton).click().build().perform();
//            System.out.println("Main page selected week button clicked ");
//        }
//        catch (Exception e)
//        {
//            System.out.println("Main88 can't select this week button on main page");
//        }
       // Thread.sleep(5000);
//        try//To set this week on main Scores and Matchups page
//        {
//            WebElement weekButton = driver.findElement(By.cssSelector("a.cmg_active_navigation_item[data-date=Week '" + weekNumber + "']"));//Click week button
//            act.moveToElement(weekButton).click().build().perform();
//            System.out.println("Main95 Main page this week button clicked => " + "weeek " + " week number => " + weekNumber);
//        }
//        catch (Exception e)
//        {
//            System.out.println("Main99 can't set this week on main page");
//        }

        //Thread.sleep(5000);

//       try//To click on odds button on main page
//       {
//           WebElement oddsButton = driver.findElement(By.cssSelector(".covers-CoversSubNav-highlight > a:nth-child(1)"));//Click Odds button
//           act.moveToElement(oddsButton).click().build().perform();
//           System.out.println("Main page oddsButton clicked");
//       }
//       catch (Exception e)
//       {
//           System.out.println("Main89 can't click on main page odds button");
//       }

       //Thread.sleep(5000);

       new Main().initialize();//Get out of static context
    }
    private void initialize() throws IOException, InterruptedException
    {
        //weekNumber = JOptionPane.showInputDialog("Enter NFL week number");
        weekNumber = "1";//Override for testing
        season = "2022";
        String weekDate = weekDateMap.get(weekNumber);
        sportDataWorkbook = excelReader.readSportData();
        spreadCollector.setDriver(Main.driver, act);
        moneyLineCollector.setDriver(Main.driver, act);
        dataCollector.setThisSeason(season);
        excelBuilder.setSeason(season);
        excelBuilder.setWeekNumber(weekNumber);
        Elements nflElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDate);
        Elements weekElements = nflElements.select(".cmg_game_data, .cmg_matchup_game_box");
        xRefMap = buildXref(weekElements);
        oddsElements = webSiteReader.readCleanWebsite(oddsURL);//Info from log-in date through the present NFL week
        System.out.println("Main115 week number => " + weekNumber + ", week date => " + weekDate + ", " + weekElements.size() + " games this week") ;
        System.out.println(xRefMap);
        dataCollector.collectTeamInfo(weekElements);
        spreadCollector.collectSpreads(xRefMap, driver);
        excelBuilder.setHomeSpreadCloseOddsMap(spreadCollector.getHomeSpreadCloseOddsMap());
        Thread.sleep(5000);
        moneyLineCollector.collectMoneyLines(xRefMap);
        excelBuilder.setAwayMoneylineCloseOddsMap(moneyLineCollector.getAwayMoneylineCloseOddsMap());
        for (Map.Entry<String, String> entry : xRefMap.entrySet())
        {
            String dataEventId = entry.getKey();
            String dataGame = xRefMap.get(dataEventId);
            System.out.println("Main65 START MAIN LOOP-----------------------------------------------------START MAIN LOOP FOR dataEventId/dataGame " + dataEventId + "/" + dataGame + "\t" + dataCollector.getAwayFullNameMap().get(dataEventId) + " @ " +  dataCollector.getHomeFullNameMap().get(dataEventId) + "-------------------------------------------------------------------------------------------START MAIN LOOP");
            Elements moneyLineOddsElements = oddsElements.select("[data-book='bet365'][data-game='" + dataGame + "'][data-type='moneyline']");
            consensusElements = webSiteReader.readCleanWebsite("https://contests.covers.com/consensus/matchupconsensusdetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + dataEventId);
            dataCollector.collectConsensusData(consensusElements, dataEventId);
            excelBuilder.setThisWeekAwayTeamsMap(dataCollector.getAwayFullNameMap());
            excelBuilder.setHomeTeamsMap(dataCollector.getHomeFullNameMap());
            excelBuilder.setHomeShortNameMap(dataCollector.getHomeShortNameMap());
            excelBuilder.setAwayShortNameMap(dataCollector.getAwayShortNameMap());
            excelBuilder.setGameDatesMap(dataCollector.getGameDatesMap());
            excelBuilder.setAtsHomesMap(dataCollector.getAtsHomesMap());
            excelBuilder.setAtsAwaysMap(dataCollector.getAtsAwaysMap());
            excelBuilder.setOuOversMap(dataCollector.getOuAwayMap());
            excelBuilder.setOuUndersMap(dataCollector.getOuHomeMap());
            excelBuilder.setAwayCompleteNameMap(dataCollector.getAwayTeamCompleteNameMap());
            excelBuilder.setHomeCompleteNameMap(dataCollector.getHomeTeamCompleteNameMap());
            excelBuilder.setGameIdentifier(dataCollector.getGameIdentifierMap().get(dataEventId));
            excelBuilder.setTotalHomeOpenOddsMap(dataCollector.getTotalHomeOpenOddsMap());
            excelBuilder.setTotalHomeCloseOddsMap(dataCollector.getTotalHomeCloseOddsMap());
            excelBuilder.buildExcel(sportDataWorkbook, dataEventId, excelLineNumberIndex, dataCollector.getGameIdentifierMap().get(dataEventId));
            excelLineNumberIndex++;
            System.out.println("END MAIN LOOP--------------------------------------------------------------END MAIN LOOP FOR dataEventId/dataGame " + dataEventId + "/" + dataGame + "    " + dataCollector.getAwayFullNameMap().get(dataEventId)+ " @ " + dataCollector.getHomeFullNameMap().get(dataEventId) + "-------------------------------------------------------------------------------------------END MAIN LOOP");
        }
        excelWriter.openOutputStream();
        excelWriter.writeSportData(sportDataWorkbook);
        excelWriter.closeOutputStream();
        System.out.println("Proper Finish...HOORAY!");
    }
    public HashMap<String, String> buildXref(Elements weekElements)
    {
        for (Element e : weekElements) {
            String dataLinkString = e.attr("data-link");
            String[] dlsa = dataLinkString.split("/");
            String dataLink = dlsa[5];
            String dataEvent = e.attr("data-event-id");
            xRefMap.put(dataEvent, dataLink);
        }
        return xRefMap;
    }


}