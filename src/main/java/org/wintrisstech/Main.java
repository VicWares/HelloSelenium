package org.wintrisstech;
/**********************************************************************************
 * version 230603
 * Teams going west have a circadian disadvantage
 **********************************************************************************/

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import static org.wintrisstech.CityNameMapBuilder.*;
import static org.wintrisstech.BigNFLbookReader.*;
public class Main
{
    private static XSSFWorkbook book1 = new XSSFWorkbook();//Output workbook
    private static String version = "version 230603";
    static String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private static double homeScore;
    private static double awayScore;
    private static FileOutputStream os;
    public static String homeNumber;
    public static String awayNumber;
    public static void main(String[] args) throws IOException, InterruptedException, InvalidFormatException
    {
        new BigNFLbookReader();
        new CityNameMapBuilder();
        bigNFLsheet = BigNFLbookReader.bigNFLsheet;
        System.out.println("SharpMarkets, version " + version + ", Copyright 2023 Dan Farris");
        System.out.println("Main37 START MAIN LOOP ***************************BigNFLsheet size: " + bigNFLsheet.getLastRowNum() + "**************************************** START MAIN LOOP");
        for (int i = 3; i < bigNFLsheet.getLastRowNum(); i++)//Start of main loop interating through BigNFL.xlsx...all games in history
        {
            System.out.print("=======> row[" + i + "] ...");
            teamCityGenerator(cityNameMap, i);//Gets proper city name for home and away teams
            homeScore = bigNFLsheet.getRow(i).getCell(20).getNumericCellValue();//Home Team Score
            awayScore = bigNFLsheet.getRow(i).getCell(31).getNumericCellValue();//Away Team Score
            if (homeScore > awayScore)//Home Team Wins, so put home team number in the cell 6
            {
                System.out.println("Main43******>homeWin " + CityNameMapBuilder.homeCity +":" + (int)homeScore + "/" + CityNameMapBuilder.awayCity + ":" + (int)awayScore);
            }
            if (awayScore > homeScore)//Away Team Wins, so put away team number in the cell 7
            {
                System.out.println("Main47======>awayWin " + CityNameMapBuilder.awayCity +":" + (int)awayScore + "/" + CityNameMapBuilder.homeCity + ":" + (int)homeScore);
            }
            if (awayScore == homeScore)
            {
                System.out.println(" <TIE> Main51======>awayWin " + CityNameMapBuilder.awayCity +":" + (int)awayScore + "/" + CityNameMapBuilder.homeCity + ":" + (int)homeScore);
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
    private static void teamCityGenerator(Map<String, String> cityNameMap, int i)
    {
        String[] awayArray = bigNFLsheet.getRow(i).getCell(10).getStringCellValue().split(" ");//Away Team's Full Names
        String[] homeArray = bigNFLsheet.getRow(i).getCell(21).getStringCellValue().split(" ");//Home Team's Full Names
        if(homeArray.length == 2)// 2 word team name e.g. Buffalo Bills
        {
            CityNameMapBuilder.homeCity = homeArray[0].trim();//e.g. Buffalo
        }
        if(homeArray.length == 3)// 3 word team name e.g. Los Angeles Rams
        {
            CityNameMapBuilder.homeCity = homeArray[0].trim() + " " + homeArray[1];//e.g. Los Angeles
        }
        if(awayArray.length == 2)// 2 word team name e.g. Buffalo Bills
        {
            CityNameMapBuilder.awayCity = awayArray[0].trim();//e.g. Buffalo
        }
        if(awayArray.length == 3)// 3 word team name e.g. Los Angeles Rams
        {
            CityNameMapBuilder.awayCity = awayArray[0].trim() + " " + awayArray[1];//e.g. Los Angeles
        }
    }
}
