package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 221007 HelloSelenium3
 **********************************************************************************/
import java.util.HashMap;
public class WeekDateMapBuilder
{
    private static HashMap<String, String> weekDateMap = new HashMap();
    public HashMap<String, String> WeekDateMapBuilder()
    {
        weekDateMap.put("1", "2022-09-08");//Season 2022 start...Week 1
        weekDateMap.put("2", "2022-09-15");//Weeks start on Thursdays
        weekDateMap.put("3", "2022-09-22");
        weekDateMap.put("4", "2022-09-29");
        weekDateMap.put("5", "2022-10-06");
        weekDateMap.put("6", "2022-10-13");
        weekDateMap.put("7", "2022-10-20");
        weekDateMap.put("8", "2022-10-27");
        weekDateMap.put("9", "2022-11-03");
        weekDateMap.put("10", "2022-11-10");
        weekDateMap.put("11", "2022-11-17");
        weekDateMap.put("12", "2022-11-24");
        weekDateMap.put("13", "2022-12-01");
        weekDateMap.put("14", "2022-12-08");
        weekDateMap.put("15", "2022-12-15");
        weekDateMap.put("16", "2022-12-22");
        weekDateMap.put("17", "2022-12-29");
        weekDateMap.put("18", "2023-01-08");
        weekDateMap.put("19", "2023-02-05");
        return weekDateMap;
    }
    public static HashMap<String, String> getWeekDateMap()
    {
        return weekDateMap;
    }
}
