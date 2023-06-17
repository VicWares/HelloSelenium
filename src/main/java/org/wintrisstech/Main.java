package org.wintrisstech;
/**********************************************************************************
 * version 230617A
 * Teams going west have a circadian disadvantage
 **********************************************************************************/

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.wintrisstech.BigNFLbookReader.bigNFLsheet;
import static org.wintrisstech.CityNameMapBuilder.cityNameMap;

public class Main {
    private static XSSFWorkbook book1 = new XSSFWorkbook();//Output workbook
    private static XSSFSheet sheet1 = book1.createSheet("Data");//Output sheet
    private static String version = "version 230617A";
    static String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private static double homeScore;
    private static double awayScore;
    private static FileOutputStream os;
    public static String homeNumber;
    public static String awayNumber;
    public static String homeCity;
    public static String cityName;
    public static String winCity;
    private static String awayCity;
    private static String[] homeNameArray;
    private static String[] awayNameArray;
    private static String cityNumber;
    private static String cityTime;
    private static String homeTime;
    private static String awayTime;
    private static int predictedWinCount;

    public static void main(String[] args) throws IOException, InterruptedException, InvalidFormatException {
        new BigNFLbookReader();
        new CityNameMapBuilder();
        cityNameMap = CityNameMapBuilder.cityNameMap;
        bigNFLsheet = BigNFLbookReader.bigNFLsheet;
        System.out.println("SharpMarkets, version " + version + ", Copyright 2023 Dan Farris");
        CellStyle style = book1.createCellStyle();
        style.setWrapText(true);
        XSSFRow row0 = sheet1.createRow(0);
        row0.createCell(0).setCellValue("Home\nTeam");
        row0.createCell(1).setCellValue("Away\nTeam");
        row0.createCell(2).setCellValue("Delta\nTime");
        row0.createCell(3).setCellValue("Win\nTeam");
        row0.getCell(0).setCellStyle(style);
        row0.getCell(1).setCellStyle(style);
        row0.getCell(2).setCellStyle(style);
        row0.getCell(3).setCellStyle(style);
        System.out.println("*****************Main37 START MAIN LOOP ***************************BigNFLsheet size: " + bigNFLsheet.getLastRowNum() + "**************************************** START MAIN LOOP");
        for (int i = 3; i < bigNFLsheet.getLastRowNum(); i++)//Start of main loop interating through BigNFL.xlsx...all games in history
        {
            System.out.print("row[" + i + "] ");
            if (sheet1.getRow(i) == null)//If the row doesn't exist, create it
            {
                sheet1.createRow(i);
            }
            homeNameArray = bigNFLsheet.getRow(i).getCell(10).getStringCellValue().split(" ");//Home Team Name
            awayNameArray = bigNFLsheet.getRow(i).getCell(21).getStringCellValue().split(" ");//Away Team Name
            homeCity = getCleanCityName(homeNameArray);
            awayCity = getCleanCityName(awayNameArray);
            homeNumber = getCityNumber(homeCity);
            awayNumber = getCityNumber(awayCity);
            homeTime = getCityTime(homeCity);
            awayTime = getCityTime(awayCity);
            int deltaCityTime = Integer.parseInt(awayTime) - Integer.parseInt(homeTime);
            String deltaCityTimeString = Integer.toString(deltaCityTime);
            homeScore = bigNFLsheet.getRow(i).getCell(20).getNumericCellValue();//Home Team Score
            awayScore = bigNFLsheet.getRow(i).getCell(31).getNumericCellValue();//Away Team Score
            if (homeScore > awayScore)//Home Team Wins, so put home team number in the cell 3
            {
                printWin("", "**=>");
                sheet1.getRow(i).createCell(3).setCellValue(homeNumber);//Put winning home team number in the cell 3
            }
            if (awayScore > homeScore)//Away Team Wins, so put away team number in the cell 3
            {
                printWin("**=>", "");
                sheet1.getRow(i).createCell(3).setCellValue(awayNumber);//Put winning away team number in the cell 3
            }
            if (awayScore == homeScore) {
                System.out.print("TIE...");
                sheet1.getRow(i).createCell(3).setCellValue("0");//Put winning asay team number in the cell
                printWin("", "");
            }
            sheet1.getRow(i).createCell(0).setCellValue(homeNumber);//Put home team number in the cell 0
            sheet1.getRow(i).createCell(1).setCellValue(awayNumber);//Put away team number in the cell 1
            sheet1.getRow(i).createCell(2).setCellValue(deltaCityTimeString);//Put delta time in the cell 2
            if (Integer.parseInt(deltaCityTimeString) >= 1)
            {
                sheet1.getRow(i).createCell(4).setCellValue(homeNumber);//Put predicted winning home team number in the cell 4
            }
            else
            {
                sheet1.getRow(i).createCell(4).setCellValue("-");//Put predicted winning away team number in the cell 4
            }
            if (sheet1.getRow(i).getCell(3).getStringCellValue().equals(sheet1.getRow(i).getCell(4).getStringCellValue()))
            {
                System.out.println("BINGO...Predicted winner matches actual winner");
                predictedWinCount++;
            }
        }//End of main loop
        System.out.println("****************END MAIN LOOP **********************************" + bigNFLsheet.getLastRowNum() + "**************************************************** END MAIN LOOP");
        System.out.println("Predicted winner % is predicted win  count " + predictedWinCount + "/" + (book1.getSheet("Data").getLastRowNum()-3) + " => " + (predictedWinCount * 100)/(book1.getSheet("Data").getLastRowNum()-3) + "%");
        writeBook1();
        System.out.println("Hooray...Main94...Finished");
    }

