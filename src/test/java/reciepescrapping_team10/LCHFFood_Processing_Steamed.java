package reciepescrapping_team10;
import java.time.Duration;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import reciepescrapping_team10_utility.ExcelWriter;



public class LCHFFood_Processing_Steamed {
	
	public static WebDriver driver;

	@BeforeTest
	public static void setUpDriver() {
		driver = new ChromeDriver();
		driver.get("https://www.tarladalal.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@AfterTest
	public static void tearDown() {
		driver.close();
	}

	public static void extractRecipe() throws InterruptedException {
		
		List<String> add = Arrays.asList(new String[] {"Steam","Steamed Snacks" });
		
		driver.get("https://www.tarladalal.com/recipes-for-cooking-basics-271");
		Thread.sleep(2000);
		int rowCounter = 1;

		for (int i = 0; i <= 1; i++) {
			driver.navigate().to("https://www.tarladalal.com/recipes-for-cooking-basics-271?pageindex=" + i);
			driver.findElement(By.xpath("//p/a[contains(text(),' steaming')]")).click();
			List<WebElement> recipeCardElements = driver.findElements(By.xpath("//article[@class='rcc_recipecard']"));
			List<String> recipeUrls = new ArrayList<>();
			Map<String, String> recipeIdUrls = new HashMap<>();

			recipeCardElements.stream().forEach(recipeCardElement -> {
				WebElement recipeLinkElement = recipeCardElement.findElement(By.xpath(".//span[@class='rcc_recipename']/a"));
				String recipeUrl = recipeLinkElement.getAttribute("href");
				String recipeId = recipeCardElement.getAttribute("id").replace("rcp", "");
				recipeUrls.add(recipeUrl);
				recipeIdUrls.put(recipeId, recipeUrl);
			});
			
        for (Map.Entry<String, String> recipeIdUrlEntry : recipeIdUrls.entrySet()) {
				String recipeUrl = recipeIdUrlEntry.getValue();
				String recipeId = recipeIdUrlEntry.getKey();
				driver.navigate().to(recipeUrl);
				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

				try {
					if ((isAdded(add))) {
						ExcelWriter writeOutput = new ExcelWriter();
						
						// Debugging - Print current recipe URL and ID
					//	System.out.println("Processing recipe: " + recipeUrl);
					//	System.out.println("Recipe ID: " + recipeId);
						
						// Recipe id
						try {
							writeOutput.setCellData("LCHFFood_Processing", rowCounter, 0, recipeId);
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Recipe Name
						try {
							WebElement recipeTitle = driver.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']"));
							System.out.print("Recipe Name: " + recipeTitle.getText());
							writeOutput.setCellData("LCHFFood_Processing", rowCounter, 1, recipeTitle.getText());
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Recipe Category
						try {
							WebElement recipeCategory = driver.findElement(By.xpath(
								"//span[@itemprop='description']/*[contains(text(), 'breakfast') or contains(text(), 'lunch') or contains(text(), 'dinner')]"));
							System.out.print("Recipe Category: " + recipeCategory.getText());
							writeOutput.setCellData("LCHFFood_Processing", rowCounter, 2, recipeCategory.getText());
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Food Category
						try {
							WebElement foodCategory = driver.findElement(By.xpath("//a/span[text()='No Cooking Veg Indian']"));
							System.out.print("Food Category: " + foodCategory.getText());
							writeOutput.setCellData("LCHFFood_Processing", rowCounter, 3, foodCategory.getText());
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Ingredients
						try {
							WebElement nameOfIngredients = driver.findElement(By.xpath("//div[@id='rcpinglist']"));
							System.out.print("Ingredients: " + nameOfIngredients.getText());
							writeOutput.setCellData("LCHFFood_Processing", rowCounter, 4, nameOfIngredients.getText());
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Preparation Time
						try {
							WebElement preparationTime = driver.findElement(By.xpath("//p/time[@itemprop='prepTime']"));
							System.out.print("Preparation Time: " + preparationTime.getText());
							writeOutput.setCellData("LCHFFood_Processing", rowCounter, 5, preparationTime.getText());
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Cook Time
						try {
							WebElement cookTime = driver.findElement(By.xpath("//p/time[@itemprop='cookTime']"));
							System.out.print("Cook Time: " + cookTime.getText());
							writeOutput.setCellData("LCHFFood_Processing", rowCounter, 6, cookTime.getText());
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Preparation Method
						try {
							WebElement prepMethod = driver.findElement(By.xpath("//div[@id='ctl00_cntrightpanel_pnlRcpMethod']"));
							System.out.print("Preparation Method: " + prepMethod.getText());
							writeOutput.setCellData("LCHFFood_Processing", rowCounter, 7, prepMethod.getText());
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Nutrients
						try {
							WebElement nutrients = driver.findElement(By.xpath("//table[@id='rcpnutrients']"));
							System.out.print("Nutrients: " + nutrients.getText());
							writeOutput.setCellData("LCHFFood_Processing", rowCounter, 8, nutrients.getText());
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Recipe URL
						try {
							System.out.print("Recipe URL: " + recipeUrl);
							writeOutput.setCellData("LCHFFood_Processing", rowCounter, 9, recipeUrl);
						} catch (Exception e) {
							e.printStackTrace();
						}

						rowCounter++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static boolean isAdded(List<String> add) {
		AtomicBoolean isTagPresent = new AtomicBoolean(false);
		add.parallelStream().forEach(addTag -> {
			try {
				WebElement tagWebElement = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
				String Tags = tagWebElement.getText();
				if (Tags.toLowerCase().contains(addTag.toLowerCase())) {
					isTagPresent.set(true);
				}
			} catch (Exception e) {
				System.out.print("No Such Element: " + e.getLocalizedMessage());
			}

			try {
				WebElement methodWebElement = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
				String method = methodWebElement.getText();
				if (method.toLowerCase().contains(addTag.toLowerCase())) {
					isTagPresent.set(true);
				}
			} catch (Exception e) {
				System.out.print("No Such Element: " + e.getLocalizedMessage());
			}
		});
		return isTagPresent.get();
	}


	public static void main(String[] args) throws InterruptedException {
		setUpDriver();
		extractRecipe();
		tearDown();
	}	
}