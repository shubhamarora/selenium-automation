import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RunOnSauceLab {
	
	// Create a new instance of Remote WebDriver
	WebDriver driver = null;
	
	// Annotating a public void method with @Before causes that method to be run before the Test method
	// In this case Test Method is "SwitchWindow"
	@Before
	public void setUp() throws Exception
	{
		System.out.println("In Before");
		
		// capabilities: Describes a series of key/value pairs that encapsulate aspects of a browser
		DesiredCapabilities capabilities = DesiredCapabilities.ipad();
		
		// Run over iPad on Sauce Connect 
		// Define the platform , version, and platform to test
		capabilities.setCapability("platform", "OS X 10.9");
		capabilities.setCapability("version", "7.1");
		capabilities.setCapability("device-orientation", "portrait");
		
		// You can find multiple other platforms on Sauce Labs. They are making it Awesome.
		// Platforms: https://saucelabs.com/platforms
		
		// Set connection to Sauce Labs
		driver = new RemoteWebDriver(new URL("http://YOUR_USERNAME:YOUR_ACCESS_KEY@ondemand.saucelabs.com:80/wd/hub"),
		            capabilities);   
	}
		

	@Test
	public void SauceLab() {

        	System.out.println("In Test");
        	// Use this to navigate to a page by specifying the URL
		driver.get("https://www.google.co.in/");
			
		// This will find the element by id.  
		WebElement element = driver.findElement(By.name("q"));
		
		// Enter something to search for
		 element.sendKeys("Shubham Arora Github");

		 // Submit the form
		 element.submit();

	}
	
	// Annotating a public void method with @After causes that method to be run before the Test method
	@After
	public void tearDown()  
	{
		System.out.println("In After");
		 // close the browser
		 driver.quit();
	}
}
