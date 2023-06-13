package org.wintrisstech;
import java.util.Map;
public class CityNameMapBuilder
{
    public static Map<String, String> cityNameMap = new java.util.HashMap<String, String>();
    public static String awayCity;
    public static String homeCity;
    public CityNameMapBuilder()
    {
        cityNameMap.put("Minneapolis", "1&Minnesota&5");//Minnesota Vikings......cityNumber&cityName&cityTime
        cityNameMap.put("Tampa", "2&Tampa Bay&4");//Tampa Bay Buccaneers
        cityNameMap.put("Tampa Bay", "2&Tampa Bay&4");//Tampa Bay Buccaneers
        cityNameMap.put("Arlington", "3&Dallas&5");//Dallas Cowboys
        cityNameMap.put("Dallas", "3&Dallas&5");//Dallas Cowboys
        cityNameMap.put("Orchard Park", "4&Buffalo&4");//Buffalo Bills
        cityNameMap.put("Buffalo", "4&Buffalo&4");//Buffalo Bills
        cityNameMap.put("Charlotte", "5&Carolina&4");//Carolina Panthers
        cityNameMap.put("Carolina", "5&Carolina&4");//Carolina Panthers
        cityNameMap.put("Arizona", "6&Arizona&6");//Arizona Cardinals
        cityNameMap.put("Tempe", "6&Arizona&6");//Arizona Cardinals
        cityNameMap.put("Foxborough", "7&New England&4");//New England Patriots
        cityNameMap.put("New England", "7&New England&4");//New England Patriots
        cityNameMap.put("East Rutherford", "8&New York&4");//New York Giants and New York Jets
        cityNameMap.put("New York", "8&New York&4");//New York Giants and New York Jets
        cityNameMap.put("Landover", "9&Washington&7");//Washington Football Team
        cityNameMap.put("Washington Football Team", "9&Washington&7");//Washington Football Team
        cityNameMap.put("Washington Commanders", "9&Washington&7");//Washington Football Team
        cityNameMap.put("Washington", "9&Washington&7");//Washington Football Team
        cityNameMap.put("Nashville", "10&Tennessee&5");//Tennessee Titans
        cityNameMap.put("Miami", "11&Miami&4");//Miami Dolphins
        cityNameMap.put("Baltimore", "12&Baltimore&4");//Baltimore Ravens
        cityNameMap.put("Cincinnati", "13&Cincinnati&4");//Cincinnati Bengals
        cityNameMap.put("Cleveland", "14&Cleveland&4");//Cleveland Browns
        cityNameMap.put("Pittsburgh", "15&Pittsburgh&4");//Pittsburgh Steelers
        cityNameMap.put("Houston", "16&Houston&5");//Houston Texans
        cityNameMap.put("Indianapolis", "17&Indianapolis&4");//Indianapolis Colts
        cityNameMap.put("Jacksonville", "18&Jacksonville&4");//Jacksonville Jaguars
        cityNameMap.put("Tennessee", "19&Tennessee&4");//Tennessee Titans
        cityNameMap.put("Denver", "20&Denver&6");//Denver Broncos
        cityNameMap.put("Kansas City", "21&Kansas City&5");//Kansas City Chiefs
        cityNameMap.put("Las Vegas", "22&Las Vegas&7");
        cityNameMap.put("Philadelphia", "23&Philadelphia&4");//Philadelphia Eagles
        cityNameMap.put("Chicago", "24&Chicago&5");//Chicago Bears
        cityNameMap.put("Detroit", "25&Detroit&4");//Detroit Lions
        cityNameMap.put("Green Bay", "26&Green Bay&5");//Green Bay Packers
        cityNameMap.put("Minnesota", "27&Minnesota&5");
        cityNameMap.put("Atlanta", "28&Atlanta&4");//Atlanta Falcons
        cityNameMap.put("New Orleans", "29&New Orleans&5");//New Orleans Saints
        cityNameMap.put("Los Angeles", "30&Los Angeles&7");//Los Angeles Rams
        cityNameMap.put("San Francisco", "31&San Francisco&7");//San Francisco 49ers
        cityNameMap.put("Seattle", "32&Seattle&7");//Seattle Seahawks
        cityNameMap.put("Oakland", "33&Oakland&7");//Seattle Seahawks
        cityNameMap.put("San Diego", "34&Los Angeles&7");//Los angeles Chargers
        cityNameMap.put("St. Louis", "35&St. Louis&5");//St. Louis Rams
        //cityNameMap.forEach((key, value) -> System.out.println(key + " = " + value));
    }

}