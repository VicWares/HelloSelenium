package org.wintrisstech;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.InputStream;
public class BigNFLbookReader
{
    static String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    public static XSSFSheet bigNFLsheet;
    public BigNFLbookReader()
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
            System.out.println("BNLSR22...problems reading " + deskTopPath + "/BigNFL.xlsx" + " sheet");
        }
    }
}
