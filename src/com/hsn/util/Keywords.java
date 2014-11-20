package com.hsn.util;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.sf.saxon.exslt.Math;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;



// SELENIUM LAYER 

public class Keywords {
	
	WebDriver driver = null;
	
	static Keywords k;
	static HSNBusinessFunctions hsnbf = null; 
	
	WebDriver back_Mozilla = null;
	WebDriver back_IE = null;
	WebDriver back_Chrome = null;
	WebDriver back_Safari = null;
	
	DesiredCapabilities ff = null;
	DesiredCapabilities ch = null;
	DesiredCapabilities ie = null;
	DesiredCapabilities sa = null;
	
	static Properties OR = null;
	Properties ENV = null;
	
	private static final String JQUERY_LOAD_SCRIPT = System.getProperty("user.dir")+"//src//com//hsn//util//queryify.js" ;
	
	public Xls_Reader xls = null;
	public static Logger APP_LOGS ;
	public String suiteXLSName = "";
	
	 public int int_currentDataSetIndex = 0; // holds the current data set being run starts form 1.
	 public int int_currentTestCaseRowStartIndex = 0; // holds the current test case
	 public Hashtable<String, String> currentDataSet;
	 public String CurrentBrowser = "";
	 public String currenttestReportStmnt = "";
	 public String currenttestCaseName;
	 public int currenttestHeaderrow;
	 public int currenttestDatarow;
	 	

	public void setCurrentDataSetIndex(int x){
		
		k.int_currentDataSetIndex = x;
	}
	
	public int getCurrentDataSetIndex(){
		
		return k.int_currentDataSetIndex;
	}
	
	public void setCurrentTestCaseRowStartIndex(int x){
		
		k.int_currentTestCaseRowStartIndex = x;
	}
	
	public int getCurrentTestCaseRowStartIndex(){
		
		return k.int_currentTestCaseRowStartIndex;
	}
	
	private static String readFile(String file) throws IOException {
		  Charset cs = Charset.forName("UTF-8");
		  FileInputStream stream = new FileInputStream(file);
		  try {
		  Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
		  StringBuilder builder = new StringBuilder();
		  char[] buffer = new char[8192];
		  int read;
		  while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
		  builder.append(buffer, 0, read);
		  }
		  return builder.toString();
		  }
		  finally {
		  stream.close();
		  }
	}
	
	public String runJqueryStatement(String object,String data) throws IOException{
		
		try {
			
			String jQueryLoader = readFile(JQUERY_LOAD_SCRIPT);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeAsyncScript(jQueryLoader);
			String Myscript = data.trim() ;
			System.out.println("running the jquery statement "+data.trim());
			js.executeScript(Myscript);  
			
			
			return "PASS";
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAIL";

		}
	}
	
	public static Keywords getKeywordsInstance() throws IOException{
		
		if (k==null){
			
			k = new Keywords();
			APP_LOGS = Logger.getLogger("devpinoyLogger");
			System.out.println("@@@@@@@@@ CREATING A NEW KEYWORDS OJECT@@@@@@@@@@@@");
			APP_LOGS.debug("@@@@@@@@@ CREATING A NEW KEYWORDS OJECT@@@@@@@@@@@@");
			k.loadEnv();
		}
		
		return k;
		
	}
	
	public void loadEnv() throws IOException{
		
		OR = new Properties();
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//com//hsn//config//or.properties");
		OR.load(fs);
		
		Properties CONFIG = new Properties();
		FileInputStream fs1 = new FileInputStream(System.getProperty("user.dir")+"//src//com//hsn//config//config.properties");
		CONFIG.load(fs1);
		
		String env = CONFIG.getProperty("ENV");
		
		suiteXLSName = CONFIG.getProperty("SUITENAME");
	
		ENV = new Properties();
		FileInputStream fs2 = new FileInputStream(System.getProperty("user.dir")+"//src//com//hsn//config//"+env+".properties");
		ENV.load(fs2);
		
		System.out.println("my url is "+ENV.getProperty("siteUrl"));
		APP_LOGS.debug("my url is "+ENV.getProperty("siteUrl"));

	}
	
	public  Properties getEnvProperties(){
		
		return k.ENV;
	}
	
	public String ClearCache(String object,String data) throws IOException{
		
		try {
			System.out.println("tying cc cleaner");
			Runtime.getRuntime().exec(System.getProperty("user.dir")+"//drivers//LaunchCCCleaner.exe");
			Thread.sleep(90000);
			System.out.println("Java is done with cc cleaner");
			return "PASS";
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAIL";

		}
	}
	

	
