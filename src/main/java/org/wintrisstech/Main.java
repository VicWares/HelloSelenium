package org.wintrisstech;
/**********************************************************************************
 * Must be run before Selenium for initial setup
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 230429
 **********************************************************************************/
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
public class Main
{
    private static XSSFWorkbook bigNFLWorkbook = new XSSFWorkbook();
    private static XSSFWorkbook book1 = new XSSFWorkbook();
    private static XSSFSheet sheet1 = book1.createSheet("Sheet1");
    private static XSSFRow row;
    private static String version = "version 230429";
    static String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private static double homeTeamScore;
    private static double awayTeamScore;
    private static XSSFSheet bigNFLSheet;
    private static FileOutputStream os;
    public static void main(String[] args) throws IOException, InterruptedException, InvalidFormatException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2023 Dan Farris");
        CityNameMapBuilder cityNameMapBuilder = new CityNameMapBuilder();//All team names
        try//Reading BigNFL.xlsx
        {
            System.out.println("Main34...reading " + deskTopPath + "/BigNFL.xlsx");
            InputStream is = new FileInputStream(deskTopPath + "/BigNFL.xlsx");
            XSSFWorkbook bigNFLWorkbook = (XSSFWorkbook) WorkbookFactory.create(is);
            is.close();
            bigNFLSheet = bigNFLWorkbook.getSheet("Data");
        }
        catch (Exception e)
        {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>Main65...problems reading " + deskTopPath + "/BigNFL.xlsx" + " sheet");
        }
        System.out.println("Successfully read...BigNFL Sheet, Size => " + bigNFLSheet.getLastRowNum());
        //<START MAIN LOOP> >************************************************************************************************************ <START MAIN LOOP> *************************************************************************************************************
        System.out.println("Main69 START MAIN LOOP ********** BigNFL Sheet Size =>  *********************** START MAIN LOOP");
        for (int i = 3; i < bigNFLSheet.getLastRowNum(); i++)
        {
            row = sheet1.createRow(i);
            row.createCell(50);
            homeTeamScore = bigNFLSheet.getRow(i).getCell(20).getNumericCellValue();// Home Team Score(Zero based)
            awayTeamScore = bigNFLSheet.getRow(i).getCell(31).getNumericCellValue();// Away Team Score Zero based
            if (homeTeamScore > awayTeamScore)
            {
                String homeWin = "1";
                sheet1.getRow(i).getCell(50).setCellValue(homeWin);
            }
            if (awayTeamScore > homeTeamScore)
            {
                String awayWin = "0";
                sheet1.getRow(i).getCell(50).setCellValue(awayWin);
            }
            System.out.println("========> " + sheet1.getRow(i).getCell(50).getStringCellValue());
        }
      try //Writing Book1.xlsx
      {
              os = new FileOutputStream(deskTopPath + "/Book1.xlsx");
              book1.write(os);
              os.close();
          }
          catch (Exception e)
          {
              System.out.println("Main91...problems writing " + deskTopPath + "/Book1.xlsx" + " sheet");
          }
    }
}
