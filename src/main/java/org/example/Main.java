package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 2201001A
 **********************************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
public class Main extends JComponent
{
    private static ArrayList<WebElement> dataEventIdList;
    CityNameMapBuilder cityNameMapBuilder = new CityNameMapBuilder();
    public static Actions act;
    private static String version = "221001A";
    private XSSFWorkbook sportDataWorkbook;
    private static HashMap<String, String> weekDateMap = new WeekDateMapBuilder().weekDateMapBuilder();
    private static ArrayList<String> dataEventList = new ArrayList<>();
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
    private static String weekNumber = "2";
    public static WebDriver driver = new SafariDriver();
    public static JavascriptExecutor js;
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        new CityNameMapBuilder();//Builds full city name map to correct for Covers variations in team city names
        new WeekDateMapBuilder();//Builds Game dates for this week
        driver.manage().window();
        js = (JavascriptExecutor) driver;
        dataCollector.setCityNameMap(CityNameMapBuilder.getCityNameMap());//TODO: Fix this
        Main.driver.get("https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDateMap.get(weekNumber));
        buildDataEventIdList();
        new MainPageGetter(weekNumber);//Gets https://www.covers.com/sports/nfl/matchups for this week, clears cookies
        new Main().scrape();//Get out of static context
    }

    private void scrape() throws IOException, InterruptedException
    {
        sportDataWorkbook = excelReader.readSportData();//Dan's giant excel spreadsheet
        excelBuilder.setSeason(season);
        excelBuilder.setWeekNumber(weekNumber);
        System.out.println("Main62...... " + dataEventList);
        for (String dataEventId : dataEventList)
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
    private static void buildDataEventIdList()
    {
        ArrayList<WebElement> dataEventIdElements = (ArrayList<WebElement>) driver.findElements(By.cssSelector("[data-event-id]"));//Find all games for this week
        dataEventIdList = (ArrayList<WebElement>) driver.findElements(By.cssSelector("[data-event-id]"));//Find all games for this week
        int i = 0;
        for (WebElement e : dataEventIdList)
        {
            if (i++ % 2 == 0)
            {
                dataEventList.add(e.getAttribute("data-event-id"));
            }
        }
    }
}
