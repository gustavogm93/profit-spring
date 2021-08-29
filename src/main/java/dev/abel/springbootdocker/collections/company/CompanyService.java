package dev.abel.springbootdocker.collections.company;


import org.springframework.stereotype.Service;


@Service
public class CompanyService {
/*
	public ScrapingFetchImpl ScrapingFetch;

	@Autowired
	public CompanyService(ScrapingFetchImpl scrapingFetch) {
		ScrapingFetch = scrapingFetch;
	}

	private static Logger logger = LoggerFactory.getLogger(CompanyService.class);

	public void saveFromScratch(CompanyOperationMessage companyOperationMessage) throws Exception {

		try {
			CompanyDTO company = new CompanyDTO();
			Industry profile = new Industry();

			Flow flow = new Flow();

			if (Objects.nonNull(companyOperationMessage.getIdCompany())) {
			}
			else {
				flow.setTitle(companyOperationMessage.getTitle());
				flow.setCode(companyOperationMessage.getScrappingCode());
				company.setFetchOperation(flow);
			}

			ScrapingFetch.getCompanyProfileByCompanyTitle(flow.getTitle(), profile);

			List<LocalDate> summaryPeriodTime = new ArrayList<>();

			String url = ScrapingFetch.buildSummaryUrl(company.getFetchOperation().getCode());

			Document doc = Jsoup.connect(url).get();

			Elements periodElements = ScrapingFetch.getElementsByTag("Th", doc);
			summaryPeriodTime.addAll(ScrapingFetch.getPeriods(periodElements));

			List<Instrument> instrumentsPerSummary = new ArrayList<>();

			Elements scrapingElements = ScrapingFetch.getElementsByTag("Td", doc);

			List<Instrument> listInstrument = ScrapingFetch.getSummaryByPeriod(scrapingElements, instrumentsPerSummary,
					summaryPeriodTime);

		} catch (Exception e) {
			logger.error("Error log message");
			throw new Exception(e.getMessage());
		}
	}

	public void create(StarterMessage starterMessage) {

		Flow fetchOperation = new Flow();

		fetchOperation.setTitle(starterMessage.getCompanyTitle());

		fetchOperation.setCode((ScrapingFetch.getScrapingCodeByCompanyTitle(fetchOperation.getTitle())));

		// fetchOperation.setState("STARTER");
		sender(fetchOperation);
	}

	public void sender(Flow companyOperation) {

	}*/
}

//