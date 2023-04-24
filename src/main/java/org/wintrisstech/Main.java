package org.wintrisstech;
/**********************************************************************************
 * Must be run before Selenium for initial setup
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 230423
 **********************************************************************************/
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
public class Main
{
    private static XSSFWorkbook bigNFLWorkbook = new XSSFWorkbook();
    private static XSSFWorkbook book1 = new XSSFWorkbook();
    private static XSSFSheet Sheet1;
    private static String version = "version 230423";
    private static XSSFSheet BigNFLsheet;
    static String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private static OutputStream os;
    private static double homeTeamScore;
    private static double awayTeamScore;
    private static XSSFSheet sheet1;
    private static XSSFSheet bigNFLSheet;
    static FileOutputStream outputStream;
    static
    {
        try
        {
            outputStream = new FileOutputStream(deskTopPath + "/Book1.xlsx");
        }
        catch (FileNotFoundException e)
        {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Main39...problems creating " + (deskTopPath + "/Book1.xlsx") + " output stream");
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException, InvalidFormatException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2023 Dan Farris");
        new CityNameMapBuilder();//All team names
        try
        {
            System.out.println("XLSXR16...reading " + deskTopPath + "/BigNFL.xlsx");
            InputStream is = new FileInputStream(deskTopPath + "/BigNFL.xlsx");
            XSSFWorkbook bigNFLWorkbook = (XSSFWorkbook) WorkbookFactory.create(is);
            is.close();
            bigNFLSheet = bigNFLWorkbook.getSheet("Data");        }
        catch (Exception e)
        {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>Main65...problems reading " + deskTopPath + "/BigNFL.xlsx" + " sheet");
        }
        System.out.println("Successfully read...BigNFL Sheet, Size => " + bigNFLSheet.getLastRowNum());
        //<START MAIN LOOP> >************************************************************************************************************ <START MAIN LOOP> *************************************************************************************************************
        System.out.println("Main69 START MAIN LOOP ********** BigNFL Sheet Size =>  *********************** START MAIN LOOP");
        sheet1 = book1.createSheet("Sheet1");
        for (int i = 3; i < bigNFLSheet.getLastRowNum(); i++)
        {
            homeTeamScore = bigNFLSheet.getRow(i).getCell(20).getNumericCellValue();// Home Team Score(Zero based)
            awayTeamScore = bigNFLSheet.getRow(i).getCell(31).getNumericCellValue();// Away Team Score Zero based
            if (homeTeamScore > awayTeamScore)
            {
                String homeWin = "1";
                sheet1.createRow(i).createCell(6).setCellValue(homeWin);
            }
            if (awayTeamScore > homeTeamScore)
            {
                String awayWin = "0";
                sheet1.createRow(i).createCell(6).setCellValue(awayWin);
            }
        }
        for (Row myrow : bigNFLSheet)
        {
            System.out.println("*" + bigNFLSheet.getRow(myrow.getRowNum()).getCell(0).getStringCellValue());
        }
        try
        {
            book1.write(outputStream);
            System.out.println("Main94...writing " + (deskTopPath + "/Book1.xlsx"));
        }
        catch (Exception e)
        {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>Main98...problems writing " + (deskTopPath + "/Book1.xlsx"));
        }
        System.out.println("Main96...finis...successfully wrote " + deskTopPath + "/Book1.xlsx");
        outputStream.close();
    }
}
