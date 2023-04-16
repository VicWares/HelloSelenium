package org.wintrisstech;
/**********************************************************************************
 * Must be run before Selenium for initial setup
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 230415
 **********************************************************************************/
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class Main
{
    private static XSSFWorkbook sportDataWorkbook;
    private static String version = "version 230414";
    private static XSSFSheet BigNFLsheet;
    static String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private static InputStream is;
    private static OutputStream os;
    private static String homeTeam;
    private static String awayTeam;
    private static int matchups = 0;
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2023 Dan Farris");
        try {
            is = new FileInputStream(deskTopPath + "/BigNFL.xlsx");
            sportDataWorkbook = (XSSFWorkbook) WorkbookFactory.create(is);
            BigNFLsheet = sportDataWorkbook.getSheet("Data");
            is.close();
            //os = new FileOutputStream(deskTopPath + "/SportData.xlsx");
        } catch (Exception e) {
            System.out.println("Main52...problems reading/writing deskTopPath SportData.xlsx");
        }
        System.out.println("Main27...successfully read " + deskTopPath + "/BigNFL.xlsx" + " BigNFL Sheet Size => " + BigNFLsheet.getLastRowNum());
        //<START MAIN LOOP> >************************************************************************************************************ <START MAIN LOOP> *************************************************************************************************************
        System.out.println("Main44 START MAIN LOOP ********** BigNFL Sheet Size => " + BigNFLsheet.getLastRowNum() + " *********************** START MAIN LOOP");
        for (int i = 3; i < BigNFLsheet.getLastRowNum(); i++)
        {
            homeTeam = String.valueOf(BigNFLsheet.getRow(i).getCell(11).getStringCellValue());//K10 Home Team (Zero based)
            awayTeam = String.valueOf(BigNFLsheet.getRow(i).getCell(22).getStringCellValue());//V21 Away Team Zero based
            if ((homeTeam.equals("LAR") && awayTeam.equals("BUF"))|| (homeTeam.equals("BUF") && awayTeam.equals("LAR")))
            {
                System.out.println("Main47..." + homeTeam + "/" + awayTeam + " matchup");
                matchups++;
            }
        }
        System.out.println("LAR/BUF matchups " + matchups);
        //<END MAIN LOOP>******************************************************************************************************************<END>***********************************************************************************************************************
        System.out.println("Main58......Completed GreatCovers Successfully...Hooray...");
    }
//        try
//        {
//            sportDataWorkbook.write(os);
//            Main.driver.close();
//        }
//        catch (Exception e)
//        {
//            System.out.println("Main91 Safari web driver close exception");
//        }
}


