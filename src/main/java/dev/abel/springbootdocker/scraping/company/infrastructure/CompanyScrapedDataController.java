package dev.abel.springbootdocker.scraping.company.infrastructure;

import dev.abel.springbootdocker.scraping.company.application.CompanyEncodedDataService;
import dev.abel.springbootdocker.scraping.company.application.ScrapingCompanyStrategy;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/company")
@Data
@Component
public class CompanyScrapedDataController {

    private CompanyEncodedDataService companyEncodedDataService;
    private ScrapingCompanyStrategy scrapingCompanyStrategy;

    public CompanyScrapedDataController(CompanyEncodedDataService companyEncodedDataService, ScrapingCompanyStrategy scrapingCompanyStrategy) {
        this.companyEncodedDataService = companyEncodedDataService;
        this.scrapingCompanyStrategy = scrapingCompanyStrategy;
    }

    @GetMapping("/normalize")
    public void normalize(@RequestParam( "region") String region) throws Exception {
        companyEncodedDataService.normalize(region);
    }

    @GetMapping("/fetch")
    public void fetchCompanyData(@RequestParam( "region") String region) throws Exception {
        scrapingCompanyStrategy.run(region);
    }
}
