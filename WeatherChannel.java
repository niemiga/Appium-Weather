package Practice.AppiumFramework;


  
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class WeatherChannel {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

   
    @BeforeMethod
    public  void setup () throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("udid", "emulator-5554"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "9.0");
        caps.setCapability("skipUnlock","true");      
        caps.setCapability("appPackage", "com.weather.Weather");
		//caps.setCapability("appActivity", "com.weather.Weather.daybreak.MainActivity");
        caps.setCapability("appActivity", "com.weather.Weather.app.SplashScreenActivity");
        caps.setCapability("noReset","true");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),caps);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public  void basicTest () throws InterruptedException {
       
    	driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    	driver.findElement(By.id("com.weather.Weather:id/txt_location_name")).click(); 	
    	driver.findElement(By.id("com.weather.Weather:id/search_text")).sendKeys("Portland");
    	Thread.sleep(1000);
    	TouchAction t =new TouchAction(driver);	
    	t.tap(PointOption.point(975, 1700)).perform();
    	
    	//Find number of search results for 'Portland'  
    	int count=driver.findElements(By.id("com.weather.Weather:id/search_item_container")).size();
    	System.out.println("No of search results "+count);
    	 for(int i=0;i<count;i++) // loop through all the 'Portland' results and print them to the console
 	    {
    		 driver.findElements(By.id("com.weather.Weather:id/search_item_container")).get(i).click();  				 
    		 driver.findElement(By.id("com.weather.Weather:id/txt_location_name")).click();    	
    	    	driver.findElement(By.id("com.weather.Weather:id/search_text")).sendKeys("Portland");
    	    	Thread.sleep(2000);
    	    	TouchAction y =new TouchAction(driver);	
    	    	y.tap(PointOption.point(975, 1700)).perform();
    	    	String temp= driver.findElementById("com.weather.Weather:id/current_conditions_temperature").getText();
        		String currentlocation= driver.findElementById("com.weather.Weather:id/txt_location_name").getText();
           		String currentPhrase= driver.findElementById("com.weather.Weather:id/current_conditions_temperature_phrase").getText();
        		System.out.println(temp+" "+currentlocation+" "+currentPhrase);
        		System.out.println();       		
 	    }
		driver.findElement(By.id("com.weather.Weather:id/txt_location_name")).click();
    	driver.findElement(By.id("com.weather.Weather:id/search_text")).sendKeys("Portland");
    	Thread.sleep(2000);
    	TouchAction v =new TouchAction(driver);	
    	v.tap(PointOption.point(975, 1700)).perform();
    	//Find current weather at PDX airport
    	driver.findElement(By.xpath("//*[@text='Portland International Airport']/parent::*")).click();
	
		Thread.sleep(5000);
      
    }

   @AfterMethod
    public void teardown(){
        driver.quit();
    }
}


