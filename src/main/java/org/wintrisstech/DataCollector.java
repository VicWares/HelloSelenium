package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 221103 HelloSeleniumX
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.wintrisstech.Main.*;
public class DataCollector
{
    private static HashMap<String, String> bet365HomeTeamOdds = new HashMap<>();
    private static HashMap<String, String> bet365AwayTeamOdds = new HashMap<>();
    private static HashMap<String, String> bet365Odds = new HashMap<>();
    private static ArrayList<String> thisWeekMatchuplist = new ArrayList<>();
    private static ArrayList<String> homeAmericanOddsArray = new ArrayList<>();
    private static HashMap<String, String> homeAmericanOddsMap = new HashMap<>();
    private static ArrayList<String> homeDecimalOddsArray = new ArrayList<>();
    private static HashMap<String, String> homeDecimalOddsMap = new HashMap<>();
    private static ArrayList<String> homeFractionalOddsArray = new ArrayList<>();
    private static HashMap<String, String> homeFractionalOddsMap = new HashMap<>();
    private static ArrayList<String> awayAmericanOddsArray = new ArrayList<>();
    private static HashMap<String, String> awayAmericanOddsMap = new HashMap<>();
    private static ArrayList<String> awayDecimalOddsArray = new ArrayList<>();
    private static HashMap<String, String> awayMLoddsMap = new HashMap<>();
    private static HashMap<String, String> homeMLoddsMap = new HashMap<>();
    private static ArrayList<String> awayFractionalOddsArray = new ArrayList<>();
    private static HashMap<String, String> awayFractionalOddsMap = new HashMap<>();
    private static HashMap<String, String> totalHomeOpenOddsMap = new HashMap<>();
    private static HashMap<String, String> totalHomeCloseOddsMap = new HashMap<>();
    private HashMap getHomeTotalCloseOddsMap = new HashMap<String, String>();
    private HashMap getHomeTotalOpenOddsMap = new HashMap<String, String>();
    private HashMap<String, String> mlHomeOdds = new HashMap<String, String>();
    private HashMap<String, String> mlAwayOdds = new HashMap<String, String>();
    private String dataEventId;
    private String MLhomeOdds;
    private String MLawayOdds;
    private String homeNickname;//e.g. Browns...data-home-team-nickname-search
    private String awayTeamNickname;//e.g Texans...data-away-team-nickname-search
    private String awayTeamFullName;//e.g. Cleveland...data-home-team-fullname-search
    private String homeTeamFullName;//e.g Houston...data-home-team-fullname-search
    private String awayCompleteName;//e.g. Kansas City Chiefs
    private String homeCompleteName;//e.g Houston Texans
    private String gameIdentifier;//e.g 2020 - Houston Texans @ Kansas City Chiefs
    private String awayTeamScore;
    private String homeTeamScore;
    private String gameDate;
    private String awayTeamCity;
    private String homeTeamCity;
    private String thisWeek;
    private String thisSeason;
    private ArrayList<String> thisWeekGameDates = new ArrayList<String>();
    private ArrayList<String> thisGameWeekNumbers = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekAwayTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeams = new ArrayList<String>();
    private ArrayList<String> atsHomes = new ArrayList<String>();
    private ArrayList<String> thisWeekAwayTeams = new ArrayList<String>();
    private HashMap<String, String> gameDatesMap = new HashMap<>();
    private static HashMap<String, String> gameIdentifierMap = new HashMap<>();
    public static HashMap<String, String> homeFullNameMap = new HashMap<>();
    private HashMap<String, String> awayFullNameMap = new HashMap<>();
    private HashMap<String, String> homeShortNameMap = new HashMap<>();
    private HashMap<String, String> awayShortNameMap = new HashMap<>();
    private HashMap<String, String> atsHomeMap = new HashMap<>();
    private HashMap<String, String> ouHomeMap = new HashMap<>();
    private HashMap<String, String> ouOversMap = new HashMap<>();
    private HashMap<String, String> cityNameMap = new HashMap<>();
    private HashMap<String, String> idXref = new HashMap<>();
    private HashMap<String, String> homeTotalOpenOddsMap = new HashMap<>();
    private HashMap<String, String> homeTotalCloseOddsMap = new HashMap<>();
    private String[] bet365OddsArray = new String[6];
    private String homeTeamShortName;
    private String awayTeamShortName;
    private HashMap<String, String> homeTeamCompleteNameMap = new HashMap<>();
    private HashMap<String, String> awayTeamCompleteNameMap = new HashMap<>();
    private String awayShortName;
    private String[] gameDateTime;
    private String homeShortName;
    private String month;
    private String day;
    private HashMap<String, String> homeCityMap = new HashMap<>();
    private HashMap<String, String> homeNicknameMap = new HashMap<>();
    private XSSFWorkbook sportDataWorkbook;
    private XSSFSheet sportDataSheet;
    private Integer excelRowIndex;
    public static HashMap<String, String> getGameIdentifierMap(String dataEventId)
    {
        return gameIdentifierMap;
    }
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    public void collectTeamDataForThisWeek(List<WebElement> weekEventElements)//From covers.com website for this week's matchups
    {
        sportDataSheet = sportDataWorkbook.getSheet("Data");
        int minute = LocalTime.now().getMinute();
        String javaTime = LocalDate.now() + " " + LocalTime.now().getHour() + ":" + minute;
        sportDataSheet.getRow(0).createCell(0);//Row 1, Column A1, Report time e.g. 10/18/22 13:17
        sportDataSheet.getRow(0).getCell(0).setCellValue(javaTime);
        for (WebElement matchupElement : weekEventElements)//Iterate through all games for this NFL week
        {
            String dataEventId = matchupElement.getAttribute("data-event-id");
            excelRowIndex = excelRowIndexMap.get(dataEventId);
            String homeFullName = matchupElement.getAttribute("data-home-team-fullname-search");//e.g. Dallas
            homeFullName = Main.cityNameMap.get(homeFullName);//To correct for Covers goofy city names
            String awayFullName = matchupElement.getAttribute("data-away-team-fullname-search");//e.g. Miami
            awayFullName = Main.cityNameMap.get(awayFullName);//To correct for Covers goofy city names
            String homeNickname = matchupElement.getAttribute("data-home-team-nickname-search");//e.g. Texans
            String awayNickname = matchupElement.getAttribute("data-away-team-nickname-search");//e.g. Dolphins
            String awayShortName = matchupElement.getAttribute("data-away-team-shortname-search");//e.g. MIA
            String homeShortName = matchupElement.getAttribute("data-home-team-shortname-search");//e.g. DAL
            homeCompleteName = homeFullName + " " + homeNickname;//e.g. Miami Dolphins
            awayCompleteName = awayFullName + " " + awayNickname;
            gameIdentifier = season + " - " + awayCompleteName + " @ " + homeCompleteName;//Column A1, gameIentifier e.g. 2022-Buffalo Bills @ Los Angles Rams
            gameIdentifierMap.put(dataEventId, gameIdentifier);
            sportDataSheet.getRow(excelRowIndex).createCell(0);
            sportDataSheet.getRow(excelRowIndex).getCell(0).setCellValue(gameIdentifier);
            sportDataSheet.getRow(excelRowIndex).createCell(1);//Column B2, date e.g. 2022-10-06
            sportDataSheet.getRow(excelRowIndex).getCell(1).setCellValue(weekDate);
            sportDataSheet.getRow(excelRowIndex).createCell(2);//Column C3, Season
            sportDataSheet.getRow(excelRowIndex).getCell(2).setCellValue(season);
            sportDataSheet.getRow(excelRowIndex).createCell(3);//Column D4 NFL week e.g. 5
            sportDataSheet.getRow(excelRowIndex).getCell(3).setCellValue("Week " + weekNumber);
            sportDataSheet.getRow(excelRowIndex).createCell(10);// Column K11, Home team complete name e.g. Dallas Coyboys
            sportDataSheet.getRow(excelRowIndex).getCell(10).setCellValue(homeCompleteName);
            sportDataSheet.getRow(excelRowIndex).createCell(11);// Column L12, Home team short name e.g. DAL
            sportDataSheet.getRow(excelRowIndex).getCell(11).setCellValue(homeShortName);
            sportDataSheet.getRow(excelRowIndex).createCell(25);// Column Z26, Away team complete name e.g. Dallas Coyboys
            sportDataSheet.getRow(excelRowIndex).getCell(25).setCellValue(awayCompleteName);
            sportDataSheet.getRow(excelRowIndex).createCell(26);// Column AA27, Away team short name e.g. DAL
            sportDataSheet.getRow(excelRowIndex).getCell(26).setCellValue(awayShortName);
        }
    }
    public void collectOverall(String dataEventId)
    {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#public > a"))).click();//Click on Overall option in consensus page
        this.gameIdentifier = gameIdentifierMap.get(dataEventId);
        excelRowIndex = excelRowIndexMap.get(dataEventId);
//        String ouAway = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.covers-CoversConsensusDetailsTable-row:nth-child(7) > div:nth-child(1) > div:nth-child(1)"))).getText();//OU Away
//        sportDataSheet.getRow(excelRowIndex).createCell(70);
//        sportDataSheet.getRow(excelRowIndex).getCell(70).setCellValue(ouAway);
//        System.out.println("DC153...ouAway => " + ouAway);
//        String ouHome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#consensus_analysis_content > div:nth-child(16) > div.covers-CoversConsensusDetailsTable-homeFinal > div"))).getText();//BU73 OU Home***BAD
//        System.out.println("DC155...ouHome => ouHome "+ ouHome);
//        sportDataSheet.getRow(excelRowIndex).createCell(72);
//        sportDataSheet.getRow(excelRowIndex).getCell(72).setCellValue(ouHome);
        String atsHome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div .covers-CoversConsensusDetailsTable-homeFinal .covers-CoversConsensusDetailsTable-finalWagersRight"))).getText();//BO67 ATS Home
        System.out.println("DC159...atsHome => "+ atsHome);
        sportDataSheet.getRow(excelRowIndex).createCell(66);
        sportDataSheet.getRow(excelRowIndex).getCell(66).setCellValue(atsHome);
//        String atsAway = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.covers-CoversConsensusDetailsTable-row:nth-child(18) > div:nth-child(1) > div:nth-child(1)"))).getText();//BM65 ATS Away
//        System.out.println("DC163...atsAway => ouHome "+ atsAway);
//        sportDataSheet.getRow(excelRowIndex).createCell(64);
//        sportDataSheet.getRow(excelRowIndex).getCell(64).setCellValue(atsAway);
    }
    public void collectMoneyLeaders(String dataEventId)
    {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li.covers-CoversConsensus-sides:nth-child(1) > a:nth-child(1)"))).click();//Click on Money Leaders option in consensus page

        //driver.findElement(By.cssSelector("li.covers-CoversConsensus-sides:nth-child(1) > a:nth-child(1)")).click();
        this.gameIdentifier = gameIdentifierMap.get(dataEventId);
        excelRowIndex = excelRowIndexMap.get(dataEventId);
//        String atsAway = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.covers-CoversConsensusDetailsTable-row:nth-child(2) > div:nth-child(1) > div:nth-child(2)"))).getText();//BP68 ATS Away
//        sportDataSheet.getRow(excelRowIndex).createCell(67);
//        sportDataSheet.getRow(excelRowIndex).getCell(67).setCellValue(atsAway);
//        System.out.println("DC173...ATS away => " + gameIdentifier + " " + atsAway);
        String atsHome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.covers-CoversConsensusDetailsTable-row:nth-child(2) > div:nth-child(2) > div:nth-child(2)"))).getText();//BQ69 ATS Home
        sportDataSheet.getRow(excelRowIndex).createCell(68);
        sportDataSheet.getRow(excelRowIndex).getCell(68).setCellValue(atsHome);
        System.out.println("DC177...ATS home for => " + gameIdentifier + " " + atsHome);
//        String ouAway = driver.findElement(By.cssSelector("div.covers-CoversConsensusDetailsTable-row:nth-child(3) > div:nth-child(1) > div:nth-child(2)")).getText();//BV74 OU Away######bad
//        sportDataSheet.getRow(excelRowIndex).createCell(73);
//        sportDataSheet.getRow(excelRowIndex).getCell(73).setCellValue(ouAway);
//        System.out.println("DC182...OU away => " + gameIdentifier + " " + ouAway);
//        String ouHome = driver.findElement(By.cssSelector("div.covers-CoversConsensusDetailsTable-row:nth-child(3) > div:nth-child(2) > div:nth-child(2)")).getText();//BW75 OU Home#####bad
//        sportDataSheet.getRow(excelRowIndex).createCell(74);
//        sportDataSheet.getRow(excelRowIndex).getCell(74).setCellValue(ouHome);
//        System.out.println("DC185...OU home => " + gameIdentifier + " " + ouHome);
    }
    public void setSportDataWorkbook(XSSFWorkbook sportDataWorkbook)
    {
        this.sportDataWorkbook = sportDataWorkbook;
    }
    public String getGameIdentifier()
    {
        return gameIdentifier;
    }
}