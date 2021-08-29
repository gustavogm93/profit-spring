package dev.abel.springbootdocker.scraping.company.infrastructure;

import dev.abel.springbootdocker.scraping.company.application.CompanyEncodedDataService;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/scraping/company")
@Data
@Component
public class CompanyScrapedDataController {

    private CompanyEncodedDataService companyEncodedDataService;

    public CompanyScrapedDataController(CompanyEncodedDataService companyEncodedDataService) {
        this.companyEncodedDataService = companyEncodedDataService;
    }

    @GetMapping("/normalize")
    public void normalize(@RequestParam( "region") String region) throws Exception {
        companyEncodedDataService.normalize(region);
    }
}
