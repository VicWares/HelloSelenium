package org.wintrisstech;
/**********************************************************************************
 * Must be run before Selenium for initial setup
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 230601
 * Teams going west have a circadian disadvantage
 **********************************************************************************/

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import static org.wintrisstech.BigNFLbookReader.bigNFLsheet;
public class Main
{
    public static final ThreadLocal<CityNameMapBuilder> cnmb = ThreadLocal.withInitial(() -> new CityNameMapBuilder());
    public static BigNFLbookReader bnbr = new BigNFLbookReader();
    private static XSSFWorkbook bigNFLWorkbook = new XSSFWorkbook();
    private static XSSFWorkbook book1 = new XSSFWorkbook();
    private static String version = "version 230530";
    static String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private static double homeScore;
    private static double awayScore;
    private static FileOutputStream os;
    public static String homeNumber;
    public static String awayNumber;
    private static String homeCity;
    private static String awayCity;
    private static Map<String, String> cityNameMap;
    public static void main(String[] args) throws IOException, InterruptedException, InvalidFormatException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2023 Dan Farris");
        XSSFRow row = bigNFLsheet.createRow(0);//Team numbers on first row of Book1.xlsx
        System.out.println("Main37 START MAIN LOOP ***************************BigNFLsheet size: " + bigNFLsheet.getLastRowNum() + "**************************************** START MAIN LOOP");
        for (int i = 3; i < bigNFLsheet.getLastRowNum(); i++)//Start of main loop interating through BigNFL.xlsx...all games in history
        {
            System.out.println("=======>" + i);
            teamCityGenerator(cityNameMap, i);//Gets proper city name for home and away teams
            homeScore = bigNFLsheet.getRow(i).getCell(20).getNumericCellValue();//Home Team Score
            awayScore = bigNFLsheet.getRow(i).getCell(31).getNumericCellValue();//Away Team Score
            if (homeScore > awayScore)//Home Team Wins, so put home team number in the cell 6
            {
                System.out.println("Main46******>homeWin " + CityNameMapBuilder.homeCity +":" + (int)homeScore + "/" + awayCity + ":" + (int)awayScore);
            }
            if (awayScore > homeScore)//Away Team Wins, so put away team number in the cell 7
            {
                System.out.println("Main50======>awayWin " + CityNameMapBuilder.awayCity +":" + (int)awayScore + "/" + homeCity + ":" + (int)homeScore);
            }
            if (awayScore == homeScore)
            {
                System.out.println(" <TIE> Main54======>awayWin " + CityNameMapBuilder.awayCity +":" + (int)awayScore + "/" + homeCity + ":" + (int)homeScore);
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
            homeCity = homeArray[0].trim();//e.g. Buffalo
        }
        if(homeArray.length == 3)// 3 word team name e.g. Los Angeles Rams
        {
            homeCity = homeArray[0].trim() + " " + homeArray[1];//e.g.
        }
        if(awayArray.length == 2)// 2 word team name e.g. Buffalo Bills
        {
            CityNameMapBuilder.awayCity = awayArray[0].trim();//e.g. Buffalo
        }
        if(awayArray.length == 3)// 3 word team name e.g. Los Angeles Rams
        {
            CityNameMapBuilder.awayCity = awayArray[0].trim() + " " + awayArray[1].trim();
        }
    }
}
