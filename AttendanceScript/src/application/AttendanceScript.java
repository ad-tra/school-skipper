package application;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AttendanceScript implements Runnable {

private  String password;
private  String browser;
private  static WebDriver driver;
private  String email;
private  String name;
private  int  grade;
private  String userSchoolName;
private  String lastname;
private static String url;
public static Properties prop;
public static  File file;
public static  FileInputStream fileInput;




public static void main(String[] args) throws Exception  
{

	runTest();
}


public static void runTest() throws Exception
{
	AttendanceScript script = new AttendanceScript();
	
	script.getUserInput();
	System.out.println("-----------Your Info Has Been Entered--------------");
	script.setBrowserConfig();
	System.out.println("------------Browser Setup Completed----------------");
	driver.get(url);
	script.login();
	System.out.println("------------Logged in Successfully!----------------");
	script.fillNormalElements();
	System.out.println("-------------Filling Out The Form...---------------");
	script.fillSchoolName();
	script.fillGrade();
	System.out.println("______Congrats You have been marked Present!_______");
	driver.close();					
}
/**
 * instantiates basic info required to run the script including
 * the browser,password, and email address
 */

private static void configUserInput()
{
	file = new File(System.getProperty("user.dir") + "\\config.properties");
	  
	fileInput = null;
	try {
		fileInput = new FileInputStream(file);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	prop = new Properties();
	
	//load properties file
	try {
		prop.load(fileInput);
	} catch (IOException e) {
		e.printStackTrace();
	} 
}

public boolean isUserInputEmpty()
{
	configUserInput();
	//later change the get property  to grade or school  
	//update later
	if(prop.getProperty("grade").compareTo("") == 0)
		{
		return true;
		}
	return false; 
	
}

public void getUserInput() {
		configUserInput();
		browser= prop.getProperty("browser");
		email = prop.getProperty("email");
		password = prop.getProperty("password");
		url = prop.getProperty("url");
		name = prop.getProperty("name");
		lastname = prop.getProperty("lastname");
		userSchoolName= prop.getProperty("school");
		grade = Integer.parseInt(prop.getProperty("grade"));

		
}

public static void setUserInput(String name, String lastname, String email, String password,String school, String grade, String browser  ) throws IOException {
		configUserInput();
		prop.setProperty("name", name);
		prop.setProperty("lastname", lastname);
		prop.setProperty("email", email);
		prop.setProperty("password", password);
		prop.setProperty("school", school);
		prop.setProperty("grade", grade);
		prop.setProperty("browser", browser);
		FileWriter writer = new FileWriter(file);
		prop.store(writer, "host settings");
}

/**
 * configures the browser setup. 
 * Current options are Firefox and Chrome
 */
private void setBrowserConfig()
{
	if(browser.compareToIgnoreCase("firefox") ==0)
	{
		System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "\\webdrivers\\geckodriver.exe");
		 driver = new FirefoxDriver();
	}
	
	if(browser.compareToIgnoreCase("chrome") ==0)
	{
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\webdrivers\\chromedriver.exe");
		 driver = new ChromeDriver();

	}
}


/**
 * login into gmail using  the user specified email and password 
 */
private void login() throws Exception
{
	driver.findElement(By.id("identifierId")).sendKeys(email);
	driver.findElement(By.id("identifierNext")).click();
	
	WebDriverWait wait = new WebDriverWait(driver, 15);
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwordNext")));
	
	driver.findElement(By.name("password")).sendKeys(password);
	Thread.sleep(500);
	driver.findElement(By.id("passwordNext")).click();


	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='mG61Hd']/div/div/div[2]/div/div/div[2]/div/span/div/div/label/div/div/div/div[3]/div")));
}


/**
 * fills out the first three question of the form which include the y/n, name, and last name 
 */
private void fillNormalElements() 
{

	//fills the y/n field
	driver.findElement(By.xpath("//form[@id='mG61Hd']/div/div/div[2]/div/div/div[2]/div/span/div/div/label/div/div/div/div[3]/div")).click();
	
	//fills the name field
	driver.findElement(By.xpath("//form[@id='mG61Hd']/div/div/div[2]/div[2]/div/div[2]/div/div/div/div/input")).sendKeys(name);
	//fills the last name field
	driver.findElement(By.xpath("//form[@id='mG61Hd']/div/div/div[2]/div[3]/div/div[2]/div/div/div/div/input")).sendKeys(lastname);	
}


/**
 * fills out two drop downs concerning the school name 
 */
private void fillSchoolName() throws Exception
{
	
	
	String schoolNames[] = {"adamsville","bradley gardens","crim","hamilton","jfk","milltown","van holten","Eisenhower","hillside","middle school","high school"};
	
	int index =0;
	for(int i = 0; i <schoolNames.length; i++)
	{
		if(userSchoolName.compareToIgnoreCase(schoolNames[i])==0)
		{
			index = i +3;
			i=schoolNames.length;
		}
	}
	
	driver.findElement(By.xpath("//form[@id='mG61Hd']/div/div/div[2]/div[4]/div/div[2]/div/div/div")).click();
	Thread.sleep(500);	
	driver.findElement(By.xpath("//form[@id='mG61Hd']/div/div/div[2]/div[4]/div/div[2]/div[2]/div[" + index +"]/span")).click();
	
	
	
}


/**
 * fills out two drop downs concerning the grade
 */
private void fillGrade() throws Exception
{
	//Grade drop out
	driver.findElement(By.xpath("//form[@id='mG61Hd']/div/div/div[2]/div[5]/div/div[2]/div/div[2]")).click();
	Thread.sleep(500);
	driver.findElement(By.xpath("//form[@id='mG61Hd']/div/div/div[2]/div[5]/div/div[2]/div[2]/div["+ grade +"]")).click();
	Thread.sleep(500);
	
	//final submit
	//driver.findElement(By.xpath("//form[@id='mG61Hd']/div/div/div[3]/div/div/div/span/span")).click();
}


@Override

public void run() {
	
	try {
		runTest();
	} catch (Exception e) {
		e.printStackTrace();
	}
}




 

}
