package dev.abel.springbootdocker.scraping.company.application;

import dev.abel.springbootdocker.scraping.company.domain.EncodedProfile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
class FetchingProfileCompany {


    public FetchingProfileCompany() {}

    private static final Logger logger = LoggerFactory.getLogger(FetchingProfileCompany.class);

    public EncodedProfile getCompanyProfile(String url){
        logger.info("Starting to fetch profile company in {}", url);
        try {
            Document doc = getDocumentProfile(url);
            Elements profileElements =extractEncodedProfileElements(doc);
            Elements currencyElement =extractEncodedCurrencyElement(doc);

            HashMap<String, String> profileValues = getProfileFields(profileElements);

             String industry = profileValues.get("industry");

             String sector = profileValues.get("sector");

             String employees = profileValues.get("employees");

             String equityType = profileValues.get("equityType");

             String currency = getCurrency(currencyElement);

            EncodedProfile encodedProfile = new EncodedProfile(industry,sector, employees,equityType, currency );
            encodedProfile.verifyValidEncoded();

            logger.info("Profile company was successful scraped in {} ", url);

            return encodedProfile;
        }catch (Exception e){
            logger.warn("An Error has ocurred on Company profile process: {}", e.getMessage());
            return null;
        }

    }


    public Document getDocumentProfile(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            return doc;
        } catch (Exception e) {
            return null;
        }
    }


    public Elements extractEncodedProfileElements(Document doc) {
        try {
            Elements profileElements = doc.select("div.companyProfileHeader");
            return profileElements;
        }catch (Exception e){
            return null;
        }
    }

    public Elements extractEncodedCurrencyElement(Document doc) {
        try { //doc.select("#quotes_summary_current_data").select("div.inlineblock").get(0)
            Elements currencyElements = doc.select("#quotes_summary_current_data");
        return currencyElements;
        }catch (Exception e){
            return null;
        }
    }

    public HashMap<String, String> getProfileFields(Elements elements) {

        HashMap<String, String> profileFields = new HashMap<>();
        try {
            if (elements.get(0) == null || elements.get(0).childrenSize() == 0)
                return profileFields;

            for (int i = 0; i < elements.get(0).childrenSize(); i++) {
                Element elementToBeScraped = elements.get(0).child(i);
                if (elementToBeScraped == null) continue;

                String field = elementToBeScraped.select("div").first().ownText();

                if (field.equalsIgnoreCase("Industry")) {
                    profileFields.put("industry", extractProfileText(elementToBeScraped));
                }

                if (field.equalsIgnoreCase("Sector")) {
                    profileFields.put("sector", extractProfileText(elementToBeScraped));
                }

                if (field.equalsIgnoreCase("Employees")) {
                    profileFields.put("employees", extractProfileSecondaryText(elementToBeScraped));
                }

                if (field.equalsIgnoreCase("Equity Type")) {
                    profileFields.put("equityType", extractProfileSecondaryText(elementToBeScraped));
                }

            }

            return profileFields;
        } catch (Exception e) {
            return profileFields;
        }

    }

    public String getCurrency(Elements elements ){
        try {
            return elements.select("div.inlineblock").get(0).select(":eq(3)").select("span.bold").text();
        }catch (Exception e){
            return null;
        }
    }

        public String extractProfileText(Element elementToBeScraped){
            try {
                return elementToBeScraped.select("div").first().select("a").text();
            }catch (Exception e) {
                return null;
            }
        }

    public String extractProfileSecondaryText(Element elementToBeScraped){
        try {
            return elementToBeScraped.select("div").first().select("p").text();
        }catch (Exception e) {
            return null;
        }
    }

    }



