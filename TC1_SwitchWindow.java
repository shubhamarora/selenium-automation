import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TC1_SwitchWindow {
	
	// Create a new instance of the Firefox Driver
	// Firefox driver comes in the default package of Selenium 2.0
	WebDriver driver = new FirefoxDriver();
	
	// Annotating a public void method with @Before causes that method to be run before the Test method
	// In this case Test Method is "SwitchWindow"
	@Before
	public void setUp() throws Exception
	{
		System.out.println("In Before");
		// Maximize the browser window
        driver.manage().window().maximize();    
	}
		

	@Test
	public void SwitchWindow() {

        System.out.println("In Test");
        // Use this to navigate to a page by specifying the URL
		driver.get("ENTER_URL_HERE");
		
		// Get the current window/tab handle 
		String already_openned_window  = driver.getWindowHandle();
		
		// This will find the element (href or similar) on the page and click on it.  
		driver.findElement(By.xpath("ENTER_XPATH_HERE")).click();
		
		// Iterate the all open browser window/tab handles
		for (String handle : driver.getWindowHandles()) {
			
			// Switch focus of WebDriver to the next found window handle (that's your newly opened window)
			 if(!handle.equals(already_openned_window)) {
				 driver.switchTo().window(handle); 
			 }
		}
		
		// switch back to the original window
		driver.switchTo().window(already_openned_window);
		 
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