    private static void writeBook1() {
        try //Writing Book1.xlsx...epoch
        {
            System.out.println("Main103...Writing " + deskTopPath + "/Book1.xlsx" + " sheet");
            os = new FileOutputStream(deskTopPath + "/Book1.xlsx");
            book1.write(os);
            os.close();
        } catch (Exception e) {
            System.out.println("Main108...problems writing " + deskTopPath + "/Book1.xlsx" + " sheet");
        }
    }

    private static void printWin(String awayWinTag, String homeWinTag) {
        System.out.println("#" + awayNumber + awayWinTag + awayCity + ":" + (int) awayScore + " @ " + "#" + homeNumber + homeWinTag + homeCity + ":" + (int) homeScore);
    }

    private static String getCleanCityName(String[] teamNameArray)//
    {
        int teamNameLength = teamNameArray.length;
        switch (teamNameLength)//Get corrected away team City names
        {
            case 2: //Team Name is two words like Carolina Panthers
                cityName = teamNameArray[0];// e.g. Carolina
                break;
            case 3: //Team Name is three words like Los Angeles Rams
                if (teamNameArray[0].equals("Washimgton"))// Special case for Washington Football Team
                {
                    cityName = "Washington";
                    break;
                } else
                    cityName = teamNameArray[0] + " " + teamNameArray[1];// e.g. Los Angeles
                break;
            default:
                System.out.println("Main95...Default  Switch Name Error...");
        }
        return cityName;
    }

    public static String getCityNumber(String cityName) {
        if (cityName.equals("Washington Football")) //TODO: FIX Special case for Washington Football Team
        {
            cityName = "Washington";
        }
        if (cityName.equals("Oakland")) //TODO: FIX Special case for Oakland Raiders
        {
            cityName = "Oakland";
        }
        try {
            cityNumber = cityNameMap.get(cityName).split("&")[0];
        } catch (Exception e) {
            System.out.println("ERROR Main157..." + cityNameMap.get(cityName));
        }
        return cityNumber;
    }

    private static String getCityTime(String cityName) {
        if (cityName.equals("Washington Football")) //TODO: FIX Special case for Washington Football Team
        {
            cityName = "Washington";
        }
        return cityNameMap.get(cityName).split("&")[2];
    }
}


