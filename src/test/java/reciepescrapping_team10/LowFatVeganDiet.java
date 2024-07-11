package reciepescrapping_team10;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LowFatVeganDiet {
	
		
		 private static WebDriver driver;

		 public static void launchDriver()
		 {
		//WebDriverManager.firefoxdriver().setup();
		  //driver = new FirefoxDriver();
	     WebDriverManager.chromedriver().setup();
	     driver = new ChromeDriver();
		  driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx");
	     }
		 
		 public static void parseDataOnPage() throws IOException {

			  // list of WebElements that store all the links
			  List<WebElement> raw_recipes = driver.findElements(By.className("rcc_recipename"));
	           System.out.println(raw_recipes.size());
			  // arraylist to store all the links in string form (can be optimized)
			  ArrayList<String> links = new ArrayList<>(14);
			  System.out.println(links);

			  // loop through raw_recipes to fill the links arraylist
			  for (WebElement e : raw_recipes)
			  {
			   // .findElement -----> finds the tag <a> inside the current WebElement
			   // .getAttribute ----> returns the href attribute of the <a> tag in the current WebElement
			   links.add(e.findElement(By.tagName("a")).getAttribute("href"));
			   System.out.println(links);
			  }
		 
			  List<WebElement> raw_ids = driver.findElements((By.className("rcc_rcpno")));
	          System.out.println(raw_ids);
	          
		 }
		 public static void main(String[] args) throws IOException {
			launchDriver();
			parseDataOnPage();
		}
	}

