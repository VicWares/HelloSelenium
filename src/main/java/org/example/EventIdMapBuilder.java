package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 221003A
 **********************************************************************************/
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.example.Main.*;
public class EventIdMapBuilder
{
    public EventIdMapBuilder()
    {
        Main.driver.get("https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDateMap.get(weekNumber));
        List<WebElement> weekElements = Main.driver.findElements(By.cssSelector("[data-link]"));
        for(WebElement inputElement : weekElements)
        {
            String dataGame = inputElement.getAttribute("data-link").split("/")[5];
            String dataEventId = inputElement.getAttribute("data-event-id");
            Main.dataEventIdMap.put(dataEventId, dataGame);
        }
    }
}
