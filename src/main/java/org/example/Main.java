package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 221003B
 **********************************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
public class Main extends JComponent
{
    public static HashMap<String,String> dataEventIdMap = new HashMap<>();
    private CityNameMapBuilder cityNameMapBuilder = new CityNameMapBuilder();
    private static String version = "221003A";
    private XSSFWorkbook sportDataWorkbook;
    public static HashMap<String, String> weekDateMap = new WeekDateMapBuilder().weekDateMapBuilder();
    public ExcelReader excelReader = new ExcelReader();
    public ExcelBuilder excelBuilder = new ExcelBuilder();
    public ExcelWriter excelWriter = new ExcelWriter();
    public static DataCollector dataCollector = new DataCollector();
    public SpreadCollector spreadCollector = new SpreadCollector();
    public MoneyLineCollector moneyLineCollector = new MoneyLineCollector();
    private ArrayList<WebElement> consensusElementList;
    private int excelLineNumberIndex = 3;//Start filling excel sheet after header
    private Elements oddsElements;
    private static String season = "2022";
    public static String weekNumber = "5";
    public static WebDriver driver = new SafariDriver();
    public static JavascriptExecutor js;
    private static HashMap<String, String> xRefMap;
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        new CityNameMapBuilder();//Builds full city name map to correct for Covers variations in team city names
        new WeekDateMapBuilder();//Builds Game dates for this week
        driver.manage().window();
        js = (JavascriptExecutor) driver;
        dataCollector.setCityNameMap(CityNameMapBuilder.getCityNameMap());
        System.out.println("Main48 city map => " + CityNameMapBuilder.getCityNameMap());
        new DataEventIdMapBuilder();//Builds Main.dataEventIdMap
        System.out.println("Main50 dataEventId => " + dataEventIdMap);
        new MainPageGetter(weekNumber);//Gets https://www.covers.com/sports/nfl/matchups for this week, clears cookies
        new Main().scrape();//Get out of static context
    }

    private void scrape() throws IOException, InterruptedException
    {
        sportDataWorkbook = excelReader.readSportData();//Dan's giant excel spreadsheet
        System.out.println("Main59 read sportsData.xlsx row 0, col 0 => " + sportDataWorkbook.getSheet("Data").getRow(0).getCell(0));
        excelBuilder.setSeason(season);
        excelBuilder.setWeekNumber(weekNumber);
        System.out.println("Main61 dataEventList.size() =>" + Main.dataEventIdMap.size());
        for (String dataEventId : dataEventIdMap.keySet())
        {
            System.out.println("Main65 START MAIN LOOP-----------------------------------------------------START MAIN LOOP FOR dataEventId/dataGame " + dataEventId + "," + dataCollector.getAwayCityPlusNicknameMap().get(dataEventId) + " @ " + dataCollector.getHomeCityPlusNicknameMap().get(dataEventId) + "-------------------------------------------------------------------------------------------START MAIN LOOP");
            dataCollector.collectTeamInfo(dataEventId);
            excelBuilder.setAwayCityMap(dataCollector.getAwayCityMap());
            excelBuilder.setHomeCityMap(dataCollector.getHomeCityMap());
            excelBuilder.setHomeNicknameMap(dataCollector.getHomeNicknameMap());//e.g.BUF, column L12
            excelBuilder.setAwayNicknameMap(dataCollector.getAwayShortNameMap());//e.g. LAR, column AA27
            excelBuilder.setAwayCityPlusNicknameMap(dataCollector.getAwayCityPlusNicknameMap());
            excelBuilder.setHomeCityPlusNicknameMap(dataCollector.getHomeCityPlusNicknameMap());
            excelBuilder.setAwayShortNameMap(dataCollector.getAwayShortNameMap());
            excelBuilder.setHomeShortNameMap(dataCollector.getHomeShortNameMap());
            excelBuilder.setHomeCompleteNameMap(dataCollector.getHomeTeamCompleteNameMap());
            dataCollector.collectConsensusData(consensusElementList, dataEventId);
            excelBuilder.setThisWeekAwayTeamsMap(dataCollector.getAwayFullNameMap());
            excelBuilder.setHomeTeamsMap(dataCollector.getHomeFullNameMap());
            excelBuilder.setGameDateMap(dataCollector.getGameDateMap());
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
            System.out.println("END MAIN LOOP--------------------------------------------------------------END MAIN LOOP FOR dataEventId/dataGame " + dataEventId + ", " + dataCollector.getAwayCityPlusNicknameMap().get(dataEventId) + " @ " + dataCollector.getHomeCityPlusNicknameMap().get(dataEventId) + "-------------------------------------------------------------------------------------------END MAIN LOOP");
        }
        excelWriter.openOutputStream();
        excelWriter.writeSportData(sportDataWorkbook);
        excelWriter.closeOutputStream();
        driver.quit();
        System.out.println("Proper Finish...HOORAY!");
    }
}
