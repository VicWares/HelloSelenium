package org.wintrisstech;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.InputStream;

public class BigNFLsheetReader
{
    static String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private static XSSFSheet bigNFLsheet;

    //    private static XSSFSheet bigNFLsheet;
    public BigNFLsheetReader()
    {
        try//Reading BigNFL.xlsx
        {
            InputStream is = new FileInputStream(deskTopPath + "/BigNFL.xlsx");
            XSSFWorkbook bigNFLWorkbook = (XSSFWorkbook) WorkbookFactory.create(is);
            is.close();
            bigNFLsheet = bigNFLWorkbook.getSheet("Data");
        }
        catch (Exception e)
        {
            System.out.println("BNLSR27...problems reading " + deskTopPath + "/BigNFL.xlsx" + " sheet");
        }
    }
    public static XSSFSheet getBigNFLsheet()
    {
        return bigNFLsheet;
    }
}
