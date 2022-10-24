package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 221023 HelloSeleniumx
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

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
    public static HashMap<String, String> gameIdentifierMap = new HashMap<>();
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
    private String atsAway;
    private Integer excelRowIndex;
    private String atsHome;
    public void collectTeamDataForThisWeek()//From covers.com website for this week's matchups
    {
        sportDataSheet = sportDataWorkbook.getSheet("Data");
        int minute = LocalTime.now().getMinute();
        String javaTime = LocalDate.now() + " " + LocalTime.now().getHour() + ":" + minute;
        sportDataSheet.getRow(0).createCell(0);//Row 1, Column A1, Report time e.g. 10/18/22 13:17
        sportDataSheet.getRow(0).getCell(0).setCellValue(javaTime);
        for (String dataEventId : xRefMap.keySet())//Build week matchup IDs array
        {
            excelRowIndex = Main.excelRowIndexMap.get(dataEventId);
            WebElement dataEventIdElement = driver.findElement(By.cssSelector("[data-event-id='" + dataEventId + "']"));//Driver gets all team elements associated with this dataEventId
            String homeFullName = dataEventIdElement.getAttribute("data-home-team-fullname-search");//e.g. Dallas
            String awayFullname = dataEventIdElement.getAttribute("data-away-team-fullname-search");//e.g. Miami
            String homeNickname = dataEventIdElement.getAttribute("data-home-team-nickname-search");//e.g. Texans
            String awayNickname = dataEventIdElement.getAttribute("data-away-team-nickname-search");//e.g. Dolphins
            homeCompleteName = homeFullName + " " + homeNickname;//e.g. Miami Dolphin
            awayCompleteName = awayFullname + " " + awayNickname;
            gameIdentifier = season + " - " + awayCompleteName + " @ " + homeCompleteName;//Column A1, gameIentifier e.g. 2022-Buffalo Bills @ Los Angles Rams
            sportDataSheet.getRow(excelRowIndex).createCell(0);
            sportDataSheet.getRow(excelRowIndex).getCell(0).setCellValue(gameIdentifier);

            sportDataSheet.getRow(excelRowIndex).createCell(1);//Column B2, date e.g. 2022-10-06
            sportDataSheet.getRow(excelRowIndex).getCell(1).setCellValue(weekDate);

            sportDataSheet.getRow(excelRowIndex).createCell(2);//Column C3, Season
            sportDataSheet.getRow(excelRowIndex).getCell(2).setCellValue(season);

            sportDataSheet.getRow(excelRowIndex).createCell(3);//Column D4 NFL week e.g. 5
            sportDataSheet.getRow(excelRowIndex).getCell(3).setCellValue("Week " + Main.weekNumber);

            sportDataSheet.getRow(excelRowIndex).createCell(10);// Column K11, Home team full name e.g. Dallas Coyboys Column K11
            sportDataSheet.getRow(excelRowIndex).getCell(10).setCellValue(homeCompleteName);
        }
    }
    public void collectConsensusData(String dataEventId)
    {
        excelRowIndex = Main.excelRowIndexMap.get(dataEventId);
        atsAway = Main.driver.findElement(By.cssSelector("div.covers-CoversConsensusDetailsTable-finalWagersleft")).getText();
        sportDataSheet.getRow(excelRowIndex).createCell(64);//Column BM65 ATS consensus away
        sportDataSheet.getRow(excelRowIndex).getCell(64).setCellValue(atsAway);

        atsHome = Main.driver.findElement(By.cssSelector("div.covers-CoversConsensusDetailsTable-finalWagersRight")).getText();
        sportDataSheet.getRow(excelRowIndex).createCell(65);//Column BO66 ATS consensus home
        sportDataSheet.getRow(excelRowIndex).getCell(65).setCellValue(atsAway);
    }
    public void collectOddsData()
    {
        driver.findElement(By.cssSelector("a[href='/sport/football/nfl/odds']")).click();//Select Odds button
        for (HashMap.Entry<String,String> entry : xRefMap.entrySet())
        {
            String dataEventId = entry.getKey();
            String dataGame = entry.getValue();
            int excelRowIndex = excelRowIndexMap.get(dataEventId);
            WebElement dataEventIdElement = driver.findElement(By.cssSelector("[data-event-id='" + dataEventId + "']"));//Driver gets all team elements associated with this dataEventId
            System.out.println(".............openOdds=> " + driver.findElement(By.cssSelector(".__american")).getText());
        }
    }

    public HashMap<String, String> getGameIdentifierMap() {return gameIdentifierMap;}
    public void setCityNameMap(HashMap<String, String> cityNameMap)
    {
        this.cityNameMap = cityNameMap;
    }
    public HashMap<String, String> getAwayShortNameMap()
    {
        return awayShortNameMap;
    }
    public HashMap<String, String> getHomeShortNameMap()
    {
        return homeShortNameMap;
    }
    public HashMap<String, String> getHomeTeamCompleteNameMap()
    {
        return homeTeamCompleteNameMap;
    }
    public HashMap<String, String> getAwayTeamCompleteNameMap()
    {
        return awayTeamCompleteNameMap;
    }
    public HashMap<String, String> getGetHomeTotalCloseOddsMap()
    {
        return getHomeTotalCloseOddsMap;
    }
    public HashMap<String, String> getGetTotalHomeOpenOddsMap()
    {
        return getHomeTotalOpenOddsMap;
    }
    public HashMap<String, String> getTotalHomeOpenOddsMap()
    {
        return totalHomeOpenOddsMap;
    }
    public HashMap<String, String> getTotalHomeCloseOddsMap()
    {
        return totalHomeCloseOddsMap;
    }
    public HashMap<String, String> getAwayMLoddsMap()
    {
        return awayMLoddsMap;
    }
    public HashMap<String, String> getHomeMLoddsMap()
    {
        return awayMLoddsMap;
    }
    public void setThisSeason(String thisSeason)
    {
        this.thisSeason = thisSeason;
    }
    public void setSportDataWorkbook(XSSFWorkbook sportDataWorkbook)
    {
        this.sportDataWorkbook = sportDataWorkbook;
    }
}