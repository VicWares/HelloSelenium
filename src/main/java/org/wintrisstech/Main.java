package org.wintrisstech;
/**********************************************************************************
 * Must be run before Selenium for initial setup
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 221103 HelloSeleniumX2
 **********************************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
public class Main
{
    public static String weekDate;
    public static HashMap<String, String> homeCompleteNameMap = new HashMap<>();
    private static XSSFWorkbook sportDataWorkbook;
    private static String version = "version 221028 HelloSeleniumX";
    public static String season = "2022";
    public static String weekNumber = "9";
    public static WebDriver driver = new SafariDriver();
    static ExcelWriter excelWriter = new ExcelWriter();
    static ExcelReader excelReader = new ExcelReader();
    static DataCollector dataCollector = new DataCollector();
    private WeekDateMapBuilder weekDateMapBuilder = new WeekDateMapBuilder();
    private static int game = 1;
    private static String dataGame;
    public static HashMap<String, String> xRefMap = new HashMap<>();
    public static HashMap<String, String> weekDateMap = new HashMap<String, String>();//Constructor builds HashMap of NFL week calendar dates (e.g 2022-09-08) referenced by NFL week number (e.g. 4)
    public static HashMap<String, String> cityNameMap = new HashMap<String, String>();//Constructor builds HashMap of NFL team city names referenced by bogus Covers city names (e.g East Rutherford) referenced by NFL city names (e.g. New York)
    public static HashMap<String, Integer> excelRowIndexMap = new HashMap<String, Integer>();//HashMap references excel row numbers (e.g. 5) by dataEventId (e.g. 87409).  Each excel row has a specific data-event-id
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2022 Dan Farris");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        new CityNameMapBuilder();//Builds full city name HashMap <BadCityName,GoodCityName>to correct for Covers variations in team city names
        new WeekDateMapBuilder();//Builds Game dates HashMap <dataEventId,weekDate> for current week
        Main.driver.get("https://www.covers.com/sports/nfl/matchups?selectedDate=" + Main.weekDate);//Main Covers page
        List<WebElement> weekEventElements = driver.findElements(By.cssSelector("div.cmg_game_data.cmg_matchup_game_box[data-event-id]"));//Determine how many and which games to index this week****critical/basic index element!!!
        System.out.println("Main59...number of matchups this week => " + weekEventElements.size());
        xRefMap = buildXrefMap(weekEventElements);//Cross-reference from dava-event-id to data-game e.g. 87700=265355.  Both are used for referencing matchups at various times!!
        System.out.println("Main50 This is week " + weekNumber + ", " + weekDateMap.get(weekNumber) + ", " + weekEventElements.size() + " games this week.");
        excelRowIndexMap = buildExcelRowIndexMap();
        System.out.println("Main63...Excel Row index Map => " + excelRowIndexMap);
        sportDataWorkbook = excelReader.readSportData();
        dataCollector.setSportDataWorkbook(sportDataWorkbook);
        dataCollector.collectTeamDataForThisWeek(weekEventElements);
        for (String dataEventId : xRefMap.keySet())
        {
            //<START MAIN LOOP> >************************************************************************************************************ <START MAIN LOOP> *************************************************************************************************************
            System.out.println("Main64 START MAIN LOOP >>>>>>>> Start Game game# => " + game++ + " **************************** dataEventId => " + dataEventId + " , "  +  DataCollector.gameIdentifierMap.get(dataEventId) + " ********************************* START MAIN LOOP");
            dataGame = xRefMap.get(dataEventId);//Used sometimes to index matchups, dtataEventId used extensively
            driver.get("https://contests.covers.com/consensus/matchupconsensusdetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + dataEventId);
            dataCollector.collectOverall(dataEventId);
            dataCollector.collectMoneyLeaders(dataEventId);
            System.out.println("Main71 END MAIN LOOP **************************************************************************************<END>********************************************************************************************************** END MAIN LOOP");
            //<END MAIN LOOP>******************************************************************************************************************<END>***********************************************************************************************************************
        }
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

    public static HashMap<String, String> buildXrefMap(List<WebElement> weekEvents)
    {
        for (WebElement e : weekEvents)
        {
            String dataEventId = e.getAttribute("data-event-id");
            String dataGame = e.getAttribute("data-link");
            dataGame = dataGame.substring(28, 34);//TODO:Fix this
            xRefMap.put(dataEventId, dataGame);
        }
        System.out.println("Main201 xRefMap => " + xRefMap);
        return xRefMap;
    }
}