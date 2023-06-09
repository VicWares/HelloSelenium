package org.wintrisstech;
/**********************************************************************************
 * version 230609
 * Teams going west have a circadian disadvantage
 **********************************************************************************/

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.wintrisstech.BigNFLbookReader.bigNFLsheet;
import static org.wintrisstech.CityNameMapBuilder.cityNameMap;

public class Main
{
    private static XSSFWorkbook book1 = new XSSFWorkbook();//Output workbook
    private static XSSFSheet sheet1 = book1.createSheet("Data");//Output sheet
    private static String version = "version 230609";
    static String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private static double homeScore;
    private static double awayScore;
    private static FileOutputStream os;
    public static String homeNumber;
    public static String awayNumber;
    public static String homeName;
    public static String teamName;
    public static String winCity;
    private static String awayName;
    private static String[] homeNameArray;
    private static String[] awayNameArray;

    public static void main(String[] args) throws IOException, InterruptedException, InvalidFormatException
    {
        new BigNFLbookReader();
        new CityNameMapBuilder();
        cityNameMap = CityNameMapBuilder.cityNameMap;
        bigNFLsheet = BigNFLbookReader.bigNFLsheet;
        System.out.println("SharpMarkets, version " + version + ", Copyright 2023 Dan Farris");
        System.out.println("Main37 START MAIN LOOP ***************************BigNFLsheet size: " + bigNFLsheet.getLastRowNum() + "**************************************** START MAIN LOOP");
        for (int i = 3; i < bigNFLsheet.getLastRowNum(); i++)//Start of main loop interating through BigNFL.xlsx...all games in history
        {
            System.out.print("=======> row[" + i + "] ...");
            homeNameArray = bigNFLsheet.getRow(i).getCell(10).getStringCellValue().split(" ");//Home Team Name
            homeName = getCleanCityName(homeNameArray);
            awayNameArray = bigNFLsheet.getRow(i).getCell(21).getStringCellValue().split(" ");//Away Team Name
            awayName = getCleanCityName(awayNameArray);
            homeScore = bigNFLsheet.getRow(i).getCell(20).getNumericCellValue();//Home Team Score
            awayScore = bigNFLsheet.getRow(i).getCell(31).getNumericCellValue();//Away Team Score
            if (homeScore > awayScore)//Home Team Wins, so put home team number in the cell 6
            {
                System.out.println("Main50******>homeWin " + homeName + ":" + (int) homeScore + "/" + teamName + ":" + (int) awayScore);
            }
            if (awayScore > homeScore)//Away Team Wins, so put away team number in the cell 7
            {
                System.out.println("Main55======>awayWin " + teamName + ":" + (int) awayScore + "/" + homeName + ":" + (int) homeScore);
            }
            if (awayScore == homeScore) {
                System.out.println(" <TIE> Main59======>TIE " + teamName + ":" + (int) awayScore + "/" + homeName + ":" + (int) homeScore);
            }
            sheet1.createRow(i).createCell(6).setCellValue(teamName);//win team number
        }//End of main loop
        System.out.println("Main63 END MAIN LOOP **********************************" + bigNFLsheet.getLastRowNum() + "**************************************************** END MAIN LOOP");
        try //Writing Book1.xlsx...epoch
        {
            os = new FileOutputStream(deskTopPath + "/Book1.xlsx");
            book1.write(os);
            os.close();
        } catch (Exception e) {
            System.out.println("Main67...problems writing " + deskTopPath + "/Book1.xlsx" + " sheet");
        }
    }

    private static String getCleanCityName(String[] teamNameArray)//
    {
        int teamNameLength = teamNameArray.length;
        switch (teamNameLength)//Get corrected away team City names
        {
            case 1:
                System.out.println("Main79...Name Error...awayNameLength = " + teamNameLength + "is too short");//Single word team name not allowed in BigNFL.xlsx
                break;
            case 2: //Team Name is two words like Carolina Panthers
                teamName = teamNameArray[0];// e.g. Carolina
                break;
            case 3: //Team Name is three words like Los Angeles Rams
                teamName = teamNameArray[0] + " " + teamNameArray[1];// e.g. Los Angeles
                break;
            default:
                System.out.println("Main91...Default  Switch Name Error...");
        }
        return teamName;
    }

    public static String getCityNumber(String cityName)
    {
        String cityNumber = "+++++++++++++++++++++" + cityNameMap.get(cityName).split("&")[0];
        return cityNumber;
    }
}