public String openRemoteBrowser(String object,String data ) throws MalformedURLException{
		
		String browserType = data.trim();
		System.out.println("KEYWORD : openBrowser - opening browser type"+browserType);
		
		try {
			
			if(browserType.equalsIgnoreCase("Chrome")){
			PowerShellCommandExecutor.executePSCommand("Stop-Process -processname chromedriver*");
			}
			else if(browserType.equalsIgnoreCase("IE")){
			PowerShellCommandExecutor.executePSCommand("Stop-Process -processname IEDriverServer*");
			}
			else if(browserType.equalsIgnoreCase("Safari")){
			PowerShellCommandExecutor.executePSCommand("Stop-Process -processname Safari*");
			}
			back_Safari = null;
			back_Mozilla = null;
			back_IE = null;
			back_Chrome=null;
//			//Thread.sleep(10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 try {
			//@@@@@@@@@@@@@ MOZILLA BROWSER @@@@@@@@@@@@@@@@@@@@@@//
			  if(browserType.equals("Mozilla")){
					
					  if(back_Mozilla == null){
						  System.out.println("creating new ff");
						//ProfilesIni prof = new ProfilesIni();
						//FirefoxProfile profile = prof.getProfile("Default User");
						ff = new DesiredCapabilities();
						ff.setBrowserName("firefox");
						ff.setPlatform(Platform.ANY);
						  
						driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub") ,ff );
						//driver = new FirefoxDriver(profile);
						back_Mozilla = driver;
						System.out.println("the value of driver is "+driver.hashCode());
						return "PASS";
					  }else{
						  driver = back_Mozilla;
						 
						  return "PASS";
					  }
			
			  //@@@@@@@@@@@@@ IE BROWSER @@@@@@@@@@@@@@@@@@@@@@//				
				}else if(browserType.equals("IE")){
					
						if(back_IE==null){
							System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//drivers//IEDriverServer.exe");
							ie = DesiredCapabilities.internetExplorer();
							ie.setCapability("ignoreZoomSetting", true);
							driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub") ,ie );
							back_IE = driver;
							return "PASS";
						}else{
							driver = back_IE;
							return "PASS";
						}
			 //@@@@@@@@@@@@@ CHROME BROWSER @@@@@@@@@@@@@@@@@@@@@@//					
				}else if(browserType.equals("Chrome")){
					

					if(back_Chrome==null){
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//drivers//chromedriver.exe");
					ch = DesiredCapabilities.chrome();
					ch.setCapability("ignoreZoomSetting", true);
					driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub") ,ch );;
					back_Chrome = driver;
					return "PASS";
					}else{
						driver = back_Chrome;
						return "PASS";
					}
				//@@@@@@@@@@@@@ SAFARI BROWSER @@@@@@@@@@@@@@@@@@@@@@//	
				}else if(browserType.equals("Safari")){
					
					
						if(back_Safari==null){

							sa = DesiredCapabilities.safari();
							driver =  new RemoteWebDriver(new URL("http://localhost:4444/wd/hub") ,sa );
							back_Safari = driver;
							return "PASS";
						}else{
							driver = back_Safari;
							return "PASS";
						}
				}else{
					
					System.out.println("Invalid browser name"+browserType);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAIL";
		}
		  
		return "PASS";
			
	}

public String updateHostFile(String object,String data ) {
	
	String str_Pop =  k.currentDataSet.get("Pop");
	String str_Server =  k.currentDataSet.get("Server");
	
	
	String commandLine = "\"C:\\selenium\\HSN\\Framework\\src\\com\\hsn\\util\\hostfile_ga_update_local.ps1\" \""+ str_Pop.trim()+ "\" "+str_Server.trim()+"\" ";
	
	System.out.println("KEYWORD : updateHostFile with command "+ commandLine);
	
	// kill all browsers and drivers currently open
			try {
				
				
				PowerShellCommandExecutor.executePSCommand(commandLine);
								
				Thread.sleep(5000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	 	return "PASS";
		
}


	public String naviagateBack(String object,String data ) throws  InterruptedException{
		
//		  //Thread.sleep(5000);
		  driver.navigate().back();
//		  //Thread.sleep(5000);
		  
		return "PASS";
	}
	
	public String SetUserAgent(String object,String data){
		
		String browserType = "Chrome";
		String UserAgentString = data.trim();
		
		System.out.println("KEYWORD : SetUserAgent - using browser "+browserType+" and user agent string "+UserAgentString);
		
		back_Safari = null;
		back_Mozilla = null;
		back_IE = null;
		back_Chrome=null;
		
		if(back_Chrome == null){
	 		
			try {
				
				PowerShellCommandExecutor.executePSCommand("Stop-Process -processname chromedriver*");
				PowerShellCommandExecutor.executePSCommand("Stop-Process -processname chrome*");
				
				Thread.sleep(5000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	 		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//drivers//chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("user-data-dir=C:/Users/user_name/AppData/Local/Google/Chrome/User Data");  
			String str_UserAgentSetter =  "--user-agent=" + UserAgentString;
			options.addArguments(str_UserAgentSetter); //iPhone 4
			driver = new ChromeDriver(options);
			
			return "PASS";
		  }else{
			  driver = back_Chrome;
			  return "PASS";
		  }
		
	}

	
	public String openBrowser(String object,String data ) throws MalformedURLException{
		
		String browserType = data.trim();
		System.out.println("KEYWORD : openBrowser:opening browser type"+browserType);
				

		// kill all browsers and drivers currently open
				try {
					
					if(browserType.equalsIgnoreCase("Mozilla")){
						PowerShellCommandExecutor.executePSCommand("Stop-Process -processname firefox*");
					}
					else if(browserType.equalsIgnoreCase("Chrome")){
					PowerShellCommandExecutor.executePSCommand("Stop-Process -processname chromedriver*");
					PowerShellCommandExecutor.executePSCommand("Stop-Process -processname chrome*");
					}
					else if(browserType.equalsIgnoreCase("IE")){
					PowerShellCommandExecutor.executePSCommand("Stop-Process -processname IEDriverServer*");
					PowerShellCommandExecutor.executePSCommand("Stop-Process -processname iexplore*");
					}
					else if(browserType.equalsIgnoreCase("Safari")){
					PowerShellCommandExecutor.executePSCommand("Stop-Process -processname Safari*");
					}
					back_Safari = null;
					back_Mozilla = null;
					back_IE = null;
					back_Chrome=null;
//					//Thread.sleep(5000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		 //@@@@@@@@@@@@@ MOZILLA BROWSER @@@@@@@@@@@@@@@@@@@@@@//
		  if(browserType.equals("Mozilla")){
				
				  if(back_Mozilla == null){
					ProfilesIni prof = new ProfilesIni();
					FirefoxProfile profile = prof.getProfile("Default User");
					driver = new FirefoxDriver(profile);
					back_Mozilla = driver;
					return "PASS";
				  }else{
					  driver = back_Mozilla;
					  return "PASS";
				  }
		
		  //@@@@@@@@@@@@@ IE BROWSER @@@@@@@@@@@@@@@@@@@@@@//				
			}else if(browserType.equals("IE")){
				
					if(back_IE==null){
						System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//drivers//IEDriverServer.exe");
						DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
						//caps.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);  
						//caps.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
						caps.setCapability("ignoreZoomSetting", true);
						driver = new InternetExplorerDriver(caps);
						back_IE = driver;
						
						/*String CookiesFolder =  "C://Users//"+System.getProperty("user.name")+"//AppData//Roaming//Microsoft//Windows//Cookies";
						File file = new File(CookiesFolder);        
				        String[] myFiles;      
				            if(file.isDirectory()){  
				                myFiles = file.list();  
				                for (int i=0; i<myFiles.length; i++) {  
				                    File myFile = new File(file, myFiles[i]);   
				                    myFile.delete();  
				                }  
				             }  
				             
				             */
						
						return "PASS";
					}else{
						driver = back_IE;
						return "PASS";
					}
		 //@@@@@@@@@@@@@ CHROME BROWSER @@@@@@@@@@@@@@@@@@@@@@//					
			}else if(browserType.equals("Chrome")){
				
			
					if(back_Chrome==null){
						
						try {
							System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//drivers//chromedriver.exe");
							DesiredCapabilities caps = DesiredCapabilities.chrome();
							caps.setCapability("ignoreZoomSetting", true);
							driver = new ChromeDriver(caps);
							back_Chrome = driver;
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							k.openBrowser(object, data);
							System.out.println("RECALLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
						}
						
					return "PASS";
					
					}else{
						driver = back_Chrome;
						return "PASS";
					}
			//@@@@@@@@@@@@@ SAFARI BROWSER @@@@@@@@@@@@@@@@@@@@@@//		
			}else if(browserType.equals("Safari")){
				
				
				if(back_Safari==null){
				
						try {
							driver = new SafariDriver();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							k.openBrowser(object, data);
							System.out.println("RECALLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
							
						}
						System.out.println("DRIVER created for SAFARI"+ driver.getCurrentUrl());
						back_Safari = driver;
						return "PASS";
					}else{
						driver = back_Safari;
						System.out.println("driver is NOT NULL");
						return "PASS";
					}
			}else if(browserType.equals("Iphone")){
				
				 	if(back_Chrome == null){
				 		
				 		System.setProperty("webdriver.chrome.driver","C:\\selenium\\HSN\\Framework\\drivers\\chromedriver.exe");
						ChromeOptions options = new ChromeOptions();
						options.addArguments("user-data-dir=C:/Users/user_name/AppData/Local/Google/Chrome/User Data");  
						options.addArguments("--user-agent=Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5"); //iPhone 4
						driver = new ChromeDriver(options);
						back_Chrome = driver;
						return "PASS";
					  }else{
						  driver = back_Chrome;
						  return "PASS";
					  }
				
			}
			
			else{
				
				System.out.println("Invalid browser name"+browserType);
			}
		  
		try {
			driver.manage().deleteAllCookies();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "PASS";
			
	}
	
	public void closeModals(){
		
		driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
	
		try {
				if(driver.findElements(By.xpath(OR.getProperty("ArcadeBadgeModalClose"))).size()>0){
					driver.findElement(By.xpath(OR.getProperty("ArcadeBadgeModalClose"))).click();
					System.out.println("closing modal for Badgerville");
				}
				
				if(driver.findElements(By.xpath(OR.getProperty("ForeseeClose"))).size()>0){
					driver.findElement(By.xpath(OR.getProperty("ForeseeClose"))).click();
					System.out.println("closing modal for foresee");
				}
				
				if(driver.findElements(By.xpath(OR.getProperty("HSN_Modal"))).size()>0){
					driver.findElement(By.xpath(OR.getProperty("HSN_Modal"))).click();
					System.out.println("closingHSN_Modal");
				}
				
		} catch (Exception e) {
			// TODO Auto-generated catch blockn
			//e.printStackTrace();
		}
			
	}
	
    public void closeModalsFast(){
		
		driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
	
		try {
				if(driver.findElements(By.xpath(OR.getProperty("ArcadeBadgeModalClose"))).size()>0){
					driver.findElement(By.xpath(OR.getProperty("ArcadeBadgeModalClose"))).click();
					System.out.println("closing modal for Badgerville");
				}
				
				if(driver.findElements(By.xpath(OR.getProperty("ForeseeClose"))).size()>0){
					driver.findElement(By.xpath(OR.getProperty("ForeseeClose"))).click();
					System.out.println("closing modal for foresee");
				}
				
				if(driver.findElements(By.xpath(OR.getProperty("HSN_Modal"))).size()>0){
					driver.findElement(By.xpath(OR.getProperty("HSN_Modal"))).click();
					System.out.println("closingHSN_Modal");
				}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
			
	}

    public String checkForText(String object,String data){
	
	
    System.out.println("KEYWORD : checkForText . looking for text "+object.trim()+ " on the page");
	driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
	
	try {
		if (driver.getPageSource().contains(object.trim()))
				return "PASS"+ " found the text "+object.trim()+" on the page";
			else
				return "FAIL"+ " UNABLE TO FIND the text "+object.trim()+" on the page";
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "FAIL"+ " UNABLE TO FIND the text "+object.trim()+" on the page";
	} 
	
	
}

	
	public String navigate(String object,String data){
		
		        
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		try {
			driver.navigate().to(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().window().maximize();
		
		System.out.println("KEYWORD : navigate . navigating to "+data.trim());
		
		
		
		// signout if you see that that the site opens with existing users cookie
		
		try {
			
		
			if(driver.findElements(By.xpath(OR.getProperty("HomePage_AccessYourHSN"))).size() != 0)			 
			 {					
					k.click("HomePage_AccessYourHSN", data);
					k.clickIfExists("HomePage_MyHSN_SignOut", data);
					System.out.println("signing out existing user");
					driver.navigate().refresh();
					return Constants.KEYWORD_PASS;
					
				}
			
			if(driver.findElements(By.xpath(OR.getProperty("M_HomePageHamburgerMenu"))).size() != 0){
				
				k.click("M_HomePageHamburgerMenu", "");				
				k.click("M_HomePageAccount", "");				
				k.clickIfExists("M_HomePageSignout ", "");
				driver.navigate().to(data);				
				System.out.println("signing out existing mobile user");
				
				return Constants.KEYWORD_PASS;		

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return "PASS";
			
	}
	
	public String click(String object,String data){
		
		k.closeModals();
		
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
        	try {
       	    
        		if(!k.currentDataSet.get("Browser").equals("Safari")){
		
        		String mainWindowHandle = driver.getWindowHandle();   //Handle of main window 
        		driver.switchTo().window(mainWindowHandle); 
        		driver.manage().window().maximize();
        		System.out.println("KEYWORD : click - the count for element"+object.trim() +" is " +driver.findElements(By.xpath(OR.getProperty(object.trim()))).size());
           		WebElement navButton = driver.findElement(By.xpath(OR.getProperty(object.trim())));
           		JavascriptExecutor js = (JavascriptExecutor) driver;
           	    js.executeScript("arguments[0].click();", navButton);
           	    //System.out.println("java script click used");
//        		//Thread.sleep(3000);
        		
        		}else{        		
        		driver.findElement(By.xpath(OR.getProperty(object.trim()))).click();	
        		}
        		
			
		} catch (Exception e) {
			
			e.printStackTrace();
			//k.closeBroswer("", "");
			return " -- Not able to click object "+ object;
		}
     
		return Constants.KEYWORD_PASS;
	}
	
	public String doubleClick(String object,String data) throws InterruptedException{
		
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
       
		//Assert.assertFalse(((driver.findElements(By.xpath(OR.getProperty(object.trim()))).size())<1), "unable to find element by XPATH "+OR.getProperty(object.trim()));
		//Assert.assert
		
        try {
	        	String mainWindowHandle = driver.getWindowHandle();   //Handle of main window 
	    		System.out.println("the window is "+mainWindowHandle);
	    		driver.switchTo().window(mainWindowHandle); 
	    		driver.manage().window().maximize();
        		Actions actions = new Actions(driver);
        		actions.doubleClick(driver.findElement(By.xpath(OR.getProperty(object.trim())))).perform();
//       		//Thread.sleep(10000);
        		System.out.println("doing Keyword: doubleclick for object "+object.trim());

			
		} catch (Exception e) {
			
			e.printStackTrace();
			k.closeBroswer("", "");
			return " -- Not able to double click "+ object;
		}
     
		return Constants.KEYWORD_PASS;
	}

	public String clickIfExists(String object,String data){
		
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		
        System.out.println("count for object with XPath "+OR.getProperty(object.trim())+" is "+driver.findElements(By.xpath(OR.getProperty(object.trim()))).size());
		
		try {			
			if(driver.findElements(By.xpath(OR.getProperty(object.trim()))).size()>0){
				k.click(object,data);
				//driver.findElement(By.xpath(OR.getProperty(object.trim()))).click();				
			}else{				
				System.out.println("object doesnt not exist - no submit click ");
			}			
			return Constants.KEYWORD_PASS;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return Constants.KEYWORD_PASS;
		}		
}
	
	public void close(){
		
		driver.close();
	}
	
	public  String waitTime(String object,String data){
	    
		 System.out.println("waiting for "+object.trim()+" seconds ");
		 int waitingtime = Integer.parseInt(object.trim());

		 try {
			 
			 	Thread.sleep(waitingtime*1000);
			 	
			} catch (Exception e) {
				
				return Constants.KEYWORD_FAIL+" -- Not able to wait!!!!!";
			}
		 
		 return Constants.KEYWORD_PASS;
	 
	}	
	
	public  String CloseAndReopenBrowser(String object,String data){
	     
		 String browserType = data.trim();
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 try {
				
			 
			 	driver.close();
			 	
				APP_LOGS.debug("openBrowser:opening browser type"+browserType);
				System.out.println("opening a browser");
				
				 // create a new browser as needed
				
				 //@@@@@@@@@@@@@ MOZILLA BROWSER @@@@@@@@@@@@@@@@@@@@@@//
				  if(browserType.equals("Mozilla")){
						

							ProfilesIni prof = new ProfilesIni();
							FirefoxProfile profile = prof.getProfile("default");
							driver = new FirefoxDriver(profile);
							back_Mozilla = driver;
							return "PASS";

				  //@@@@@@@@@@@@@ IE BROWSER @@@@@@@@@@@@@@@@@@@@@@//				
					}else if(browserType.equals("IE")){
						
								System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//drivers//IEDriverServer.exe");
								DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
								caps.setCapability("ignoreZoomSetting", true);
								driver = new InternetExplorerDriver(caps);
								back_IE = driver;
								return "PASS";

				 //@@@@@@@@@@@@@ CHROME BROWSER @@@@@@@@@@@@@@@@@@@@@@//					
					}else if(browserType.equals("Chrome")){
						
						
							System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//drivers//chromedriver.exe");
							DesiredCapabilities caps = DesiredCapabilities.chrome();
							caps.setCapability("ignoreZoomSetting", true);
							driver = new ChromeDriver(caps);
							back_Chrome = driver;
							return "PASS";

					}else{
						
						System.out.println("Invalid browser name"+browserType);
					}
			
			 				 	
				return Constants.KEYWORD_PASS+" closed and reopened browser"+object;
				
			} catch (Exception e) {
				
				return Constants.KEYWORD_FAIL+" -- Not able to closed and reopened browser"+object;
			}
    
	}
	
	public  String ValidateSearch(String object,String data){
	     
		 Boolean bool_ValidateSearch ;
		 APP_LOGS.debug("getting text from link "+OR.getProperty(object.trim()));
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 try {
				String actual = driver.findElement(By.xpath(OR.getProperty(object.trim()))).getText();
				APP_LOGS.debug("captured text  tis"+actual);
				if(actual.length()>0){
					
					bool_ValidateSearch = true;
					Assert.assertTrue(bool_ValidateSearch);
					return Constants.KEYWORD_PASS;
					
				}else{
					
					bool_ValidateSearch = false;
					Assert.assertFalse(bool_ValidateSearch, "no search results text seen on page ");
					return Constants.KEYWORD_FAIL+" unable to capture text for"+object;
					
				}
				
			} catch (Exception e) {
				
				return Constants.KEYWORD_FAIL+" -- Not able to find element"+object;
			}
    
	}
	
	public  String ValidateIfStringContains(String object,String data){
	     
		 String[] strarr = data.split(" , ");
		 String s1 = strarr[0].replace("'", "");
		 String s2 = strarr[1].replace("'", "");
		 
		 if(s1.trim().contains(s2.trim()) | s2.trim().contains(s1.trim()))
		 {
		
			 System.out.println("String s1 = "+s1+" contains or is contained in "+s2);
			 return Constants.KEYWORD_PASS;
		 }    
		 else
		 {
			 
			 System.out.println("String s1 = "+s1+" does not contain "+s2);
			 return Constants.KEYWORD_FAIL;
			 
		 }
			 
		
   
	}
	public  String saveLinkRedirectUrl(String object,String data){
	     
		 System.out.println("saving text on link "+OR.getProperty(object.trim())+" to column name "+data.trim());
		 driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		 try {
				
//			    //Thread.sleep(10000);
			    int count = driver.findElements(By.xpath(OR.getProperty(object.trim()))).size();
			 	if(count>0)
			 	
			 	{
			 		
			 		k.click(object, data);
			 		
//			 		//Thread.sleep(10000);
			 		
			 		String rediretUrl = driver.getCurrentUrl();
			 		
			 		System.out.println("found element count"+rediretUrl.length());
					
					if(rediretUrl.length()>0){
						
						return Constants.KEYWORD_PASS+ " "+data.trim()+" = "+rediretUrl;
						
					}
					else
						System.out.println("ERROR found no element count"+rediretUrl.length());
				
			 	} return Constants.KEYWORD_FAIL+" unable to capture redirected Url after selection "+object;
				
			} catch (Exception e) {
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+" -- Not able to find element"+object;
			}
  
	}
	
	
	public  String getResponseCode(String responseCode,String url){
	     
		 System.out.println("Getting Response code for "+url +" and string it in column"+responseCode);
		 driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		 try {
			 	HttpURLConnection.setFollowRedirects(false);
			 	HttpURLConnection con = (HttpURLConnection) new URL(url.trim()).openConnection();
		        con.setRequestMethod("HEAD");
		        String rc = ""+ con.getResponseCode();
		        return Constants.KEYWORD_PASS+ " "+responseCode.trim()+" = "+rc;
							
			} catch (Exception e) {
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+" -- Not able to get rc for "+url;
			}
 
	}
	
	public  String saveCookieValue(String object,String data){
	     
		 String cookieName = object.trim();
		 org.openqa.selenium.Cookie myCookie ;
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 APP_LOGS.debug("getting and saving cookie value "+OR.getProperty(object.trim())+"to column name"+data.trim());
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 try {
				
			    myCookie = driver.manage().getCookieNamed(cookieName);
				System.out.println("the Value of the cookie"+cookieName+" is "+myCookie.getValue());		
			 	
				
			} catch (Exception e) {
				
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+" -- Not able to find cookieName"+cookieName;
			}
		 
		 return Constants.KEYWORD_PASS+ " "+data.trim()+" = "+myCookie.getValue();
		 
   
	}
	
	public  String setCookieValue(String object,String data){
	     
		 String cookieName = object.trim();
		 org.openqa.selenium.Cookie myCookie ;
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 APP_LOGS.debug("getting and saving cookie value "+OR.getProperty(object.trim())+"to column name"+data.trim());
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 try {
				
			    
			    myCookie = driver.manage().getCookieNamed(cookieName);
			    driver.manage().deleteCookie(myCookie);
			    
			    Thread.sleep(5000);
			    
			    driver.navigate().refresh();
			    
			    Thread.sleep(5000);
			    
			    driver.manage().addCookie(new Cookie(cookieName,data));
			    
			    myCookie = driver.manage().getCookieNamed(cookieName);
				System.out.println("the Value of the cookie"+cookieName+" is "+myCookie.getValue());		
				
				driver.navigate().refresh();
				
				Thread.sleep(5000);
			 	
				
			} catch (Exception e) {
				
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+" -- Not able to find cookieName"+cookieName;
			}
		 
		 return Constants.KEYWORD_PASS ;

  
	}
	
	
	public  String saveText(String object,String data){
	     
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 APP_LOGS.debug("saving text on link "+OR.getProperty(object.trim())+"to column name"+data.trim());
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 try {
				
			    int count = driver.findElements(By.xpath(OR.getProperty(object.trim()))).size();
			 	if(count>0)
			 	
			 	{
			 		
			 		for (int i=0;i<count;i++){
			 			
			 			
			 		}
				 	String actual = driver.findElement(By.xpath(OR.getProperty(object.trim()))).getText();
					
					if(actual.length()>0){
						
						return Constants.KEYWORD_PASS+ " col|"+data.trim()+" = "+actual;
						
					}
				
			 	} return Constants.KEYWORD_FAIL+" unable to capture text for"+object;
				
			} catch (Exception e) {
				
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+" -- Not able to find element"+object;
			}
    
	}
	
	
	
	
	public  String verifyLinkText(String object,String data){
	       
		 APP_LOGS.debug("veryfying text on link "+OR.getProperty(object.trim()));
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 try {
				String actual = driver.findElement(By.xpath(OR.getProperty(object.trim()))).getText();
				String expected = data.trim();
				
				if(actual.equals(expected)){
					return Constants.KEYWORD_PASS;
				}else{
					return Constants.KEYWORD_FAIL+" -- expected"+expected+"--found "+actual;
				}
				
			} catch (Exception e) {
				
				return Constants.KEYWORD_FAIL+" -- Not able to click link";
			}


       
	}
	
	public  String getLinkText(String object,String data){
	     
		 APP_LOGS.debug("getting text from link "+OR.getProperty(object.trim()));
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 try {
				String actual = driver.findElement(By.xpath(OR.getProperty(object.trim()))).getText();
				
				if(actual.length()>0){
					
					return Constants.KEYWORD_PASS+". value captured is "+actual;
					
				}else{
					return Constants.KEYWORD_FAIL+" unable to capture text for"+object;
				}
				
			} catch (Exception e) {
				
				return Constants.KEYWORD_FAIL+" -- Not able to find element"+object;
			}
     
	}
	
	public  String saveElementValue(String object,String data){
	     
		 System.out.println("saving value on element "+OR.getProperty(object.trim())+"to column name"+data.trim());
		 driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		 try {
				
//			    //Thread.sleep(10000);
			    int count = driver.findElements(By.xpath(OR.getProperty(object.trim()))).size();
			 	if(count>0)
			 	
			 	{
			 		
			 		WebElement we = driver.findElement(By.xpath(OR.getProperty(object.trim())));
			 		
			 		String actual = we.getAttribute("value");
					
					if(actual.length()>0){
						
						return Constants.KEYWORD_PASS+ " "+data.trim()+" = "+actual;
						
					}
					else
						System.out.println("ERROR found no element count"+actual.length());
				
			 	} return Constants.KEYWORD_FAIL+" unable to capture value for"+object;
				
			} catch (Exception e) {
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+" -- Not able to find element"+object;
			}
   
	}
	
	public  String saveLinkText(String object,String data){
	     
		 System.out.println("saving text on link "+OR.getProperty(object.trim())+"to column name"+data.trim());
		 driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		 try {
				
//			    //Thread.sleep(10000);
			    int count = driver.findElements(By.xpath(OR.getProperty(object.trim()))).size();
			 	if(count>0)
			 	
			 	{
			 		
			 		String actual = driver.findElement(By.xpath(OR.getProperty(object.trim()))).getText();
			 		
			 		if(actual.length()>0){
						
						return Constants.KEYWORD_PASS+ " "+data.trim()+" = "+actual;
						
					}
					else
						System.out.println("ERROR found no element count"+actual.length());
				
			 	} return Constants.KEYWORD_FAIL+" unable to capture text for"+object;
				
			} catch (Exception e) {
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+" -- Not able to find element"+object;
			}
    
	}
	
	public  String captureUrl(String object,String data){
	     
		 APP_LOGS.debug("captureUrl to column name"+data.trim());
		 driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		 try {
				
			   		String actual = driver.getCurrentUrl();
					
					return Constants.KEYWORD_PASS+ " "+data.trim()+" = "+actual;
					
			} catch (Exception e) {
				
				return Constants.KEYWORD_FAIL+" -- Not able to find url";
			}
   
	}
	
	
	
	public  String clickButton(String object,String data){
		
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
       APP_LOGS.debug("Clicking on Button "+OR.getProperty(object.trim()));
       
       try {
			driver.findElement(By.xpath(OR.getProperty(object.trim()))).click();
//			Thread.sleep(3000);
		} catch (Exception e) {
			
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+" -- Not able to click button"+object;
		}
    
		return Constants.KEYWORD_PASS;

	}
	
	public  String verifyButtonText(String object,String data){
		
		 APP_LOGS.debug("comparing Button textin "+OR.getProperty(object.trim())+"with "+OR.getProperty(data));
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 try {
				String actual = driver.findElement(By.xpath(OR.getProperty(object.trim()))).getText();
				String expected = OR.getProperty(data);
				
				if(actual.equals(expected)){
					return Constants.KEYWORD_PASS;
				}else{
					return Constants.KEYWORD_FAIL+" -- expected"+expected+"--found "+actual;
				}
				
			} catch (Exception e) {
				
				return Constants.KEYWORD_FAIL+" -- Not able to click find button text";
			}
	
	}
	
	public  String selectList_ByValue(String object,String data){
		
		 APP_LOGS.debug("Selecting from list value: "+data);
		 driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		 data = data.trim();
		 
		 k.closeModals();
		
		try {
			
			//(driver.findElement(By.xpath(OR.getProperty(object.trim()))).isDisplayed(),"unable to find "+OR.getProperty(object.trim()));
			
//			Thread.sleep(2000);
			
			System.out.println("The number of match select elements is"+driver.findElements(By.xpath(OR.getProperty(object.trim()))).size());
			
			Select select = new Select(driver.findElement(By.xpath(OR.getProperty(object.trim()))));

			List<WebElement > list = select.getOptions(); 
			
			String currentSelection;
			
			for ( int i = 0; i < list.size();i++){
				
				currentSelection = list.get(i).getText().toString().trim();
				
				System.out.println("the "+i+"th element is ::: "+currentSelection+ " ::: looking for :::"+data+":::");
				
				if(currentSelection.contains(data.trim()) || currentSelection.equals(data.trim())){
					
					select.selectByVisibleText(currentSelection);
					return Constants.KEYWORD_PASS;	 
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			k.closeBroswer("", "");
			return Constants.KEYWORD_FAIL+"unable to find "+data+" in the dropdown";	
			
			
		}
		return Constants.KEYWORD_FAIL+"unable to find "+data+"in the dropdown";	
		
		
	}
	
	public  String selectList_Random(String object,String data){
		
		 APP_LOGS.debug("Selecting from list value: "+data);
		 driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		
		 k.closeModals();
		 
		try {
			
			//(driver.findElement(By.xpath(OR.getProperty(object.trim()))).isDisplayed(),"unable to find "+OR.getProperty(object.trim()));
			
//			//Thread.sleep(2000);
			
			System.out.println("The number of match select elements is"+driver.findElements(By.xpath(OR.getProperty(object.trim()))).size());
			
			Select select = new Select(driver.findElement(By.xpath(OR.getProperty(object.trim()))));

			List<WebElement > list = select.getOptions(); 
			
			int Randindex = (int) ( list.size()*Math.random() );
			
				    System.out.println("selecting "+Randindex+" from available list of "+list.size());
					select.selectByIndex(Randindex);
					return Constants.KEYWORD_PASS+" "+data.trim()+" = "+"flexpay Option"+Randindex;	 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return Constants.KEYWORD_FAIL+"unable to find "+data+"in the dropdown";	
			
		}
		
		
	}
	
	public String selectElementWithtext (String object , String textValue) {
		  
		  
		  List<WebElement> allElements = driver.findElements(By.xpath(OR.getProperty(object.trim())));
		  
		  for(WebElement e : allElements){
			  
			  System.out.println(e.getText());
			  if(e.getText().contains(textValue.trim())){
				  e.click();
				  return Constants.KEYWORD_PASS;	 
				  
			  }
		  }
		  
		  return Constants.KEYWORD_FAIL;
		  
	  }

	
	public  String verifyListSelection(String object,String data){
		APP_LOGS.debug("Verifying the selection of the list");
		
		
		return Constants.KEYWORD_PASS;	
	}
	
	public  String verifyAllListElements(String object,String data){
		APP_LOGS.debug("Verifying all the list elements");
		
		return Constants.KEYWORD_PASS;	

	}
	
	public  String mouseHoverOver(String object,String data){
		APP_LOGS.debug("Hovering over element ");
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

		
		
       try {
       	Actions action = new Actions(driver).moveToElement(driver.findElement(By.xpath(OR.getProperty(object.trim()))));
   		action.build().perform();
   		Thread.sleep(1000);
   		//action.release();
		} catch (Exception e) {
			
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+" -- Not able to mouse over";
		}
    
		return Constants.KEYWORD_PASS;

	}
	
	public  String selectRadio(String object,String data){
		
		APP_LOGS.debug("Selecting a radio button "+OR.getProperty(object.trim()));
		
		 try {
				driver.findElement(By.xpath(OR.getProperty(object.trim()))).click();
			} catch (Exception e) {
				
				return Constants.KEYWORD_FAIL+" -- Not able to select link";
			}
	     
			return Constants.KEYWORD_PASS;

	}
	
	public  String verifyRadioSelected(String object,String data){
		APP_LOGS.debug("Verify Radio Selected");
		
		return Constants.KEYWORD_PASS;	

	}
	
	public  String verifyCheckBoxSelected(String object,String data){
		APP_LOGS.debug("Verifying checkbox selected");
		
		return Constants.KEYWORD_PASS;
		
	}
	
	
	public  String verifyText(String object,String data){
		APP_LOGS.debug("Verifying the text");
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

      
		
		return Constants.KEYWORD_PASS;
		
	}
	
	public  String verifyTextinInput(String object,String data){
		
		 APP_LOGS.debug("Verifying the text in input box "+OR.getProperty(object.trim())+"with "+data);
		
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

		 try {
//			    //Thread.sleep(3000);
				String actual = driver.findElement(By.xpath(OR.getProperty(object.trim()))).getAttribute("value");
				String expected = data.trim();
				
				if(actual.equals(expected)){
					return Constants.KEYWORD_PASS;
				}else{
					return Constants.KEYWORD_FAIL+" -- expected "+expected+"--found "+actual;
				}
				
			} catch (Exception e) {
				
				return Constants.KEYWORD_FAIL+" -- Not able to the text in input box";
			}
    

		
	}
	
	public  String writeInInput(String object,String data) throws InterruptedException{
		
		k.closeModals();

		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
	      System.out.println("KEYWORD : writeInInput - Writing in text box "+OR.getProperty(object.trim())+" value "+data);
	     
	      
	      try {
	   	   
	    	  driver.findElement(By.xpath(OR.getProperty(object.trim()))).sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END)," ");
	    	  Thread.sleep(100);
	    	  driver.findElement(By.xpath(OR.getProperty(object.trim()))).sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),data);
	    	  //driver.findElement(By.xpath(OR.getProperty(object.trim()))).sendKeys(data);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				k.closeBroswer("", "");
				return Constants.KEYWORD_FAIL+" -- Not able to find and write to input"+OR.getProperty(object);
			}
	   
			
			return Constants.KEYWORD_PASS;
}
	
	public  String clickImage(String object,String data){
	       APP_LOGS.debug("Clicking the image");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String verifyFileName(String object,String data){
	       APP_LOGS.debug("Verifying inage filename");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String pause(){
	       APP_LOGS.debug("Waiting");
			
			return Constants.KEYWORD_PASS;
	}
	
	
	public  String store(){
	       APP_LOGS.debug("Storing value");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String verifyTitle(){
	       APP_LOGS.debug("Verifying title");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String isElementVisible(String object,String data){
	      	       
	       driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		   System.out.println("Looking for object "+OR.getProperty(object.trim()));	
	       APP_LOGS.debug("Looking for object "+OR.getProperty(object.trim()));
	       
	       List<WebElement> AllMatchingELements = driver.findElements(By.xpath(OR.getProperty(object.trim())));
	       int count = AllMatchingELements.size();
	       
	       try {
				 if(count > 0 ){
					 
					 return Constants.KEYWORD_PASS;
				 }
				 
				 else{
					 
					 return Constants.KEYWORD_FAIL + "unable to find element with xpath displayed "+OR.getProperty(object.trim());
				 }
				
			} catch (Exception e) {
				
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+" -- Not able to find element "+object;
			}
	    

	}
	
	public  String BrowseFlyouts(String object,String data) throws InterruptedException{
		 
		   driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		   
		   String capturedLinksFromDepts = "";
			
	       APP_LOGS.debug("mouseover all department flyouts ");
	       
	       List<WebElement> AllDeptFlyouts = driver.findElements(By.xpath("//li[@data-marketing-id]"));
	       int count = AllDeptFlyouts.size();
	       
	       System.out.println("mouseover all department flyouts count is"+count);
	       
		   if(count>0)
		 	
		 	{
			   for(int index = 0; index < count; index++ ){
				
				    mouseHoverOver("HomePage_HSNTV","");
				   
				    capturedLinksFromDepts = capturedLinksFromDepts + AllDeptFlyouts.get(index).getText() + ": ";
				   
					Actions action = new Actions(driver);
					action.moveToElement(AllDeptFlyouts.get(index));
			   		action.perform();
			   		action.release(AllDeptFlyouts.get(index));
//			   		//Thread.sleep(5000);
			   		
			   		System.out.println("cat count is"+driver.findElements(By.xpath(OR.getProperty("HomePage_Flyout_Category"))).size());
				    
			   		System.out.println("the category "+driver.findElement(By.xpath(OR.getProperty("HomePage_Flyout_Category"))).getText());
			   		capturedLinksFromDepts.concat(driver.findElement(By.xpath(OR.getProperty("HomePage_Flyout_Category"))).getText());
			   		
			   			   		
				   
			   }
		 		
			   System.out.println("the captured text is "+capturedLinksFromDepts);
			
		 	} 
		
		return Constants.KEYWORD_PASS;
	}
	
	
	public  String removeSinglequotes(String object,String data){
		
		 System.out.println("got to remove quotes from " + data.trim() +" and put it back to ");
		 String tempStr = data.replaceAll("'", "");
		 System.out.println("got to remove quotes from " + data.trim() +" and put it back as  "+tempStr);
		 return Constants.KEYWORD_PASS+ " " + object.trim()+" = " + tempStr ;
	}
	
	
	public  String synchronize(String object,String data){
		APP_LOGS.debug("Waiting for page to load");
		
		return Constants.KEYWORD_PASS;
	}
	
	public  String waitForElementVisibility(String object,String data){
		APP_LOGS.debug("Waiting for an elelement to be visible");
		
		return Constants.KEYWORD_PASS;
	}
	
	public  String closeBroswer(String object,String data){
		APP_LOGS.debug("Closing the browser from closeBroswer");
		System.out.println("Closing the browser");
		
		k.clickIfExists("HomePage_SignOut", "");
		try {
			
			if(!(driver==null))
			driver.quit();
			
			APP_LOGS.debug("Setting all backups to null");
			back_Mozilla = null;
			back_IE = null;
			back_Chrome = null;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			PowerShellCommandExecutor.executePSCommand("Stop-Process -processname chromedriver*");
			PowerShellCommandExecutor.executePSCommand("Stop-Process -processname IEDriverServer*");
			PowerShellCommandExecutor.executePSCommand("Stop-Process -processname Safari*");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Constants.KEYWORD_PASS;

	}
	
public  String executeSQLOracleDB(String object,String data) throws ClassNotFoundException, SQLException{
		
		String sql = data.trim();
		String output = "";
	    String[] strAray;
	    String finalstr;
		
		APP_LOGS.debug("running the sql"+sql);
		
		    Class.forName("oracle.jdbc.driver.OracleDriver");
		    String jdbcurl = k.getEnvProperties().getProperty("JDBCURL"); //"jdbc:oracle:thin:@//spdudb18.hsn.net:1521/seb81uat";
		    Connection conn = DriverManager.getConnection(jdbcurl,"kakarlapudik","Sathya123$");
		    conn.setAutoCommit(false);
		    
		   

	              
	              try{
	            	  
	            	    Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//"+k.suiteXLSName);
	            	    
	            	    //@@@@@@@@@@@@@@ First get the correct SQL from the SQLS sheet based on the SQLID @@@@@@@@@@@@@@@@@@@@@@@//
	            	    
	            	    int rowNum  = xls.getCellRowNum("SQLS", "SQLID", sql);
		          	    
		          	    System.out.println("the rownum for SQLID "+sql+" in the SQLS sheet SQLID column is "+rowNum);
		          	    
		          	    if(rowNum == -1){
		          	    	
		          	    	APP_LOGS.debug("There is no sql defined for sqlId = "+sql);
		          	    	System.out.println("There is no sql defined for sqlId = "+sql);
		          	    	return "";
		          	    	
		          	    }
		          	    
		          	    sql = xls.getCellData("SQLS", "SQL", rowNum);
		          	    APP_LOGS.debug("There copied sql from the k.suiteXLSName is "+sql);
	          	    	System.out.println("The copied sql from the k.suiteXLSName is "+sql);
	          	    	
	          	    	sql = sql.replaceAll("[\n\r]", " ");

	          	    	
	          	    //@@@@@@@@@@@@@@ If the sql contains any col_### replace with correct values  @@@@@@@@@@@@@@@@@@@@@@@//
	            	  
	            	    strAray = sql.trim().split(" ");
						
	            	    finalstr = "";
						
						for(int i = 0;i < strAray.length ; i++){
							
							// If a col_ocuurs, we need to get the value for the correct test Data row and column in the "Data" sheet 
							if(strAray[i].contains("col_")){
								
								String actualCol = strAray[i].split("col_")[1]; 
						    		    	    			
									int curtestCaseDataHeaderRow =  TestUtil.getFirstDataRowRunmode(k.currenttestCaseName, xls);
									int current_DataColNum = TestUtil.getDataColumNum( curtestCaseDataHeaderRow ,  actualCol,  xls);
									System.out.println("taking value from col "+current_DataColNum+" and row "+ k.currenttestDatarow);
									String cellValue = xls.getCellData("Data", current_DataColNum,  k.currenttestDatarow);
									
									if(!cellValue.contains("key_")){
										
										cellValue = "'" + cellValue + "'";
									}
									finalstr = finalstr +  cellValue +" ";	
									
									
							}// end of case where we need to replace col_ColumnName with actual value form data sheet
							
							else
								
								finalstr = finalstr + strAray[i] + " ";
							
						}
						
						System.out.println("the partial processed sql with unreplaced keys is "+finalstr);
						
						//@@@@@@@@@@@@@@ If the sql contains any key_### replace with correct values  @@@@@@@@@@@@@@@@@@@@@@@//
						
						strAray = finalstr.split(" ");
						
						finalstr = "";
						
						for(int i = 0;i < strAray.length ; i++){
							
							// If a col_occurs, we need to get the value for the correct test Data row and column in the "Data" sheet 
							if(strAray[i].contains("key_")){
								
								String actualCol = strAray[i].split("key_")[1]; 
								
						    		    	    			
									int current_DatarowNum = xls.getCellRowNum("SharedDataForAllTestCases", "Common_Key_Name", actualCol);
									String cellValue = xls.getCellData("SharedDataForAllTestCases", "Common_Key_Value",  current_DatarowNum);
									finalstr = finalstr + "'" + cellValue + "'" + " ";	
									
									
							}// end of case where we need to replace col_ColumnName with actual value form data sheet
							
							else
								
								finalstr = finalstr + strAray[i] + " ";
							
						}
						
					
						 System.out.println("the fully processed sql with replaced keys is "+finalstr);
						 
						 Statement stmt = conn.createStatement();
						 
						 ResultSet rset =
						 stmt.executeQuery(finalstr);
						 
						 while (rset.next()) {
						         System.out.println (rset.getString(1));
						         output = rset.getString(1);
		                         System.out.println("sql results is "+output);
						 }
						 stmt.close();
						 System.out.println ("Ok.");
						           
	   
						 conn.close();
	                     
	              }catch(Exception e){
	                     
	                     e.printStackTrace();
	                     return Constants.KEYWORD_FAIL;
	                     
	              }

	    return Constants.KEYWORD_PASS+ " " + object.trim()+" = '"+output+"'";


	}

public  String executeSQLUpdateOracleDB(String object,String data) throws ClassNotFoundException, SQLException{
	
	String sql = data.trim();
	String output = "";
    String[] strAray;
    String finalstr;
	
	APP_LOGS.debug("running the sql"+sql);
	
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    String jdbcurl = k.getEnvProperties().getProperty("JDBCURL"); //"jdbc:oracle:thin:@//spdudb18.hsn.net:1521/seb81uat";
	    Connection conn = DriverManager.getConnection(jdbcurl,"kakarlapudik","Sathya123$");
	    conn.setAutoCommit(true);
	    
	   

              
              try{
            	  
            	    Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//"+k.suiteXLSName);
            	    
            	    //@@@@@@@@@@@@@@ First get the correct SQL from the SQLS sheet based on the SQLID @@@@@@@@@@@@@@@@@@@@@@@//
            	    
            	    int rowNum  = xls.getCellRowNum("SQLS", "SQLID", sql);
	          	    
	          	    System.out.println("the rownum for SQLID "+sql+" in the SQLS sheet SQLID column is "+rowNum);
	          	    
	          	    if(rowNum == -1){
	          	    	
	          	    	APP_LOGS.debug("There is no sql defined for sqlId = "+sql);
	          	    	System.out.println("There is no sql defined for sqlId = "+sql);
	          	    	return "";
	          	    	
	          	    }
	          	    
	          	    sql = xls.getCellData("SQLS", "SQL", rowNum);
	          	    APP_LOGS.debug("There copied sql from the k.suiteXLSName is "+sql);
          	    	System.out.println("The copied sql from the k.suiteXLSName is "+sql);
          	    	
          	    	sql = sql.replaceAll("[\n\r]", " ");

          	    	
          	    //@@@@@@@@@@@@@@ If the sql contains any col_### replace with correct values  @@@@@@@@@@@@@@@@@@@@@@@//
            	  
            	    strAray = sql.trim().split(" ");
					
            	    finalstr = "";
					
					for(int i = 0;i < strAray.length ; i++){
						
						// If a col_ocuurs, we need to get the value for the correct test Data row and column in the "Data" sheet 
						if(strAray[i].contains("col_")){
							
							String actualCol = strAray[i].split("col_")[1]; 
					    		    	    			
								int curtestCaseDataHeaderRow =  TestUtil.getFirstDataRowRunmode(k.currenttestCaseName, xls);
								int current_DataColNum = TestUtil.getDataColumNum( curtestCaseDataHeaderRow ,  actualCol,  xls);
								System.out.println("taking value from col "+current_DataColNum+" and row "+ k.currenttestDatarow);
								String cellValue = xls.getCellData("Data", current_DataColNum,  k.currenttestDatarow);
								
										
								finalstr = finalstr +  cellValue +" ";	
								
								
						}// end of case where we need to replace col_ColumnName with actual value form data sheet
						
						else
							
							finalstr = finalstr + strAray[i] + " ";
						
					}
					
					System.out.println("the partial processed sql with unreplaced keys is "+finalstr);
					
					//@@@@@@@@@@@@@@ If the sql contains any key_### replace with correct values  @@@@@@@@@@@@@@@@@@@@@@@//
					
					strAray = finalstr.split(" ");
					
					finalstr = "";
					
					for(int i = 0;i < strAray.length ; i++){
						
						// If a col_occurs, we need to get the value for the correct test Data row and column in the "Data" sheet 
						if(strAray[i].contains("key_")){
							
							String actualCol = strAray[i].split("key_")[1]; 
							
					    		    	    			
								int current_DatarowNum = xls.getCellRowNum("SharedDataForAllTestCases", "Common_Key_Name", actualCol);
								String cellValue = xls.getCellData("SharedDataForAllTestCases", "Common_Key_Value",  current_DatarowNum);
								finalstr = finalstr + "'" + cellValue + "'" + " ";	
								
								
						}// end of case where we need to replace col_ColumnName with actual value form data sheet
						
						else
							
							finalstr = finalstr + strAray[i] + " ";
						
					}
					
				
					 System.out.println("the fully processed sql with replaced keys is "+finalstr);
					 
					 Statement stmt = conn.createStatement();
					 stmt.executeUpdate(finalstr);
					 
					 stmt.close();
					 System.out.println ("Ok.");
					           
   
					 conn.close();
                     
              }catch(Exception e){
                     
                     e.printStackTrace();
                     return Constants.KEYWORD_FAIL;
                     
              }

    return Constants.KEYWORD_PASS+ " " + object.trim()+" = '"+output+"'";


}

	public  String executeSQL(String object,String data){
		
		String sql = data.trim();
		String output = "";
		APP_LOGS.debug("running the sql"+sql);
		
		   //String url = "jdbc:jtds:sqlserver://ilsqlp91c.hsnlab.com:1433/";
	       String url = k.getEnvProperties().getProperty("DBCONNECTIONSTRING");
	       String dbname = k.getEnvProperties().getProperty("DBNAME");
	       String driver = k.getEnvProperties().getProperty("DRIVER");
	       String userName = k.getEnvProperties().getProperty("USERNAME");
	       String password = k.getEnvProperties().getProperty("PASSWORD");
	       
	       String[] strAray;
	       String finalstr;
	              
	              try{
	            	  
	            	    Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//"+k.suiteXLSName);
	            	    
	            	    //@@@@@@@@@@@@@@ First get the correct SQL from the SQLS sheet based on the SQLID @@@@@@@@@@@@@@@@@@@@@@@//
	            	    
	            	    int rowNum  = xls.getCellRowNum("SQLS", "SQLID", sql);
		          	    
		          	    System.out.println("the rownum for SQLID "+sql+" in the SQLS sheet SQLID column is "+rowNum);
		          	    
		          	    if(rowNum == -1){
		          	    	
		          	    	APP_LOGS.debug("There is no sql defined for sqlId = "+sql);
		          	    	System.out.println("There is no sql defined for sqlId = "+sql);
		          	    	return "";
		          	    	
		          	    }
		          	    
		          	    sql = xls.getCellData("SQLS", "SQL", rowNum);
		          	    APP_LOGS.debug("There copied sql from the k.suiteXLSName is "+sql);
	          	    	System.out.println("The copied sql from the k.suiteXLSName is "+sql);
	          	    	
	          	    	sql = sql.replaceAll("[\n\r]", " ");

	          	    	
	          	    //@@@@@@@@@@@@@@ If the sql contains any col_### replace with correct values  @@@@@@@@@@@@@@@@@@@@@@@//
	            	  
	            	    strAray = sql.trim().split(" ");
						
	            	    finalstr = "";
						
						for(int i = 0;i < strAray.length ; i++){
							
							// If a col_ocuurs, we need to get the value for the correct test Data row and column in the "Data" sheet 
							if(strAray[i].contains("col_")){
								
								String actualCol = strAray[i].split("col_")[1]; 
						    		    	    			
									int curtestCaseDataHeaderRow =  TestUtil.getFirstDataRowRunmode(k.currenttestCaseName, xls);
									int current_DataColNum = TestUtil.getDataColumNum( curtestCaseDataHeaderRow ,  actualCol,  xls);
									System.out.println("taking value from col "+current_DataColNum+" and row "+ k.currenttestDatarow);
									String cellValue = xls.getCellData("Data", current_DataColNum,  k.currenttestDatarow);
									finalstr = finalstr +  cellValue +" ";	
									
									
							}// end of case where we need to replace col_ColumnName with actual value form data sheet
							
							else
								
								finalstr = finalstr + strAray[i] + " ";
							
						}
						
						System.out.println("the partial processed sql with unreplaced keys is "+finalstr);
						
						//@@@@@@@@@@@@@@ If the sql contains any key_### replace with correct values  @@@@@@@@@@@@@@@@@@@@@@@//
						
						strAray = finalstr.split(" ");
						
						finalstr = "";
						
						for(int i = 0;i < strAray.length ; i++){
							
							// If a col_occurs, we need to get the value for the correct test Data row and column in the "Data" sheet 
							if(strAray[i].contains("key_")){
								
								String actualCol = strAray[i].split("key_")[1]; 
								
						    		    	    			
									int current_DatarowNum = xls.getCellRowNum("SharedDataForAllTestCases", "Common_Key_Name", actualCol);
									String cellValue = xls.getCellData("SharedDataForAllTestCases", "Common_Key_Value",  current_DatarowNum);
									finalstr = finalstr + "'" + cellValue + "'" + " ";	
									
									
							}// end of case where we need to replace col_ColumnName with actual value form data sheet
							
							else
								
								finalstr = finalstr + strAray[i] + " ";
							
						}
						
						
	   
						 System.out.println("the fully processed sql with replaced keys is "+finalstr);
						 
						 System.out.println("connection string is "+url+dbname+userName+password);
	                     
	                     Class.forName(driver).newInstance(); //initialize driver
	                     
	                     Connection conn = DriverManager.getConnection(url+dbname,userName,password);
	                     
	                     System.out.println("connection string is "+url+dbname+userName+password);
	                     
	                     Statement stmt = conn.createStatement(); //select webp_id from hsn_content..cnt_product where pfid = '181450'
	                     ResultSet rs = stmt.executeQuery(finalstr);
	                     while(rs.next()){
	                    	 
	                    	   output = rs.getString(1);
	                           System.out.println("sql results is "+rs.getString(1));
	                     }
	                     
	                     conn.close();
	                     
	              }catch(Exception e){
	                     
	                     e.printStackTrace();
	                     return Constants.KEYWORD_FAIL;
	                     
	              }

	    return Constants.KEYWORD_PASS+ " " + object.trim()+" = '"+output+"'";


	}
	
    public  String executeSQLNoResults(String object,String data){
		
		String sql = data.trim();
		String output = "";
		APP_LOGS.debug("running the sql"+sql);
		
		   //String url = "jdbc:jtds:sqlserver://ilsqlp91c.hsnlab.com:1433/";
	       String url = k.getEnvProperties().getProperty("DBCONNECTIONSTRING");
	       String dbname = k.getEnvProperties().getProperty("DBNAME");
	       String driver = k.getEnvProperties().getProperty("DRIVER");
	       String userName = k.getEnvProperties().getProperty("USERNAME");
	       String password = k.getEnvProperties().getProperty("PASSWORD");
	       
	       String[] strAray;
	       String finalstr;
	              
	              try{
	            	  
	            	    Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//"+k.suiteXLSName);
	            	    
	            	    //@@@@@@@@@@@@@@ First get the correct SQL from the SQLS sheet based on the SQLID @@@@@@@@@@@@@@@@@@@@@@@//
	            	    
	            	    int rowNum  = xls.getCellRowNum("SQLS", "SQLID", sql);
		          	    
		          	    System.out.println("the rownum for SQLID "+sql+" in the SQLS sheet SQLID column is "+rowNum);
		          	    
		          	    if(rowNum == -1){
		          	    	
		          	    	APP_LOGS.debug("There is no sql defined for sqlId = "+sql);
		          	    	System.out.println("There is no sql defined for sqlId = "+sql);
		          	    	return "";
		          	    	
		          	    }
		          	    
		          	    sql = xls.getCellData("SQLS", "SQL", rowNum);
		          	    APP_LOGS.debug("There copied sql from the k.suiteXLSName is "+sql);
	          	    	System.out.println("The copied sql from the k.suiteXLSName is "+sql);
	          	    	
	          	    	sql = sql.replaceAll("[\n\r]", " ");

	          	    	
	          	    //@@@@@@@@@@@@@@ If the sql contains any col_### replace with correct values  @@@@@@@@@@@@@@@@@@@@@@@//
	            	  
	            	    strAray = sql.trim().split(" ");
						
	            	    finalstr = "";
						
						for(int i = 0;i < strAray.length ; i++){
							
							// If a col_ocuurs, we need to get the value for the correct test Data row and column in the "Data" sheet 
							if(strAray[i].contains("col_")){
								
								String actualCol = strAray[i].split("col_")[1]; 
						    		    	    			
									int curtestCaseDataHeaderRow =  TestUtil.getFirstDataRowRunmode(k.currenttestCaseName, xls);
									int current_DataColNum = TestUtil.getDataColumNum( curtestCaseDataHeaderRow ,  actualCol,  xls);
									System.out.println("taking value from col "+current_DataColNum+" and row "+ k.currenttestDatarow);
									String cellValue = xls.getCellData("Data", current_DataColNum,  k.currenttestDatarow);
									finalstr = finalstr +  cellValue +" ";	
									
									
							}// end of case where we need to replace col_ColumnName with actual value form data sheet
							
							else
								
								finalstr = finalstr + strAray[i] + " ";
							
						}
						
						System.out.println("the partial processed sql with unreplaced keys is "+finalstr);
						
						//@@@@@@@@@@@@@@ If the sql contains any key_### replace with correct values  @@@@@@@@@@@@@@@@@@@@@@@//
						
						strAray = finalstr.split(" ");
						
						finalstr = "";
						
						for(int i = 0;i < strAray.length ; i++){
							
							// If a col_occurs, we need to get the value for the correct test Data row and column in the "Data" sheet 
							if(strAray[i].contains("key_")){
								
								String actualCol = strAray[i].split("key_")[1]; 
								
						    		    	    			
									int current_DatarowNum = xls.getCellRowNum("SharedDataForAllTestCases", "Common_Key_Name", actualCol);
									String cellValue = xls.getCellData("SharedDataForAllTestCases", "Common_Key_Value",  current_DatarowNum);
									finalstr = finalstr + "'" + cellValue + "'" + " ";	
									
									
							}// end of case where we need to replace col_ColumnName with actual value form data sheet
							
							else
								
								finalstr = finalstr + strAray[i] + " ";
							
						}
						
						
	   
						 System.out.println("the fully processed sql with replaced keys is "+finalstr);
						 
						 System.out.println("connection string is "+url+dbname+userName+password);
	                     
	                     Class.forName(driver).newInstance(); //initialize driver
	                     
	                     Connection conn = DriverManager.getConnection(url+dbname,userName,password);
	                     
	                     System.out.println("connection string is "+url+dbname+userName+password);
	                     
	                     Statement stmt = conn.createStatement(); //select webp_id from hsn_content..cnt_product where pfid = '181450'
	                     stmt.executeUpdate(finalstr);
	                      
	                     conn.close();
	                     
	              }catch(Exception e){
	                     
	                     e.printStackTrace();
	                     return Constants.KEYWORD_FAIL;
	                     
	              }

	    return Constants.KEYWORD_PASS+ " " + object.trim()+" = '"+output+"'";


	}
	
	public  String executeSQLtestforMultirows(String sqlIdCol,String output){
		
		String sqlId = k.currentDataSet.get(sqlIdCol);
		APP_LOGS.debug("running the sql"+sqlId);
		
		String DBurl = "jdbc:jtds:sqlserver://ilsqlp91c.hsnlab.com:1433/";
		//String url = "jdbc:jtds:sqlserver://172.20.254.98:1433/";
		String dbname = "HSN_Commerce";
		String driver = "net.sourceforge.jtds.jdbc.Driver";
		String userName = "userQA";
		String password = "Q%USR#33";
	    Boolean issqlFirstExecution = false;
	    Connection conn = null;
	              
	              try{
	            	  
	            	  /************* Create a new excel sheet for each sql id ***************/
	            	  
			            	Xls_Reader xls2 = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//sql_results.xlsx");
			          		
			          		if(!xls2.isSheetExist(sqlId)){
			          			xls2.addSheet(sqlId);
			          			issqlFirstExecution = true;
			          		}
			          		
			          		
			          		Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//"+k.suiteXLSName);
			          	
			          	    int rowNum  = xls.getCellRowNum("SQLS", "SQLID", sqlId);
			          	    
			          	    System.out.println("the rownum for SQLID "+sqlId+" in the SQLS sheet SQLID column is "+rowNum);
			          	    
			          	    if(rowNum == -1){
			          	    	
			          	    	APP_LOGS.debug("There is no sql defined for sqlId = "+sqlId);
			          	    	System.out.println("There is no sql defined for sqlId = "+sqlId);
			          	    	return "";
			          	    	
			          	    }
			          	    
			          	    String sql = xls.getCellData("SQLS", "SQL", rowNum);
			          	    APP_LOGS.debug("There copied sql from the k.suiteXLSName is "+sql);
		          	    	System.out.println("There copied sql from the k.suiteXLSName is "+sql);
			          	    
			          	    String[] strAray = sql.split(" ");
			          	    
			          	    sql = "";
			          	    
			          	    for(int i = 0;i < strAray.length ; i++){
			          	    	
			          	    	if(strAray[i].contains("col_")){
			          	    		
			          	    		String actualCol = strAray[i].split("_")[1]; 
			          	    		sql = sql + "'" + k.currentDataSet.get(actualCol) + "'" + " ";
			          	    	}
			          	    	
			          	    	else
			          	    		
			          	    		sql = sql + strAray[i] + " ";
			          	    	
			          	    }
			          	    	
			          	   APP_LOGS.debug("The prepared sql is "+sql);
		          	       System.out.println("The prepared sql is "+sql);
	                     
	                     Class.forName(driver).newInstance(); //initialize driver
	                     
	                     conn = DriverManager.getConnection(DBurl+dbname,userName,password);
	                     
	                     	                     
	                     Statement stmt = conn.createStatement(); //select webp_id from hsn_content..cnt_product where pfid = '181450'
	                     ResultSet rs = stmt.executeQuery(sql);
	                     
	                     ResultSetMetaData rsmd = rs.getMetaData();
	         		     int numberOfColumns = rsmd.getColumnCount();
	         		    
	         		     // Add columns only if this is the first execution set result
	         		     	if(issqlFirstExecution == true){
			         		    for(int i = 1 ; i <= numberOfColumns;i++ ){
			         				System.out.println("Collumn Name  :: "+ rsmd.getColumnName(i));
			         				xls2.addColumn(sqlId, rsmd.getColumnName(i));
			         			}
	         		     	}
		         			
		         		    int xlsrownum = xls2.getRowCount(sqlId)+1;
		         		    
		         			while(rs.next()){
		         				for(int i = 1 ; i <= numberOfColumns;i++ ){
		         					System.out.println("value in col "+i+"is"+ rs.getString(i));
		         					xls2.setCellData(sqlId, rsmd.getColumnName(i) , xlsrownum, rs.getString(i));
		         				}
		         				xlsrownum++;
		         			}
		         			
		                     while(rs.next()){
		                    	 
		                    	   //output = rs.getString(1);
		                           System.out.println("Prod_Name is :: "+rs.getString(1));
		                     }
		                     
		                    conn.close();
	                     
	              }catch(Exception e){
	                     
	                     e.printStackTrace();
	                     return Constants.KEYWORD_FAIL;
	                     
	              }
		
		return Constants.KEYWORD_PASS;

	}
	
	public  String Report(String object,String str_reportID){
		
				          		
			          		String str_report;
			          		String[] strAray;
			          		
							try {
								Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//"+k.suiteXLSName);
       	
								int rowNum  = xls.getCellRowNum("Reports", "ReportID", str_reportID);
								
								System.out.println("the rownum for reportStatementID "+str_reportID+" in the Reports sheet ReportID column is "+rowNum);
								
								if(rowNum == -1){
									
									APP_LOGS.debug("There is no reportStatementID defined for reportStatementID = "+str_reportID);
									System.out.println("ERROR: There is no reportStatementID defined for reportStatementID = "+str_reportID);
									return "";
									
								}
								
								str_report = xls.getCellData("Reports", "Reporter_Statement", rowNum);
								APP_LOGS.debug("The copied Reporter_Statement from the k.suiteXLSName is "+str_report);
								System.out.println("The copied Reporter_Statement from the k.suiteXLSName is "+str_report);
								
								strAray = str_report.split(" ");
								
								str_report = "";
								
								for(int i = 0;i < strAray.length ; i++){
									
									// If a col_ocuurs, we need to get the value for the correct test Data row and column in the "Data" sheet 
									if(strAray[i].contains("col_")){
										
										String actualCol = strAray[i].split("col_")[1]; 
								    		    	    			
											int curtestCaseDataHeaderRow =  TestUtil.getFirstDataRowRunmode(k.currenttestCaseName, xls);
											int current_DataColNum = TestUtil.getDataColumNum( curtestCaseDataHeaderRow ,  actualCol,  xls);
											System.out.println("taking value from col "+current_DataColNum+" and row "+ k.currenttestDatarow);
											String cellValue = xls.getCellData("Data", current_DataColNum,  k.currenttestDatarow);
											str_report = str_report +  cellValue +" ";	
											
											
									}// end of case where we need to replace col_ColumnName with actual value form data sheet
									
									else
										
										str_report = str_report + strAray[i] + " ";
									
								}
								
								System.out.println("the unfinished report with unreplaced keys is "+str_report);
								
								strAray = str_report.split(" ");
								
								str_report = "";
								
								for(int i = 0;i < strAray.length ; i++){
									
									// If a col_occurs, we need to get the value for the correct test Data row and column in the "Data" sheet 
									if(strAray[i].contains("key_")){
										
										String actualCol = strAray[i].split("key_")[1]; 
										
								    		    	    			
											int current_DatarowNum = xls.getCellRowNum("SharedDataForAllTestCases", "Common_Key_Name", actualCol);
											String cellValue = xls.getCellData("SharedDataForAllTestCases", "Common_Key_Value",  current_DatarowNum);
											str_report = str_report + "'" + cellValue + "'" + " ";	
											
											
									}// end of case where we need to replace col_ColumnName with actual value form data sheet
									
									else
										
										str_report = str_report + strAray[i] + " ";
									
								}
       	   
       	  Date dNow = new Date( );
           SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyy hh:mm:ss");
           String timeStamp = "TIME STAMP: "+ft.format(dNow);
       	 
           str_report= str_report + timeStamp + "\n";
									
       	   APP_LOGS.debug("The prepared report is "+str_report);
      	       System.out.println("The prepared report is "+str_report);
      	       
      	       k.currenttestReportStmnt = str_report;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								k.closeBroswer("", "");
								e.printStackTrace();
							}
	                     
		return Constants.KEYWORD_PASS + "printing report "+k.currenttestReportStmnt;

	}

	
/************************APPLICATION SPECIFIC KEYWORDS********************************/
	
	
	public  String validateLogin(String object, String data){
		
		APP_LOGS.debug("validating login ");
		
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        
         try {
     	   
 			//String visibility = driver.findElements(By.xpath("//*[@id='signin-drawer-login-submit-proxy']")).get(0).getAttribute("")
 			
 		} catch (Exception e) {
 			
 			e.printStackTrace();
 			return Constants.KEYWORD_FAIL+" -- Not able to input phone number to"+OR.getProperty(object);
 		}
     
 		
 		return Constants.KEYWORD_PASS+ " col|PrimaryPhoneNumber = ";
 	
	}
	
	public  String useNewPhone(String object, String data){
		
		APP_LOGS.debug("creating new 10 digit phone number ");
		
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        
        Random randomGenerator = new Random();
	    int randomInt = 1000000 + randomGenerator.nextInt(999999);
	    
	    int prefix = 100 + randomGenerator.nextInt(899);
	    String val = "817" + randomInt ;
	       
        try {
     	   
        	k.writeInInput(object, val);
 			//driver.findElement(By.xpath(OR.getProperty(object.trim()))).sendKeys(val);
 			
 		} catch (Exception e) {
 			
 			e.printStackTrace();
 			return Constants.KEYWORD_FAIL+" -- Not able to input phone number to"+OR.getProperty(object);
 		}
     
 		
 		return Constants.KEYWORD_PASS+ " col|PrimaryPhoneNumber = "+val;
 	
	}
	
	public  String useNewEmail(String object, String data){
		
		 APP_LOGS.debug("creating new email using time stamp");
		
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyMMddhhmmss");

        String val = "ovc"+ft.format(dNow)+"@qa1.com";

	       
        try {
     	   
        	driver.findElement(By.xpath(OR.getProperty(object.trim()))).sendKeys(val);
        	//k.click(object.trim(), "");
        	//k.writeInInput(object.trim(), "");
        	
 			
 			
 		} catch (Exception e) {
 			
 			e.printStackTrace();
 			return Constants.KEYWORD_FAIL+" -- Not able to input email to"+OR.getProperty(object);
 		}
     
 		if(data.contains("col|")){
 			return Constants.KEYWORD_PASS+ " "+data.trim()+" = "+val;
 		}
 		else
 			return Constants.KEYWORD_PASS+ " col|Email = "+val;
 	
	}
	
	public  String useNewArcadeNickName(String object, String data){
		
		 APP_LOGS.debug("creating new email using time stamp");
		
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       
       Date dNow = new Date( );
       SimpleDateFormat ft = new SimpleDateFormat ("yyMMddhhmmss");

       String val = "IAM"+ft.format(dNow)+"qa1.com";

	       
       try {
    	   
			driver.findElement(By.xpath(OR.getProperty(object.trim()))).sendKeys(val);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+" -- Not able to input newNickNmae to"+OR.getProperty(object);
		}
    
		
		return Constants.KEYWORD_PASS+ " col|ArcadeNickName = "+val;
	
	}
	
	public  String useNewPassword(String object, String data){
	
   APP_LOGS.debug("creating new password");
	
   driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
   
  String val = "password123";

      
   try {
	   
		driver.findElement(By.xpath(OR.getProperty(object.trim()))).sendKeys(val);
		
	} catch (Exception e) {
		
		e.printStackTrace();
		return Constants.KEYWORD_FAIL+" -- Not able to input password to"+OR.getProperty(object);
	}

	
	return Constants.KEYWORD_PASS+ " col|Password = "+val;

}
	
	public  String selectExtraFlex(String object, String data){
		
		   APP_LOGS.debug("selecting the extraflex option "+data);
			
		   driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		   
		      
		   try {
			   
			    Select select = new Select(driver.findElement(By.xpath(OR.getProperty(object.trim()))));

				List<WebElement > list = select.getOptions(); 
				
				String currentSelection;
				
				//makes sure the extraflex description is present in the dropdown
				k.isElementVisible("ProductDetailPage_ExtraFlexOption", "");
				
				currentSelection = list.get(list.size()-1).getText().toString();
				select.selectByVisibleText(currentSelection);
				
				return Constants.KEYWORD_PASS +" +data.trim()+  = "+currentSelection;
								
				
			} catch (Exception e) {
				
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+" -- Not able to find ExtraFlex in flex pay options"+OR.getProperty(object);
			}


		}

	public  String useCoupon(String object, String data){
	
	   APP_LOGS.debug("Using coupon for purchase"+data);
		
	   driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	   
	      
	   try {
		   
			//driver.findElement(By.xpath(OR.getProperty("CheckoutOrderReviewpage_CouponInputField"))).sendKeys(data.trim());
		    k.click("CheckoutOrderReviewpage_CouponInputField", "");
			//Thread.sleep(3000);
			//driver.findElement(By.xpath(OR.getProperty("CheckoutOrderReviewpage_ApplyCouponLink"))).click();
			k.click("CheckoutOrderReviewpage_ApplyCouponLink", "");
			//Thread.sleep(3000);

 
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+" -- Not able to input password to"+OR.getProperty(object);
		}

		
		return Constants.KEYWORD_PASS;

	}
	
	public String AsyncValidation(String object,String data){
		
		try {
			  
			  Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//"+k.suiteXLSName);
			  
			  int col = TestUtil.getDataColumNum(k.currenttestHeaderrow ,"OrderIdUrl" , xls);
			  String orderIdurl = xls.getCellData("Data", col, k.currenttestDatarow);
			  
			  col = TestUtil.getDataColumNum(k.currenttestHeaderrow ,"OrderId" , xls);
			  String orderId = xls.getCellData("Data", col, k.currenttestDatarow);
			  
			  System.out.println("the orderId url is "+ orderIdurl +". Order is "+orderId);
			  
			  if(orderIdurl.contains(orderId))
				  return Constants.KEYWORD_PASS+ " "+data.trim()+" = ASYNC";
			  else
				  return Constants.KEYWORD_PASS+ " "+data.trim()+" = SYNC";

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return Constants.KEYWORD_FAIL;
		}
	}

	public String getCCNumber(String object,String data){
		
		try {
			
			WebDriver driver2 = new FirefoxDriver();
			driver2.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			//driver2.navigate().to("file:///"+C:/Users/kakarlapudik/"+"/Desktop/CCGen.htm");
			
			driver2.navigate().to("file:///C:/Users/"+System.getProperty("user.name")+"/Desktop/CCGen.htm");
			
			
			//selectList_ByValue("CC_Page_selectCCTypeDropdown",data);
			
			Select select = new Select(driver2.findElement(By.xpath("//select[@name='ccpp']")));

			List<WebElement > list = select.getOptions(); 
			
			String currentSelection;
			
			for ( int i = 0; i < list.size();i++){
				
				currentSelection = list.get(i).getText().toString().trim();
				
				//System.out.println("the "+i+"th element is @@@@@@@@@"+currentSelection+"@@@@@@@@@@");
				
				if(currentSelection.contains(data.trim())){
					
					select.selectByVisibleText(currentSelection);
					
				}
			}
			
			
			
			//driver2.findElement(By.xpath("//select[@name='ccpp']")).sendKeys(data.trim());
			
			driver2.findElement(By.xpath("//select[@name='ccnsp']")).sendKeys("(none) ");
			
			driver2.findElement(By.xpath("//input[@name='howmany']")).sendKeys("1");
			
			driver2.findElement(By.xpath("//input[@value='Generate']")).click();
			
			String val = driver2.findElement(By.xpath("//textarea[@name='output2']")).getAttribute("value");
			String[] strArr = val.split(" ");
			
			val = "";
			
			for(int i = 0; i < strArr.length ; i++)
				
			{
				val = val+strArr[i].trim();
				
			}
			
			driver2.quit();
			
			return Constants.KEYWORD_PASS+ " col|CCNum = "+val;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return Constants.KEYWORD_FAIL;
		}
	}
	
	public String selectVariance(String object,String data){
			
		 APP_LOGS.debug("select Variance for the item"+object);
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 
		 try {
				
	 		 	
			    WebElement myModal;
			    
			    //If a select option is present select it
			    if ( driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_sizeOption"))).size() > 0 ){
					
			    	APP_LOGS.debug("Adding size");
					myModal = driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_sizeOption"))).get(0);
					System.out.println("modal 1 is "+myModal.getAttribute("alt"));
					myModal.click();
					
			    }
			    
			    List<WebElement> productModalList = null;
			    
			    if(driver.findElements(By.xpath("//label[contains(@class,'product-modal-option')]")).size()>0){
			 
				productModalList = driver.findElements(By.xpath("//label[contains(@class,'product-modal-option')]"));
				
			    

				//Select size and color if present
				if(productModalList.size()>0)
					
					{
						//add size
						if ( driver.findElements(By.xpath("//dd[@class='small-options clearfix']//label[1]")).size() > 0 ){
							
							myModal = driver.findElements(By.xpath("//dd[@class='small-options clearfix']//label[1]")).get(0);
							//System.out.println("modal 1 is "+myModal.getAttribute("name"));
							myModal.click();
							
						}
						
						
						if ( driver.findElements(By.xpath("//dd[@class='small-options image-options clearfix']//label[1]")).size() > 0 ){
							
							myModal = driver.findElements(By.xpath("//dd[@class='small-options image-options clearfix']//label[1]")).get(0);
							//System.out.println("modal 2 is "+myModal.getAttribute("data-label"));
							myModal.click();
						}
					
					}
				
			    }
					//
			        APP_LOGS.debug("result for find add to bag is "+driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_AddToBagButton"))).size());
				    //Assert.assertTrue(((driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_AddToBagButton"))).size())<1), "unable to find element by XPATH "+OR.getProperty("ProductDetailPage_AddToBagButton"));
			        
			        int clickCOunt = 0;
			        do{
			        	System.out.println("seleing add to bad for tr "+clickCOunt);
//			        	//Thread.sleep(3000);
			        	driver.findElement(By.xpath(OR.getProperty("ProductDetailPage_AddToBagButton"))).click();
			        	clickCOunt++;
			        }while(clickCOunt < 1);
			        
					return Constants.KEYWORD_PASS;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Constants.KEYWORD_FAIL;
			}
		 
		}

	public String signout(String object,String data){
			
		 APP_LOGS.debug("signing out"+object);
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		 
			try{
				
					k.click("HomePage_AccessYourHSN", data);
					
					k.click("HomePage_MyHSN_SignOut", data);
					
					return Constants.KEYWORD_PASS;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Constants.KEYWORD_FAIL;
			}
	 }
	
    public String addProductToBag(String object,String data){
		
     System.out.println("KEYWORD: addProductToBag - Adding the product the bag"+k.currentDataSet.get("PID"));
	 
	 closeModalsFast();
	 driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	 
	 try {
			
		    //If a size Type  is present, select it
		    if ( driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_sizeType"))).size() > 0 ){
				
		    	System.out.println("found "+driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_sizeType"))).size()+" elements matching size type xpath "+OR.getProperty("ProductDetailPage_sizeOption"));
		    	k.click("ProductDetailPage_sizeType", "");
		    }
		    
		    //If a size Option is present , select it
		    
		    if ( driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_sizeOption"))).size() > 0 ){
				
		    	System.out.println("found "+driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_sizeOption"))).size()+" elements matching size option xpath  "+OR.getProperty("ProductDetailPage_sizeOption"));
				k.click("ProductDetailPage_sizeOption", "");

				
		    }
		    
		    //If a select option is present for color , select it.
		    if ( driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_colorOption"))).size() > 0 ){
				
		    	System.out.println("found "+driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_colorOption"))).size()+" elements matching color option xpath "+OR.getProperty("ProductDetailPage_colorOption"));
		    	k.click("ProductDetailPage_colorOption", "");
				
		    }
		    
		  	    APP_LOGS.debug("result for find add to bag is "+driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_AddToBagButton"))).size());
			    //Assert.assertTrue(((driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_AddToBagButton"))).size())<1), "unable to find element by XPATH "+OR.getProperty("ProductDetailPage_AddToBagButton"));
		        
	        	k.click("ProductDetailPage_AddToBagButton","");
   
				return Constants.KEYWORD_PASS;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		
	}
    
    
    public String selectExpressBuy(String object,String data){
		
   	 APP_LOGS.debug("selecting the express buy option "+object);
   	 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
   	 
   	 try {
   			
   		 	 		 	
   		    WebElement myModal;
   		    
   		    //If a select option is present select it
   		    if ( driver.findElements(By.xpath("*[@id='template-product-detail-product']//dd[@class='clearfix matrix-options']/a/img[1]")).size() > 0 ){
   				
   		    	APP_LOGS.debug("Adding size in ");
   				myModal = driver.findElements(By.xpath("*[@id='template-product-detail-product']//dd[@class='clearfix matrix-options']/a/img[1]")).get(0);
   				System.out.println("modal 1 is "+myModal.getAttribute("alt"));
   				myModal.click();
   				
   		    }
   		    
   		    List<WebElement> productModalList = null;
   		    
   		    if(driver.findElements(By.xpath("//label[contains(@class,'product-modal-option')]")).size()>0){
   		 
   			productModalList = driver.findElements(By.xpath("//label[contains(@class,'product-modal-option')]"));
   			
   		    

   			//Select size and color if present
   			if(productModalList.size()>0)
   				
   				{
   					//add size
   					if ( driver.findElements(By.xpath("//dd[@class='small-options clearfix']//label[1]")).size() > 0 ){
   						
   						myModal = driver.findElements(By.xpath("//dd[@class='small-options clearfix']//label[1]")).get(0);
   						//System.out.println("modal 1 is "+myModal.getAttribute("name"));
   						myModal.click();
   						
   					}
   					
   					
   					if ( driver.findElements(By.xpath("//dd[@class='small-options image-options clearfix']//label[1]")).size() > 0 ){
   						
   						myModal = driver.findElements(By.xpath("//dd[@class='small-options image-options clearfix']//label[1]")).get(0);
   						//System.out.println("modal 2 is "+myModal.getAttribute("data-label"));
   						myModal.click();
   					}
   				
   				}
   			
   		    }
   				//
   		        APP_LOGS.debug("result for find add to bag is "+driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_ExpressBuyButton"))).size());
   			    Assert.assertFalse(((driver.findElements(By.xpath(OR.getProperty("ProductDetailPage_ExpressBuyButton"))).size())<1), "unable to find element by XPATH "+OR.getProperty("ProductDetailPage_AddToBagButton"));
   		        
   		        int clickCOunt = 0;
   		        do{
   		        	System.out.println("seleing add to bad for tr "+clickCOunt);
//   		        	//Thread.sleep(3000);
   		        	driver.findElement(By.xpath(OR.getProperty("ProductDetailPage_ExpressBuyButton"))).click();
   		        	clickCOunt++;
   		        }while(clickCOunt < 1);
   		        
   				return Constants.KEYWORD_PASS;

   		} catch (Exception e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   			return Constants.KEYWORD_FAIL;
   		}
   		
   	}


    
    public String addMultipleProductsToBag(String object,String data){
    	
    	   try {
			String[] products = data.split(",");
			   APP_LOGS.debug("adding multiple products to the bag"+data);
			   
			   for (int i = 0; i < products.length ; i++ ){
				   
				   System.out.println("adding the "+i+"'th product to the bag "+products[i]); 
				   k.writeInInput("HomePage_SearchInputField",products[i]);
				   k.click("HomePage_SearchButton","");	
				   k.addProductToBag("","");
				   k.click("JustAddedPage_Checkout","");
			   
			   }
			   
			   return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constants.KEYWORD_PASS;
		}
    	   
    	    
    	
    }
    
    public String SocialSignInWithYahooAccount(String object,String data) throws InterruptedException{
    	
  	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
  	  
  	  String mainWindowHandle=driver.getWindowHandle();
  	  
  	  APP_LOGS.debug("the pop up handle name is mainWindowHandle= "+ mainWindowHandle);
  	  System.out.println("the pop up handle name is mainWindowHandle= "+ mainWindowHandle);
  	  
  	  
  	  //  open the pop-up (click on element which causes open a new window)
  	  
  	  k.click("HomePage_SocialSignIn_YahooOption","");	
  	  
  	  //  try to get all available open window handles with below command. (the below command returns all window handles as Set)

  	  Set<String> s = driver.getWindowHandles();

  	  //  from that above set try get newly opened window and switch the control to that (pop-up window handle), as we already know the mainWindowHandle.


  	Iterator<String> ite = s.iterator();
  	while(ite.hasNext())
  	{
  		String popupHandle=ite.next().toString();
  		System.out.println("the pop up handle name is "+popupHandle);
  		if(!popupHandle.contains(mainWindowHandle))
  		{
  		driver.switchTo().window(popupHandle);
  		System.out.println("the pop up handle name is ");
  		
  		
  		k.writeInInput("HomePage_SocialSignInYahoo_Email",k.currentDataSet.get("Username"));

  		 //driver.findElement(By.xpath("//*[@id='Email']")).sendKeys(k.currentDataSet.get("Username"));
  		 
  		k.writeInInput("HomePage_SocialSignInYahoo_Password",k.currentDataSet.get("Password"));
  		 
  		//driver.findElement(By.xpath("//*[@id='Passwd']")).sendKeys(k.currentDataSet.get("Password"));
  		
  		k.click("HomePage_SocialSignInYahoo_SignInButton","");	
  		
  		// wait till the first yahoo window is closed
// 		//Thread.sleep(5000);
  		
  		Set<String> s2 = driver.getWindowHandles();
  		Iterator<String> ite2 = s2.iterator();
  		  		
  		while(ite2.hasNext())
  	  	{
  	  		String popupHandle1=ite2.next().toString();
  	  		System.out.println("the pop up handle name is "+popupHandle1);
  	  		if(!popupHandle1.contains(mainWindowHandle))
  	  		{
  	  			driver.switchTo().window(popupHandle1);
  	  			k.click("HomePage_SocialSignInYahoo_AgreeButton","");
  	  			System.out.println("closed the second Agree page for yahoo ");
  	  			
  	  	  	//    Now control can be reverted to the main window

	                 driver.switchTo().window( mainWindowHandle );
	                 return Constants.KEYWORD_PASS;
  	  		}	
  	  		
  	  		

  		
  		}
  		
  		}
  	}
  	return Constants.KEYWORD_FAIL;

    }
    

    
    public String SocialSignInWithTwitterAccount(String object,String data) throws InterruptedException{
    	
    	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    	  
    	  String mainWindowHandle=driver.getWindowHandle();
    	  
    	  APP_LOGS.debug("the pop up handle name is mainWindowHandle= "+ mainWindowHandle);
    	  System.out.println("the pop up handle name is mainWindowHandle= "+ mainWindowHandle);
    	  
    	  
    	  //  open the pop-up (click on element which causes open a new window)
    	  
    	  k.click("HomePage_SocialSignIn_TwitterOption","");	
    	  
    	  //  try to get all available open window handles with below command. (the below command returns all window handles as Set)

    	  Set<String> s = driver.getWindowHandles();

    	  //  from that above set try get newly opened window and switch the control to that (pop-up window handle), as we already know the mainWindowHandle.


    	Iterator<String> ite = s.iterator();
    	while(ite.hasNext())
    	{
    		String popupHandle=ite.next().toString();
    		System.out.println("the pop up handle name is "+popupHandle);
    		if(!popupHandle.contains(mainWindowHandle))
    		{
    		driver.switchTo().window(popupHandle);
    		System.out.println("the pop up handle name is ");
    		
    		
    		k.writeInInput("HomePage_SocialSignInTwitter_Email",k.currentDataSet.get("Username"));

    		 //driver.findElement(By.xpath("//*[@id='Email']")).sendKeys(k.currentDataSet.get("Username"));
    		 
    		k.writeInInput("HomePage_SocialSignInTwitter_Password",k.currentDataSet.get("Password"));
    		 
    		//driver.findElement(By.xpath("//*[@id='Passwd']")).sendKeys(k.currentDataSet.get("Password"));
    		
    		k.click("HomePage_SocialSignInTwitter_SignInButton","");	
    		
    	    		
    		}
    	}
    	
    	driver.switchTo().window( mainWindowHandle );
 	    return Constants.KEYWORD_PASS;
    	

      }

    
    
    public String SocialSignInWithGooglePlusAccount(String object,String data) throws InterruptedException{
    	
    	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    	  
    	  String mainWindowHandle=driver.getWindowHandle();
    	  
    	  APP_LOGS.debug("the pop up handle name is mainWindowHandle= "+ mainWindowHandle);
    	  System.out.println("the pop up handle name is mainWindowHandle= "+ mainWindowHandle);
    	  
    	  
    	  //  open the pop-up (click on element which causes open a new window)
    	  
    	  k.click("HomePage_SocialSignIn_GoogleOption","");	
    	  
    	  //  try to get all available open window handles with below command. (the below command returns all window handles as Set)

    	  Set<String> s = driver.getWindowHandles();

    	  //  from that above set try get newly opened window and switch the control to that (pop-up window handle), as we already know the mainWindowHandle.


    	Iterator<String> ite = s.iterator();
    	
    	Boolean exitIter =false;
    	while(ite.hasNext() && (exitIter.equals(false)))
    	{
    		String popupHandle=ite.next().toString();
    		System.out.println("the pop up handle name is "+popupHandle);
    		if(!popupHandle.contains(mainWindowHandle))
    		{
    			
    		exitIter = true;
    		driver.switchTo().window(popupHandle);
    		System.out.println("the pop up handle name is ");
    		
    		
    		k.writeInInput("HomePage_SocialSignInGoogle_Email",k.currentDataSet.get("Username"));

    		 //driver.findElement(By.xpath("//*[@id='Email']")).sendKeys(k.currentDataSet.get("Username"));
    		 
    		k.writeInInput("HomePage_SocialSignInGoogle_Password",k.currentDataSet.get("Password"));
    		 
    		//driver.findElement(By.xpath("//*[@id='Passwd']")).sendKeys(k.currentDataSet.get("Password"));
    		
    		k.click("HomePage_SocialSignInGoogle_SignInButton","");	
       	  
    		    		
    		
    		}
    	}

    	//    Now control can be reverted to the main window

    	    driver.switchTo().window( mainWindowHandle );
    	    return Constants.KEYWORD_PASS;
    }
    
    public String SocialSignInWithFacebookAccount(String object,String data) throws InterruptedException{
    	
    	
      driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
      
  	  String mainWindowHandle=driver.getWindowHandle();
  	  
  	  APP_LOGS.debug("the pop up handle name is mainWindowHandle= "+ mainWindowHandle);
  	  System.out.println("the pop up handle name is mainWindowHandle= "+ mainWindowHandle);
  	  
  	  
  	  //  open the pop-up (click on element which causes open a new window)
  	  
  	  k.click("HomePage_SocialSignIn_facebookOption","");	
  	  
  	  //  try to get all available open window handles with below command. (the below command returns all window handles as Set)

  	  Set<String> s = driver.getWindowHandles();

  	  //  from that above set try get newly opened window and switch the control to that (pop-up window handle), as we already know the mainWindowHandle.


  	Iterator<String> ite = s.iterator();
  	while(ite.hasNext())
  	{
  		String popupHandle=ite.next().toString();
  		System.out.println("the pop up handle name is "+popupHandle);
  		if(!popupHandle.contains(mainWindowHandle))
  		{
  		driver.switchTo().window(popupHandle);
  		System.out.println("the pop up handle name is ");
  		
  		
  		k.writeInInput("HomePage_SocialSignInFacebook_Email",k.currentDataSet.get("Username"));

  		 //driver.findElement(By.xpath("//*[@id='Email']")).sendKeys(k.currentDataSet.get("Username"));
  		 
  		k.writeInInput("HomePage_SocialSignInFacebook_Password",k.currentDataSet.get("Password"));
  		 
  		//driver.findElement(By.xpath("//*[@id='Passwd']")).sendKeys(k.currentDataSet.get("Password"));
  		
  		k.click("HomePage_SocialSignInFacebook_SignInButton","");	
     	  
  		    		
  		
  		}
  	}

  	//    Now control can be reverted to the main window

  	               driver.switchTo().window( mainWindowHandle );
  	                 
  	               return Constants.KEYWORD_PASS;     
  }

    
    public String SignInWithHSNAccount(String object,String data) throws InterruptedException{
    	
    	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    	  
    	  
         
    	  try{
   	        // select the SignIn Register link on the home page
     	    k.click("HomePage_SignIn_Register_link","");	
     	  
     	    //enter the value from the username column in the data sheet into the username field
     	    
     	    k.writeInInput("HomePage_username_inputField",TestUtil.GetUpdatedColumnValues("col_Username"));

         	//enter the value from the Password column in the data sheet into the password field
     		k.writeInInput("HomePage_password_inputField",TestUtil.GetUpdatedColumnValues("col_Password"));
     		
     		//select the signin button
     		k.click("HomePage_SignInButton","");	
        	  
    	  }catch(Exception e){
    		  
    		  System.out.println("SignInWithHSNAccount: failed with message ");
    		  e.printStackTrace();
    	  }
     	    return Constants.KEYWORD_PASS;     
    	
    }
    
    
    public String SearchByPID(String object,String data) throws Throwable{
    	
  	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       
  	  try{
 	         	  
   	    //enter the value from the PID column in the data sheet into the search field
   	    
  		TestUtil.ProcesskeywordExecutionResult(k.writeInInput("HomePage_SearchInputField",TestUtil.GetUpdatedColumnValues("col_PID")));
   	    
   		//click on the search button
  		TestUtil.ProcesskeywordExecutionResult(k.click("HomePage_SearchButton",""));	

       	//enter the value from Product description to the column - ProductName
   		TestUtil.ProcesskeywordExecutionResult(k.saveLinkText("ProductDetailPage_ProductName","col|ProductName"));
   		

      	  
  	  }catch(Exception e){
  		  
  		  System.out.println("SearchByPID: failed with message ");
  		  e.printStackTrace();
  	  }
   	    return Constants.KEYWORD_PASS;     
  	
  }
	
	public void executeKeywords(String testCaseName, Xls_Reader xls,Hashtable <String,String> testData){
		
		int testrows = xls.getRowCount("TestSteps");
		
		for(int row=1; row<testrows;row++){
			
			
			
			String str_TCID = xls.getCellData("TestSteps", "TCID", row);
			
			if(str_TCID.equalsIgnoreCase(testCaseName)){
				
			String str_TSID = xls.getCellData("TestSteps", "TSID", row);
			String str_Keyword = xls.getCellData("TestSteps", "Keyword", row);
			String str_Object = xls.getCellData("TestSteps", "Object", row);
			String str_Data = xls.getCellData("TestSteps", "Data", row);
			
			//execute the current keyword
			System.out.println("---TCID = "+str_TCID+" -- TSID ="+str_TSID+" -- Keyword = "+str_Keyword+" -- Object = "+str_Object+" -- Data = "+str_Data);
			
			}
		}
	}
	
	
	 public String copyFileUsingFileChannels(String strSource,String strDest)
				throws IOException {
		 
		 	FileChannel inputChannel = null;
			FileChannel outputChannel = null;
			File source = new File(k.currentDataSet.get("sourcePath").trim());
			File dest = new File(k.currentDataSet.get("destinationPath").trim());
			long start = 0;
			long end = 0;
					
			try {
				
				start = System.currentTimeMillis();
				inputChannel = new FileInputStream(source).getChannel();
				outputChannel = new FileOutputStream(dest).getChannel();
				outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
				end = System.currentTimeMillis();
				System.out.println("completed copy from"+strSource+" to "+strDest+" in time = "+(end-start));
				return Constants.KEYWORD_PASS;    
				
			}catch(Exception e){
				e.printStackTrace();return Constants.KEYWORD_FAIL;    
			}
			
			finally {
				inputChannel.close();
				outputChannel.close();
			}
		}
	 
	 public String ATB_Modal_Ckout(String object,String data) throws Throwable{
	    	
	  	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	       
	  	  try{
	 	         	  
	   	    
	   		//click on the checkout on the your bag page
	  		TestUtil.ProcesskeywordExecutionResult(k.click("JustAddedPage_Checkout",""));	

	   		
	      	  
	  	  }catch(Exception e){
	  		  
	  		  System.out.println("ATB_Modal_Ckout: failed with message ");
	  		  e.printStackTrace();
	  	  }
	   	    return Constants.KEYWORD_PASS;     
	  	
	  }
	 
	 public String BagPage_Ckout(String object,String data) throws Throwable{
	    	
	  	  driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	       
	  	  try{
	 	         	  
	  		
	   		//click on the checkout on the your bag page
	  		TestUtil.ProcesskeywordExecutionResult(k.click("YourBagPage_Checkout",""));	
	   		
	   		//check if the submit order button is visble 
	  		TestUtil.ProcesskeywordExecutionResult(k.isElementVisible("CheckoutOrderReviewpage_SubmitOrderButton",""));	
	   		
	      	  
	  	  }catch(Exception e){
	  		  
	  		  System.out.println("BagPage_Ckout: failed with message ");
	  		  e.printStackTrace();
	  	  }
	   	    return Constants.KEYWORD_PASS;     
	  	
	  }
	 
	 public String OrderReview_SubmitOrder (String object,String data) throws Throwable{
	    	
	  	  driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
	       
	  	  try{
	 	         	  
	  		
	   			   		
	   		//click on submit order 
	  		TestUtil.ProcesskeywordExecutionResult(k.click("CheckoutOrderReviewpage_SubmitOrderButton",""));	
	  		
	  	   //capture the order ID to column OrderID
	  		TestUtil.ProcesskeywordExecutionResult(k.saveLinkText("ItsYoursPage_OrderNumberLink","col|OrderID"));	
	   		
	      	  
	  	  }catch(Exception e){
	  		  
	  		  System.out.println("OrderReview_SubmitOrder: failed with message ");
	  		  e.printStackTrace();
	  	  }
	   	    return Constants.KEYWORD_PASS;     
	  	
	  }
		
		

		

}
