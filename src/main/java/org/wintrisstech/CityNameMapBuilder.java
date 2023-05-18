package org.wintrisstech;
import java.util.LinkedHashMap;
import java.util.Map;
public class CityNameMapBuilder
{
    private static Map<String, String> cityNameMap = new LinkedHashMap<String, String>();
    public CityNameMapBuilder()
    {
        System.out.println("CityNameMapBuilder...building cityNameMap");
        cityNameMap.put("Minneapolis", "1&Minnesota");//Minnesota Vikings
        cityNameMap.put("Tampa", "2&Tampa Bay");//Tampa Bay Buccaneers
        cityNameMap.put("Tampa Bay", "2&Tampa Bay");//Tampa Bay Buccaneers
        cityNameMap.put("Arlington", "3&Dallas");//Dallas Cowboys
        cityNameMap.put("Dallas", "3&Dallas");//Dallas Cowboys
        cityNameMap.put("Orchard Park", "4&Buffalo");//Buffalo Bills
        cityNameMap.put("Buffalo", "4&Buffalo");//Buffalo Bills
        cityNameMap.put("Charlotte", "5&Carolina");//Carolina Panthers
        cityNameMap.put("Carolina", "5&Carolina");//Carolina Panthers
        cityNameMap.put("Arizona", "6&Arizona");//Arizona Cardinals
        cityNameMap.put("Tempe", "6&Arizona");//Arizona Cardinals
        cityNameMap.put("Foxborough", "7&New England");//New England Patriots
        cityNameMap.put("New England", "7&New England");//New England Patriots
        cityNameMap.put("East Rutherford", "8&New York");//New York Giants and New York Jets
        cityNameMap.put("New York", "8&New York");//New York Giants and New York Jets
        cityNameMap.put("Landover", "9&Washington");//Washington Football Team
        cityNameMap.put("Washington", "9&Washington");//Washington Football Team
        cityNameMap.put(" Nashville", "10&Tennessee");//Tennessee Titans
        cityNameMap.put("Miami", "11&Miami");//Miami Dolphins
        cityNameMap.put("Baltimore", "12&Baltimore");//Baltimore Ravens
        cityNameMap.put("Cincinnati", "13&Cincinnati");//Cincinnati Bengals
        cityNameMap.put("Cleveland", "14&Cleveland");//Cleveland Browns
        cityNameMap.put("Pittsburgh", "15&Pittsburgh");//Pittsburgh Steelers
        cityNameMap.put("Houston", "16&Houston");//Houston Texans
        cityNameMap.put("Indianapolis", "17&Indianapolis");//Indianapolis Colts
        cityNameMap.put("Jacksonville", "18&Jacksonville");//Jacksonville Jaguars
        cityNameMap.put("Tennessee", "19&Tennessee");//Tennessee Titans
        cityNameMap.put("Denver", "20&Denver");//Denver Broncos
        cityNameMap.put("Kansas City", "21&Kansas City");//Kansas City Chiefs
        cityNameMap.put("Las Vegas", "22&Las Vegas");//Los Angeles Chargers and Los Angeles Rams
        cityNameMap.put("Philadelphia", "23&Philadelphia");//Philadelphia Eagles
        cityNameMap.put("Chicago", "24&Chicago");//Chicago Bears
        cityNameMap.put("Detroit", "25&Detroit");//Detroit Lions
        cityNameMap.put("Green Bay", "26&Green Bay");//Green Bay Packers
        cityNameMap.put("Minnesota", "27&Minnesota");
        cityNameMap.put("Atlanta", "28&Atlanta");//Atlanta Falcons
        cityNameMap.put("New Orleans", "29&New Orleans");//New Orleans Saints
        cityNameMap.put("Los Angeles", "30&Los Angeles");//Los Angeles Rams
        cityNameMap.put("San Francisco", "31&San Francisco");//San Francisco 49ers
        cityNameMap.put("Seattle", "32&Seattle");//Seattle Seahawks
        System.out.println(cityNameMap.size() + " NFL teams");
        //cityNameMap.forEach((key, value) -> System.out.println(key + " = " + value));
    }
    static Map getCityNameMap(String bigNFLawayCityName)
    {
        return cityNameMap;
    }
}