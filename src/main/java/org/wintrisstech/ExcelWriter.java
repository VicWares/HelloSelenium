package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 221104 HelloSeleniumX2
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
public class ExcelWriter
{
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private OutputStream os;
    public void writeSportData(XSSFWorkbook sportDataWorkbook)
    {
        System.out.println("EW20 Writing to desktop");
        try
        {
            os = new FileOutputStream(deskTopPath + "/SportData.xlsx");
            sportDataWorkbook.write(os);
            os.close();
        }
        catch (Exception e)
        {
            System.out.println("EW27 SportDataBook write failure");        }
    }
}
