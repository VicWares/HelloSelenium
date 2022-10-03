package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.example.Main.*;
public class BuildXrefMap
{
    public BuildXrefMap()
    {
        Main.driver.get("https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDateMap.get(weekNumber));
        List<WebElement> weekElements = Main.driver.findElements(By.cssSelector("[data-link]"));
        for(WebElement inputElement : weekElements)
        {
            String dataGame = inputElement.getAttribute("data-link").split("/")[5];
            String dataEventId = inputElement.getAttribute("data-event-id");
            dataEventIdMap.put(dataEventId, dataGame);
        }
        System.out.println(dataEventIdMap);
    }
}
