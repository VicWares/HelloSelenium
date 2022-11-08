package org.wintrisstech;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 221108A HelloSelenium32
 **********************************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class Main extends JComponent
{
    CityNameMapBuilder cityNameMapBuilder = new CityNameMapBuilder();
    private static String version = "221007";
    private XSSFWorkbook sportDataWorkbook;
    private static HashMap<String, String> weekDateMap = new WeekDateMapBuilder().WeekDateMapBuilder();
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
    private static String season = "2022";
    private static String weekNumber = "7";
    public static WebDriver driver;
    public static JavascriptExecutor js;
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        new CityNameMapBuilder();//Builds full city name map to correct for Covers variations in team city names
        new WeekDateMapBuilder();//Builds Game dates for this week
        driver = new SafariDriver();
        driver.manage().window().maximize();
        js = (JavascriptExecutor)driver;
        dataCollector.setCityNameMap(CityNameMapBuilder.getCityNameMap());
        new Main().scrape();//Get out of static context
    }
    private void scrape() throws IOException, InterruptedException
    {
        String weekDate = weekDateMap.get(weekNumber);
        sportDataWorkbook = excelReader.readSportData();
        dataCollector.setThisSeason(season);
        excelBuilder.setSeason(season);
        excelBuilder.setWeekNumber(weekNumber);
        Elements nflElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDate);//Main Covers scores & matchups page
        Elements weekElements = nflElements.select(".cmg_game_data, .cmg_matchup_game_box");
        xRefMap = buildXref(weekElements);
        System.out.println("Main66 week number => " + weekNumber + ", week date => " + weekDate + ", " + weekElements.size() + " games this week");
        System.out.println(xRefMap);
        dataCollector.collectTeamInfo(weekElements);
        for (Map.Entry<String, String> entry : xRefMap.entrySet())
        {
            String dataEventId = entry.getKey();
            String dataGame = xRefMap.get(dataEventId);
            System.out.println("Main65 START MAIN LOOP-----------------------------------------------------START MAIN LOOP FOR dataEventId/dataGame " + dataEventId + "/" + dataGame + "\t" + dataCollector.getAwayFullNameMap().get(dataEventId) + " @ " + dataCollector.getHomeFullNameMap().get(dataEventId) + "-------------------------------------------------------------------------------------------START MAIN LOOP");
            //*********************************************************************************************START MAIN LOOP******************************************************************************************************
            consensusElements = webSiteReader.readCleanWebsite("https://contests.covers.com/consensus/matchupconsensusdetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + dataEventId);//Coveers main manthips page
            dataCollector.collectConsensusData(consensusElements, dataEventId);
            excelBuilder.setAwayFullNameMap(dataCollector.getAwayFullNameMap());
            excelBuilder.setHomeFullNameMap(dataCollector.getHomeFullNameMap());
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
            excelBuilder.setTotalHomeCloseOddsMap(dataCollector.getTotalHomeCloseOddsMap());//Column AP
            excelBuilder.buildExcel(sportDataWorkbook, dataEventId, excelLineNumberIndex, dataCollector.getGameIdentifierMap().get(dataEventId));
            excelLineNumberIndex++;
            //***********************************************************************************************END MAIN LOOP******************************************************************************************************
            System.out.println("\nEND MAIN LOOP----------------------------------------------------------------END MAIN LOOP FOR dataEventId/dataGame " + dataEventId + "/" + dataGame + "    " + dataCollector.getAwayFullNameMap().get(dataEventId) + " @ " + dataCollector.getHomeFullNameMap().get(dataEventId) + "-------------------------------------------------------------------------------------------END MAIN LOOP");
        }
        excelWriter.openOutputStream();
        excelWriter.writeSportData(sportDataWorkbook);
        excelWriter.closeOutputStream();
        driver.quit();
        System.out.println("Proper Finish...HOORAY!");
    }
    public HashMap<String, String> buildXref(Elements weekElements)
    {
        for (Element e : weekElements)
        {
            String dataLinkString = e.attr("data-link");
            String[] dlsa = dataLinkString.split("/");
            String dataLink = dlsa[5];
            String dataEvent = e.attr("data-event-id");
            xRefMap.put(dataEvent, dataLink);
        }
        return xRefMap;
    }
}