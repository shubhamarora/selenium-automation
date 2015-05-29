import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

@RunWith(value = Parameterized.class)
public class CSVDrivenScript {

    private RemoteWebDriver driver;
    DesiredCapabilities dc;
    File file;
    FileWriter fw;
    BufferedWriter bw;
    private String url, browser;

    long start, finish, loadtime1;
    Double loadtime;

    @Before
    public void setUp() throws Exception {
	
	launchBrowser();
	
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
	file = new File("path/to/your/output.csv"); // OutputCSV
	
	// If output csv does not exist, it will create one
	// And in case if it already exist on the specified path.
	// The results will be appended to the existing output sheet
	if (!file.exists()) {
	    file.createNewFile();
	}
    }

    @Parameters
    public static Collection<String[]> testData() throws IOException {
	return getTestData("path/to/your/input.csv"); 
    }
		
    
    public CSVDrivenScript(String browser, String url) {
	this.browser = browser;
	this.url = url;
    }
    
    /**
     * @param fileName input sheet file
     * @return collection object
     * @throws IOException
     * 
     *  Read data from the input CSV and store it in the collection and return the collection object
     */
    public static Collection<String[]> getTestData(String fileName) throws IOException {
	List<String[]> records = new ArrayList<String[]>();
	String record;
	BufferedReader file = new BufferedReader(new FileReader(fileName));
		while ((record = file.readLine()) != null) {
		String fields[] = record.split(",");
		records.add(fields);
	}
	
	file.close();
	return records;
    }

    @Test
    public void TC_SearchGoogle() throws Exception {

	fw = new FileWriter(file.getAbsoluteFile(), true);
	bw = new BufferedWriter(fw);
	
	driver.get(url);

	try{
	    synchronized (driver) {
		driver.wait(2000);
	    }
	}
	catch(NullPointerException npe) {npe.printStackTrace();}
	catch(InterruptedException ie){ie.printStackTrace();}	
	
	// This will find the element search input box by name.
	WebElement element = driver.findElement(By.name("q"));
	
	// Enter something to search for
	element.sendKeys("Shubham Arora Github");

	// Submit the form
	element.submit();
	
	System.out.println("Launched - " + driver.getTitle());
	
	// populating in CSV sheet
	bw.write(driver.getTitle());
	bw.newLine();
	bw.write("****************");
	bw.newLine();
	
	// clearing buffer stream and closing
	bw.flush();
	bw.close();
    }

    @After
    public void tearDown() throws Exception {
	driver.quit();
    }
        
    // Launch browser depending upon the value defined in input CSV
    public void launchBrowser() {
	if(browser.toLowerCase().equals("chrome")) {
		instantiateChrome();
	}
	else if(browser.toLowerCase().equals("mozilla")) {
		instantiateMozilla();
	}
	else {
		instantiateIE();
	}
    }
    
    // Instantiate Chrome Browser
    public void instantiateChrome() 
    {
	System.setProperty("webdriver.chrome.driver", "path/to/your/chromedriver.exe");
	dc = DesiredCapabilities.chrome();

	ChromeOptions options = new ChromeOptions();
	options.addArguments("test-type");
	
	dc.setCapability(ChromeOptions.CAPABILITY, options);
	driver = new ChromeDriver();
    }
    
    // Instantiate Mozilla Browser
    public void instantiateMozilla() 
    {
	dc= DesiredCapabilities.firefox();  
	driver = new FirefoxDriver();
    }
    
    // Instantiate IE Browser
    public void instantiateIE() 
    {
	System.setProperty("webdriver.ie.driver","path/to/your/IEDriver.exe");
	dc = DesiredCapabilities.internetExplorer();
	driver = new InternetExplorerDriver();
    }
}
