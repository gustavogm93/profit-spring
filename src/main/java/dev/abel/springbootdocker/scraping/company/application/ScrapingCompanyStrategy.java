package dev.abel.springbootdocker.scraping.company.application;

import dev.abel.springbootdocker.enums.utils.Url;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;

public class ScrapingCompanyStrategy {
/*

    public void run() {
        Document doc;
        try {
            doc = Jsoup.connect(buildProfileUrl(companyTitle)).get();

            Element elementIndustry = doc.select(":containsOwn(Industry)").first();
            Element elementSector = doc.select(":containsOwn(Sector)").first();

        } catch (IOException e) {

            e.printStackTrace();


        }


    }


    public String buildProfileUrl(String companyTitle) {

        StringBuilder summaryUrl = new StringBuilder();

        summaryUrl.append(Url.profile.replace("#", companyTitle));

        return summaryUrl.toString();
    }
*/
}