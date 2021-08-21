package dev.abel.springbootdocker.collections.company;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Document(collection = "Company")
@Component
public class CompanyDTO {

	@Id
	private String id; 
	
	@Field( "title")
	private String title; 

	@Field( "Country")
	private String Country; 
	
	@Field( "market")
	private Market market;
	
	@Field( "industry")
	private Industry industry;

	@Field( "fetchOperation")
	private Flow fetchOperation;

	@Field( "financialSummary")
	private FinancialSummary financialSummary;

	@Field( "lastUpdate")
	private Date lastUpdate;

	public CompanyDTO() {
	}

}
