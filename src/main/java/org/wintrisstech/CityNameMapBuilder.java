package org.wintrisstech;
import java.util.Map;
public class CityNameMapBuilder
{
    public static Map<String, String> cityNameMap = new java.util.HashMap<String, String>();
    public static String awayCity;
    public static String homeCity;
    public CityNameMapBuilder()
    {
        cityNameMap.put("5@Minneapolis", "1&Minnesota");//Minnesota Vikings
        cityNameMap.put("4@Tampa", "2&Tampa Bay");//Tampa Bay Buccaneers
        cityNameMap.put("4@Tampa Bay", "2&Tampa Bay");//Tampa Bay Buccaneers
        cityNameMap.put("5@Arlington", "3&Dallas");//Dallas Cowboys
        cityNameMap.put("5@Dallas", "3&Dallas");//Dallas Cowboys
        cityNameMap.put("4@Orchard Park", "4&Buffalo");//Buffalo Bills
        cityNameMap.put("4@Buffalo", "4&Buffalo");//Buffalo Bills
        cityNameMap.put("4@Charlotte", "5&Carolina");//Carolina Panthers
        cityNameMap.put("4@Carolina", "5&Carolina");//Carolina Panthers
        cityNameMap.put("6@Arizona", "6&Arizona");//Arizona Cardinals
        cityNameMap.put("6@Tempe", "6&Arizona");//Arizona Cardinals
        cityNameMap.put("4@Foxborough", "7&New England");//New England Patriots
        cityNameMap.put("4@New England", "7&New England");//New England Patriots
        cityNameMap.put("4@East Rutherford", "8&New York");//New York Giants and New York Jets
        cityNameMap.put("4@New York", "8&New York");//New York Giants and New York Jets
        cityNameMap.put("7@Landover", "9&Washington");//Washington Football Team
        cityNameMap.put("7@Washington Football Team", "9&Washington");//Washington Football Team
        cityNameMap.put("7@Washington Commanders", "9&Washington");//Washington Football Team
        cityNameMap.put("7@Washington", "9&Washington");//Washington Football Team
        cityNameMap.put("5@Nashville", "10&Tennessee");//Tennessee Titans
        cityNameMap.put("4@Miami", "11&Miami");//Miami Dolphins
        cityNameMap.put("4@Baltimore", "12&Baltimore");//Baltimore Ravens
        cityNameMap.put("4@Cincinnati", "13&Cincinnati");//Cincinnati Bengals
        cityNameMap.put("4@Cleveland", "14&Cleveland");//Cleveland Browns
        cityNameMap.put("4@Pittsburgh", "15&Pittsburgh");//Pittsburgh Steelers
        cityNameMap.put("5@Houston", "16&Houston");//Houston Texans
        cityNameMap.put("4@Indianapolis", "17&Indianapolis");//Indianapolis Colts
        cityNameMap.put("4@Jacksonville", "18&Jacksonville");//Jacksonville Jaguars
        cityNameMap.put("Tennessee", "19&Tennessee");//Tennessee Titans
        cityNameMap.put("6@Denver", "20&Denver");//Denver Broncos
        cityNameMap.put("5@Kansas City", "21&Kansas City");//Kansas City Chiefs
        cityNameMap.put("7@Las Vegas", "22&Las Vegas");
        cityNameMap.put("4@Philadelphia", "23&Philadelphia");//Philadelphia Eagles
        cityNameMap.put("5@Chicago", "24&Chicago");//Chicago Bears
        cityNameMap.put("4@Detroit", "25&Detroit");//Detroit Lions
        cityNameMap.put("5@Green Bay", "26&Green Bay");//Green Bay Packers
        cityNameMap.put("5@Minnesota", "27&Minnesota");
        cityNameMap.put("4@Atlanta", "28&Atlanta");//Atlanta Falcons
        cityNameMap.put("5@New Orleans", "29&New Orleans");//New Orleans Saints
        cityNameMap.put("7@Los Angeles", "30&Los Angeles");//Los Angeles Rams
        cityNameMap.put("7@San Francisco", "31&San Francisco");//San Francisco 49ers
        cityNameMap.put("7@Seattle", "32&Seattle");//Seattle Seahawks
        //cityNameMap.forEach((key, value) -> System.out.println(key + " = " + value));
    }

}