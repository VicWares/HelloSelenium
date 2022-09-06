package org.example;
/**********************************************************************************
 * Must be run before
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 220905
 **********************************************************************************/
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.By.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
class OddsCollector
{
    public static void collectOdds(Elements oddsElements)
    {
        System.out.println("Hello OddsCollector");
        WebDriver driver = new SafariDriver();
        driver.manage().window().maximize();

        driver.get("https://www.covers.com/sport/football/nfl/odds");//Odds
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(elementToBeClickable(cssSelector("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"))).click();//Accept All cookies Odds click
        System.out.println("accept Odds cookies clicked");

        WebElement betMenuDropdown = driver.findElement(By.cssSelector("#__betMenu"));
        new Actions(driver)
                .moveToElement(betMenuDropdown)
                .perform();
        System.out.println("Moved to Bet Type Dropdown");
        new Actions(driver)
                .click()
                .perform();
        System.out.println("Clicked on Bet Type Dropdown");
        WebElement spread = driver.findElement(By.cssSelector("#BetTypeDropdown > li:nth-child(3) > a"));//Spread dropdown item
        new Actions(driver)
                .moveToElement(spread)
                .perform();
        System.out.println("Moved to Spread Dropdown");
        new Actions(driver)
                .click(spread)
                .perform();
        System.out.println("Clicked on Spread Dropdown");
    }
}