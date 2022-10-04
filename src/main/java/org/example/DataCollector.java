package org.example;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version HelloSelenium 220930
 * Builds data event id array and calendar date array
 * version 221003A
 *******************************************************************/
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
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
    private String homeTeamNickname;//e.g. Browns...data-home-team-nickname-search
    private String awayTeamNickname;//e.g Texans...data-away-team-nickname-search
    private String awayTeamFullName;//e.g. Cleveland...data-home-team-fullname-search
    private String homeTeamFullName;//e.g Houston...data-home-team-fullname-search
    private String awayTeamCompleteName;//e.g. Kansas City Chiefs
    private String homeTeamCompleteName;//e.g Houston Texans
    private String gameIdentifier;//e.g 2020 - Houston Texans @ Kansas City Chiefs
    private String awayTeamScore;
    private String homeTeamScore;
    private String gameDate;
    private String awayTeamCity;
    private String homeTeamCity;
    private String thisWeek;
    private String thisSeason = "2022";
    private ArrayList<String> thisGameWeekNumbers = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekAwayTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeams = new ArrayList<String>();
    private ArrayList<String> atsHomes = new ArrayList<String>();
    private ArrayList<String> thisWeekAwayTeams = new ArrayList<String>();
    private HashMap<String, String> gameDateMap = new HashMap<>();
    private HashMap<String, String> gameIdentifierMap = new HashMap<>();
    private HashMap<String, String> homeFullNameMap = new HashMap<>();
    private HashMap<String, String> awayFullNameMap = new HashMap<>();
    private HashMap<String, String> homeNicknameMap = new HashMap<>();//e.g. LAR, BUF
    private HashMap<String, String> atsHomesMap = new HashMap<>();
    private HashMap<String, String> atsAwaysMap = new HashMap<>();
    private HashMap<String, String> ouUndersMap = new HashMap<>();
    private HashMap<String, String> ouOversMap = new HashMap<>();
    private HashMap<String, String> cityNameMap = new HashMap<>();
    private HashMap<String, String> idXref = new HashMap<>();
    private HashMap<String, String> homeTotalOpenOddsMap = new HashMap<>();
    private HashMap<String, String> homeTotalCloseOddsMap = new HashMap<>();
    private HashMap<String, String> awayCityMap = new HashMap<>();
    private HashMap<String, String> homeCityMap = new HashMap<>();
    private HashMap<String, String> homeCityPlusNicknameMap = new HashMap<>();
    private HashMap<String, String> awayCityPlusNicknameMap = new HashMap<>();
    private String[] bet365OddsArray = new String[6];
    private HashMap<String, String> homeTeamCompleteNameMap = new HashMap<>();
    private HashMap<String, String> awayTeamCompleteNameMap = new HashMap<>();
    private HashMap<String, String> awayShortNameMap = new HashMap<>();
    private HashMap<String, String> homeShortNameMap = new HashMap<>();
    private String awayShortName;
    private String[] gameDateTime;
    private String homeShortName;
    private String month;
    private String day;
    public void collectTeamInfo(String dataEventId)//From covers.com website for this week's matchups
    {
        System.out.println("DC90 Collecting team info");
        {
            WebElement awayCityNameElement = Main.driver.findElement(By.cssSelector("div.cmg_game_data.cmg_matchup_game_box[data-event-id='" + dataEventId + "'][data-away-team-city-search]"));//Away City  e.g. Los Angeles, Z26 TODO:should be Buffalo Bills
            String awayCity = awayCityNameElement.getAttribute("data-away-team-city-search").toString();
            awayCityMap.put(dataEventId, awayCity);

            WebElement homeCityNameElement = Main.driver.findElement(By.cssSelector("div.cmg_game_data.cmg_matchup_game_box[data-event-id='" + dataEventId + "'][data-home-team-city-search]"));//Home City  e.g. Los Angeles, K11 TODO:should be Buffalo Bills
            String homeCity = homeCityNameElement.getAttribute("data-home-team-city-search").toString();
            homeCityMap.put(dataEventId, homeCity);

            WebElement homeShortNameElement = Main.driver.findElement(By.cssSelector("div.cmg_game_data.cmg_matchup_game_box[data-event-id='" + dataEventId + "'][data-home-team-shortname-search]"));//Home team short name e.g. LAR  Column L12
            String homeShortName = homeShortNameElement.getAttribute("data-home-team-shortname-search").toString();
            homeShortNameMap.put(dataEventId, homeShortName);

            WebElement awayShortNameElement = Main.driver.findElement(By.cssSelector("div.cmg_game_data.cmg_matchup_game_box[data-event-id='" + dataEventId + "'][data-away-team-shortname-search]"));//Away team short e.g. Saints, column AA27
            String awayShortName = awayShortNameElement.getAttribute("data-away-team-shortname-search").toString();
            awayShortNameMap.put(dataEventId, awayShortName);

            WebElement homeNicknameElement = Main.driver.findElement(By.cssSelector("div.cmg_game_data.cmg_matchup_game_box[data-event-id='" + dataEventId + "'][data-home-team-nickname-search]"));//Home team nickname e.g. Bills
            String homeNickname = homeNicknameElement.getAttribute("data-home-team-nickname-search").toString();
            homeNicknameMap.put(dataEventId, homeNickname);

            WebElement awayNicknameElement = Main.driver.findElement(By.cssSelector("div.cmg_game_data.cmg_matchup_game_box[data-event-id='" + dataEventId + "'][data-away-team-nickname-search]"));//away team nickname e.g. Bills
            String awayNickname = homeNicknameElement.getAttribute("data-away-team-nickname-search").toString();
            homeNicknameMap.put(dataEventId, homeNickname);

            WebElement gameDatelement = Main.driver.findElement(By.cssSelector("div.cmg_game_data.cmg_matchup_game_box[data-event-id='" + dataEventId + "'][data-game-date]"));//game date e.g  2022-09-08
            String gameDate = gameDatelement.getAttribute("data-game-date").toString().split(" ")[0];//remove time
            gameDateMap.put(dataEventId, gameDate);

            String homeCityPlusNickname = homeCity + " " + homeNickname;
            homeCityPlusNicknameMap = new HashMap<>();
            homeCityPlusNicknameMap.put(dataEventId, homeCityPlusNickname);

            String awayCityPlusNickname = awayCity + " " + awayNickname;
            awayCityPlusNicknameMap = new HashMap<>();
            awayCityPlusNicknameMap.put(dataEventId, awayCityPlusNickname);


            //homeTeamFullName = e.findElement(By.cssSelector("[data-home-team-fullname-search]")).getText();//e.g. Houston.
//            homeTeamNickname = e.attr("data-home-team-nickname-search");//e.g. Texans
           // homeShortName = weekElements.attr("data-home-team-shortname-search");//Home team abbreviation e.g. LAR
//            awayTeamShortName = weekElements.attr("data-away-team-shortname-search");//Home team abbreviation e.g. BUF
//            homeTeamCity = e.attr("data-home-team-city-search");
//            homeTeamCity = cityNameMap.get(homeTeamCity);
//            homeTeamCompleteName = homeTeamCity + " " + homeTeamNickname;
//            awayTeamFullName = e.attr("data-away-team-fullname-search");//e.g. Dallas
//            awayTeamNickname = e.attr("data-away-team-nickname-search");//e.g. Cowboys
//            awayTeamCity = e.attr("data-away-team-city-search");
//            awayTeamCity = cityNameMap.get(awayTeamCity);
//            awayTeamCompleteName = awayTeamCity + " " + awayTeamNickname;
            gameIdentifier = thisSeason + " - " + awayCityPlusNickname + " @ " + homeCityPlusNickname;
//            dataEventId = e.attr("data-event-id");
//            gameDateTime = e.attr("data-game-date").split(" ");
//            gameDate = gameDateTime[0];
//            awayTeamScore = e.attr("data-away-score");
//            thisWeek = e.attr("data-competition-type");
            gameDateMap.put(dataEventId, gameDate);
            gameIdentifierMap.put(dataEventId, gameIdentifier);
            thisWeekHomeTeams.add(homeTeamCompleteName);
            thisWeekAwayTeams.add(awayTeamCompleteName);
            homeFullNameMap.put(dataEventId, homeTeamFullName);
            awayFullNameMap.put(dataEventId, awayTeamFullName);
            homeNicknameMap.put(dataEventId, homeShortName);
            homeTeamCompleteNameMap.put(dataEventId, homeTeamCompleteName);
            awayTeamCompleteNameMap.put(dataEventId, awayTeamCompleteName);
            thisWeekHomeTeamScores.add(homeTeamScore);
            thisWeekAwayTeamScores.add((awayTeamScore));
            thisGameWeekNumbers.add(thisWeek);
//            awayShortName = e.attr("data-away-team-shortname-search");//Away team
//            awayShortNameMap.put(dataEventId, awayShortName);
//            homeShortName = e.attr("data-home-team-shortname-search");//Home team
//            homeShortNameMap.put(dataEventId, homeShortName);
        }
    }
    public void collectConsensusData(ArrayList<WebElement> thisMatchupConsensusList, String thisMatchupID)
    {
        this.dataEventId = thisMatchupID;
        String ouOver = null;
        String ouUnder = null;
        String atsHome = null;
        String atsAway = null;
//        WebElement rightConsensus = thisMatchupConsensusList.select(".covers-CoversConsensusDetailsTable-finalWagersright");//Home/Under
//        WebElement leftConsensus = thisMatchupConsensusList.select(".covers-CoversConsensusDetailsTable-finalWagersleft");//Away/Over
        try//To catch missing consensus data due to delayed or cancelled game
        {
//            ouUnder = rightConsensus.select("div").get(1).text();
//            ouOver = leftConsensus.select("div").get(1).text();
//            atsHome = leftConsensus.select("div").get(0).text();
//            atsAway = rightConsensus.select("div").get(0).text();
        }
        catch (Exception e)
        {
            System.out.println("DC121 DataCollector, no consensus data for " + gameIdentifier);
        }
        ouOversMap.put(thisMatchupID, ouOver);
        ouUndersMap.put(thisMatchupID, ouUnder);
        atsHomesMap.put(thisMatchupID, atsAway);
        atsAwaysMap.put(thisMatchupID, atsHome);
    }
    public void collectTotalHomeCloseOdds(String dataEventId)
    {
        try
        {
            System.out.println("DC169 Starting collectTotalHomeCloseOdds()");
            String totalHomeCloseOdds = String.valueOf(Main.driver.findElement(By.cssSelector("#__totalDiv-nfl-265308 > table:nth-child(2) > tbody:nth-child(3) > tr:nth-child(2) > td:nth-child(9) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1) > div:nth-child(1)")));
            System.out.println("DC171 totalHomeCloseOdds => " + totalHomeCloseOdds);
        }
        catch (Exception e)
        {
            System.out.println("DC175 Can't find totalHomeCloseOdds");
            throw new RuntimeException(e);
        }
    }
    public HashMap<String, String> getHomeFullNameMap()
    {
        return homeFullNameMap;
    }
    public HashMap<String, String> getAwayFullNameMap()
    {
        return awayFullNameMap;
    }
    public HashMap<String, String> getGameDateMap() {return gameDateMap;}
    public HashMap<String, String> getAtsHomesMap()
    {
        return atsHomesMap;
    }
    public HashMap<String, String> getAtsAwaysMap()
    {
        return atsAwaysMap;
    }
    public HashMap<String, String> getOuAwayMap()
    {
        return ouOversMap;
    }
    public HashMap<String, String> getOuHomeMap()
    {
        return ouUndersMap;
    }
    public HashMap<String, String> getGameIdentifierMap()
    {
        return gameIdentifierMap;
    }
    public void setCityNameMap(HashMap<String, String> cityNameMap)
    {
        this.cityNameMap = cityNameMap;
    }
    public HashMap<String, String> getAwayShortNameMap()
    {
        return awayShortNameMap;
    }
    public HashMap<String, String> getHomeNicknameMap()
    {
        return homeNicknameMap;
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
    public HashMap<String, String> getAwayCityMap()
    {
        return awayCityMap;
    }
    public HashMap<String, String> getHomeCityMap()
    {
        return homeCityMap;
    }
    public HashMap<String, String> getHomeCityPlusNicknameMap()
    {
        return homeCityPlusNicknameMap;
    }
    public HashMap<String, String> getAwayCityPlusNicknameMap()
    {
        return awayCityPlusNicknameMap;
    }
    public HashMap<String, String> getHomeShortNameMap()
    {
        return homeShortNameMap;
    }
}

