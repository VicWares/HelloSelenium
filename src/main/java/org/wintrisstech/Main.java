package org.wintrisstech;
/**********************************************************************************
 * Must be run before Selenium for initial setup
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 230517
 * Teams going west have a circadian disadvantage
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
    private static String version = "version 230517";
    static String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private static double homeTeamScore;
    private static double awayTeamScore;
    private static XSSFSheet bigNFLSheet;
    private static FileOutputStream os;
    private static String homeTeamName;
    private static String homeTeamNumber;
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
        System.out.println("Main69 START MAIN LOOP ********************************* START MAIN LOOP");
        sheet1.createRow(0).createCell(11).setCellValue("Home Team Abbreviation");
        for (int i = 3; i < bigNFLSheet.getLastRowNum(); i++)
        {//Start of main loop
            String teamAbbreviation = bigNFLSheet.getRow(i).getCell(11).getStringCellValue();//Home Team Abbreviation
            //sheet1.createRow(i).createCell(11).setCellValue(teamAbbreviation);//Home Team Abbreviation
            homeTeamScore = bigNFLSheet.getRow(i).getCell(20).getNumericCellValue();//Home Team Score
            awayTeamScore = bigNFLSheet.getRow(i).getCell(31).getNumericCellValue();//Away Team Score
            String teamNames = bigNFLSheet.getRow(i).getCell(0).getStringCellValue().split("-")[1];//after removing date "-" split e.g. Jacksonville Jaguars @ Washington Commanders
            String awayTeamName = teamNames.split("@")[0];//e.g.Jacksonville Jaguars
            String awayCityName = awayTeamName.split(" ")[0];//e.g. Jacksonville
            String homeTeamName = teamNames.split("@")[1];//e.g Cleveland Browns
            String homeCityName = homeTeamName.split(" ")[0];//e.g. Cleveland
        if (homeTeamScore > awayTeamScore)
            {
                System.out.println("Main64 Home Team Wins => " + homeCityName);
            }
            if (awayTeamScore > homeTeamScore)
            {
                System.out.println("Main68 Away Team Wins => " + awayCityName);
            }
        }//End of main loop
        System.out.println("Main69 END MAIN LOOP ********************************* END MAIN LOOP");
        try //Writing Book1.xlsx...desired output
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
