package dev.abel.springbootdocker.scraping.company.application;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ScrapingFetchImpl  {
    /*
    private final Logger logger = LoggerFactory.getLogger(ScrapingFetchImpl.class);

    private String titleSummary = "";

    private static int dateIndex = 4;

    private Validates validateUtils;

    private MapperUtils mapperUtils;

    public ScrapingFetchImpl() {
    };

    @Autowired
    public ScrapingFetchImpl(Validates validateUtils, MapperUtils mapperUtils) {
        super();
        this.validateUtils = validateUtils;
        this.mapperUtils = mapperUtils;
    }

    public void run(List<LocalDate> summaryPeriodTime, CompanyDTO company, Document doc) {

        logger.info("Scrapping financial Summary into HashMap and return it");

        List<Instrument> instrumentsPerSummary = new ArrayList<>();

        Elements scrapingElements = getElementsByTag("Td", doc);

        getSummaryByPeriod(scrapingElements, instrumentsPerSummary, summaryPeriodTime);

        saveSummaryCompany(company, instrumentsPerSummary);

        xd(instrumentsPerSummary);
        System.out.println("----");

        // companyRepository.save(company);

    }

    @Override
    public String getScrapingCodeByCompanyTitle(String companyTitle) {

        Document doc;
        try {
            doc = Jsoup.connect(buildCompanyCodeUrl(companyTitle)).get();

            Element element = doc.select("div[data-pair-id]").first();

            String companyCode = element.attr("data-pair-id");

            return companyCode;
        } catch (IOException e) {

            e.printStackTrace();
            return null;

        }
    }

    public HashMap<String, String> getCompanyProfileByCompanyTitle(String companyTitle, Industry industry) {

        Document doc;
        try {
            doc = Jsoup.connect(buildProfileUrl(companyTitle)).get();

            Element elementIndustry = doc.select(":containsOwn(Industry)").first();
            Element elementSector = doc.select(":containsOwn(Sector)").first();

            return null;
        } catch (IOException e) {

            e.printStackTrace();
            return null;

        }
    }

    @Override
    public String buildCompanyCodeUrl(String companyTitle) {

        StringBuilder companyCode = new StringBuilder();

        companyCode.append(ScrappingConstant.codeUrl);
        companyCode.append(companyTitle);

        return companyCode.toString();

    }

    @Override
    public String buildSummaryUrl(String codeCompany) {

        StringBuilder summaryUrl = new StringBuilder();

        summaryUrl.append(ScrappingConstant.fixUrl.replace("#", codeCompany));

        return summaryUrl.toString();
    }

    @Override
    public String buildProfileUrl(String companyTitle) {

        StringBuilder summaryUrl = new StringBuilder();

        summaryUrl.append(ScrappingConstant.profileURl.replace("#", companyTitle));

        return summaryUrl.toString();
    }

    @Override
    public Elements getElementsByTag(String tag, Document doc) {

        try {
            Elements summaryElements = doc.getElementsByTag(tag);

            return summaryElements;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<Instrument> getSummaryByPeriod(Elements e, List<Instrument> instrumentList,
                                               List<LocalDate> summaryPeriodTime) {

        List<String> valuesPerSummary = e.stream().map(Element::ownText)
                .filter((s) -> validateUtils.isSummaryModelValue(s)).collect(Collectors.toList());

        valuesPerSummary.forEach((element) -> {

            if (validateUtils.isSummaryObject(element)) {

                titleSummary = element;
                dateIndex = 0;
            } else {
                instrumentList.add(fillInstrument(element, summaryPeriodTime));
            }
        });

        return instrumentList;
    }

    @Override
    public Instrument fillInstrument(String instrumentValue, List<LocalDate> intervalTimeList) {

        Instrument instrumentToAdd = new Instrument();
        instrumentToAdd.setTitle(titleSummary);
        instrumentToAdd.setValue(mapperUtils.stringToNum(instrumentValue));
        instrumentToAdd.setPeriodEnding(intervalTimeList.get(dateIndex));

        dateIndex++;

        return instrumentToAdd;
    }

    public static void xd(List<Instrument> instru) {
        for (Instrument A : instru) {
            System.out.println(A.toString());
        }
    }

    @Override
    public List<LocalDate> getPeriods(Elements p) {

        return p.stream().map(Element::ownText).distinct().filter(PatternResource::dateStringPattern)
                .map(mapperUtils::toDate).collect(Collectors.toList());

    }

    public void saveSummaryCompany(CompanyDTO company, List<Instrument> instrumentList) {
        /*
         * switch(summarie) {
         *
         * case FS->{ FinancialSummaryDTO FinancialSummary = new FinancialSummaryDTO();
         * FinancialSummary.setSummary(instrumentList);
         * company.setFinancialSummary(FinancialSummary); } case INC-> {
         * IncomeStatementDTO incomeStatement = new IncomeStatementDTO();
         * incomeStatement.setSummary(instrumentList);
         * company.setIncomeStatement(incomeStatement); } case BAL-> { BalanceSheetDTO
         * balanceSheet = new BalanceSheetDTO();
         * balanceSheet.setSummary(instrumentList);
         * company.setBalanceSheet(balanceSheet); }
         *
         * case CAS ->{ CashFlowStatementDTO cashFlowStatement = new
         * CashFlowStatementDTO(); cashFlowStatement.setSummary(instrumentList);
         * company.setCashFlowStatement(cashFlowStatement); } }
         */
    }

