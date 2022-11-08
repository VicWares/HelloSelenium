package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 221108 HelloSelenium32
 *******************************************************************/
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static org.jsoup.Jsoup.connect;
public class WebSiteReader
{
    private Document nflRandomMatchupsDoc;
    private Elements nflRandomMatchupsElements;
    private Document dirtyDoc;
    public Elements readCleanWebsite(String urlToRead) throws IOException
    {
        dirtyDoc = Jsoup.parse(String.valueOf(connect(urlToRead).get()));
        return getDirtyDoc().getAllElements();
    }
    public Document getDirtyDoc() {
        return dirtyDoc;
    }
}



