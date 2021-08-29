package dev.abel.springbootdocker.scraping.selenium;

import dev.abel.springbootdocker.utils.Msg;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SeleniumBase {

	protected WebDriver driver;

	protected URL chromeDriverPath;

	protected Wait<WebDriver> wait;

	protected int TIMEOUT_MEDIUM = 12;

	protected long POLLING_MEDIUM = 2;

	protected int IMPLICIT_MEDIUM = 8;

	@Value("${chrome.driver.path}")
	private String ChromeDriverPathResource;

	private final static Logger log = LoggerFactory.getLogger(SeleniumBase.class);

	protected void startSeleniumDriver() throws URISyntaxException {

		log.info(Msg.executor);
/*		ChromeOptions chromeOptions= new ChromeOptions();
		chromeOptions.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
*/
		String chromeDriverPath = SeleniumBase.class.getClassLoader().getResource(ChromeDriverPathResource)
				.getPath();
		
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();

	}

	protected void setUpSeleniumDriver() {
		
		log.info(Msg.setUpDriver);
		PageFactory.initElements(driver, this);

		System.out.println(TIMEOUT_MEDIUM + "time out medium");
		System.out.println(POLLING_MEDIUM + "polling meduium");
		wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(TIMEOUT_MEDIUM))
				.pollingEvery(Duration.ofSeconds(POLLING_MEDIUM))
				.ignoring(org.openqa.selenium.NoSuchElementException.class)
				.ignoring(org.openqa.selenium.TimeoutException.class);

		System.out.println(wait.toString());
	}

	protected void closeSeleniumDriver() {
		driver.quit();
		driver = null;
	}
	
	private void changeImplicitWait(int value, TimeUnit timeUnit) {
		driver.manage().timeouts().implicitlyWait(value, timeUnit);
	}
	private void restoreDefaultImplicitWait() {
		driver.manage().timeouts().implicitlyWait(TIMEOUT_MEDIUM, TimeUnit.SECONDS);
	}

	protected boolean isElementOnPage(By by) {
		changeImplicitWait(IMPLICIT_MEDIUM, TimeUnit.SECONDS);
		try {
			return driver.findElements(by).size() > 0;
		} finally {
			restoreDefaultImplicitWait();
		}
	}

	protected boolean isElementEnabled(WebElement element) {
		return !"true".equals(element.getAttribute("disabled"));
	}

	protected boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
			return false;
		}
	}

	private ExpectedCondition<WebElement> checkForElement(WebElement webElement) {

		return ExpectedConditions.visibilityOf(webElement);
	}

	
	protected void wait(WebElement webElement) {
		wait.until(checkForElement(webElement));
	}
}
