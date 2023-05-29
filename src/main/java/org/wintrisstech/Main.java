package org.wintrisstech;
/**********************************************************************************
 * Must be run before Selenium for initial setup
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 230519
 * Teams going west have a circadian disadvantage
 **********************************************************************************/

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class Main
{
    private static XSSFWorkbook bigNFLWorkbook = new XSSFWorkbook();
    private static XSSFWorkbook book1 = new XSSFWorkbook();
    private static XSSFSheet sheet1 = book1.createSheet("Data");
    private static String version = "version 230529";
    static String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private static double homeScore;
    private static double awayScore;
    private static XSSFSheet bigNFLSheet;
    private static FileOutputStream os;
    private static String homeTeamName;
    private static String homeTeamNumber;
    private static String awayTeamNumber;
    private static XSSFSheet bigNFLsheet;
    private static String homeCity;
    private static String awayCity;
    private static String awayTeamName;
    static BigNFLsheetReader bigSheetNFLreader = new BigNFLsheetReader();
    static CityNameMapBuilder cityNameMapBuilder = new CityNameMapBuilder();//All team names
    public static void main(String[] args) throws IOException, InterruptedException, InvalidFormatException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2023 Dan Farris");
        bigNFLsheet = bigSheetNFLreader.getBigNFLsheet();
        Map<String, String> cityNameMap = cityNameMapBuilder.buildCityNameMap();
        XSSFRow row = sheet1.createRow(0);//Team numbers on first row of Book1.xlsx
        bigNFLsheet = BigNFLsheetReader.getBigNFLsheet();
        System.out.println("Main46 START MAIN LOOP ***************************" + bigNFLsheet.getLastRowNum() + "**************************************** START MAIN LOOP");
        for (int i = 3; i < bigNFLsheet.getLastRowNum(); i++)//Start of main loop interating through BigNFL.xlsx...all games in history
        {
            System.out.println("=======" + i);
            String homeTeamArray[] = bigNFLsheet.getRow(i).getCell(10).getStringCellValue().split(" ");//Home Team Full Name
            if(homeTeamArray.length == 2)// 2 word team name e.g. Buffalo Bills
            {
                homeCity = homeTeamArray[0];//e.g. Buffalo
            }
            if(homeTeamArray.length == 3)// 3 word team name e.g. Los Angeles Rams
            {
                homeCity = homeTeamArray[0] + " " + homeTeamArray[1];//e.g.
            }
            String homeNumber = cityNameMap.get(homeCity).split("&")[0];//Home Team Number
            String awayTeamArray[] = bigNFLsheet.getRow(i).getCell(21).getStringCellValue().split(" ");//Away Team Full Name
            if(awayTeamArray.length == 2)// 2 word team name e.g. Buffalo Bills
            {
                awayCity = awayTeamArray[0];//e.g. Buffalo
            }
            if(awayTeamArray.length == 3)// 3 word team name e.g. Los Angeles Rams
            {
                awayCity = awayTeamArray[0] + " " + awayTeamArray[1];//e.g.
            }
            String awayNumber = cityNameMap.get(awayCity).split("&")[0];//Away Team Number
            homeScore = bigNFLsheet.getRow(i).getCell(20).getNumericCellValue();//Home Team Score
            awayScore = bigNFLsheet.getRow(i).getCell(31).getNumericCellValue();//Away Team Score
            System.out.println("Main75...team cities away/home => " + awayCity + (int) awayScore + "/" + homeCity + (int) homeScore);
            System.out.println("Main74 Home City => " + homeCity + ", number " + homeNumber);
            System.out.println("Main79 Away City => " + awayCity + ", number " + awayNumber);
            if (homeScore > awayScore)//Home Team Wins, so put home team number in the cell 6
            {
                //sheet1.getRow(i).createCell(6).setCellValue("5");
                System.out.println(sheet1.getRow(i));
                //sheet1.getRow(i).createCell(6).setCellValue(homeTeamNumber);
            }
            if (awayScore > homeScore)//Away Team Wins, so put away team number in the cell 7
            {
               // sheet1.getRow(i).createCell(7).setCellValue(awayTeamNumber);
            }
            if (awayScore == homeScore)
            {
                System.out.println("Main87 TIE");
            }
        }//End of main loop
        System.out.println("Main91 END MAIN LOOP **********************************" + bigNFLsheet.getLastRowNum() + "**************************************************** END MAIN LOOP");
        try //Writing Book1.xlsx...epoch
        {
            os = new FileOutputStream(deskTopPath + "/Book1.xlsx");
            book1.write(os);
            os.close();
        }
        catch (Exception e)
        {
            System.out.println("Main81...problems writing " + deskTopPath + "/Book1.xlsx" + " sheet");
        }
    }

    private static void getCityNumber(Map<String, String> cityNameMap, int i)
    {
        String[] homeTeamNameArray = String.valueOf(bigNFLsheet.getRow(i).getCell(10)).split(" ");//e.g. Chicago Bears or New England Patiots...length 2 or 3
        if (homeTeamNameArray.length == 3)
        {
            homeCity = homeTeamNameArray[0];
        }
        if (homeTeamNameArray.length == 2)
        {
            homeCity = homeTeamNameArray[0] + " " + homeTeamNameArray[1];
        }
        if (homeTeamNameArray.length == 1)
        {
            homeCity = homeTeamNameArray[0];
        }
        String[] awayTeamNameArray = String.valueOf(bigNFLsheet.getRow(i).getCell(21)).split(" ");//e.g. Chicago Bears or New England Patiots...length 2 or 3
        if (awayTeamNameArray.length > 2)
        {
            awayCity = awayTeamNameArray[0] + " " + awayTeamNameArray[1];
        }
        else
        {
            awayCity = awayTeamNameArray[0];
        }
        homeTeamNumber = cityNameMap.get(homeCity);//.split("&")[0];
        awayTeamNumber = cityNameMap.get(awayCity);//.split("&")[0];
    }
}
