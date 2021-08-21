package dev.abel.springbootdocker.scraping.selenium;

import dev.abel.springbootdocker.enums.utils.Url;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InvestmentEquityPage extends SeleniumBase {

	@FindBy(how = How.ID, using = "countryID")
	private WebElement spanCountryId;
	
	@FindBy(how = How.ID, using = "stocksFilter")
	private WebElement selectMarketIndex;
	
	@FindBy(how = How.CSS, using = ".bold.left.noWrap.elp.plusIconTd")
	private List<WebElement> shareElements;
	
	@FindBy(how = How.ID, using = "cross_rate_markets_stocks_1")
	private WebElement tableMarket;
	
	private static final Logger log = LoggerFactory.getLogger(InvestmentEquityPage.class);
	
	protected void goToCountryPage(String countryCode) {
		
		log.info("ChromeDriver go to page of: {}", countryCode);
		
		driver.get(String.format("%s%s", Url.equities, countryCode));
	}

	protected String getCountryId() {
		
		return spanCountryId.getAttribute("value");
	}

	
	protected String getIdMarketIndex(WebElement optionMarketIndex) {
		
		return optionMarketIndex.getAttribute("id");
	}
	
	protected String getTitleMarketIndex(WebElement optionMarketIndex) {
		
		return optionMarketIndex.getText();
	}
	
	
	protected ExpectedCondition<WebElement> checkForTableShares() {

		return ExpectedConditions.visibilityOf(tableMarket);
	}
	
	
	protected String getShareTitle(WebElement shareElement) {		

		return shareElement.findElement(By.tagName("a")).getText();
	}

	protected String getShareId(WebElement shareElement) {		

		return shareElement.findElement(By.tagName("span")).getAttribute("data-id");
	}

	
	
	protected List<WebElement> getOptionsOfMarketIndex() {
		
		WebElement selectOfMarketIndex = driver.findElement(By.id("stocksFilter"));

		List<WebElement> optionsOfMarketIndex = new Select(selectOfMarketIndex).getOptions();

		return optionsOfMarketIndex;
	}
	
	
	protected List<WebElement> getShareElements() {		

		return shareElements;
	}
	
	protected void clickOnShareAndWaitTable(WebElement optionMarketIndex) {		

		optionMarketIndex.click();
		wait.until(checkForTableShares());
		
		
	}	


	

}
