package reciepescrapping_team10_LCHF;

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

public class LCHFAdd {

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

		List<String> additem = Arrays.asList(new String[] {"Fish", "prawn", "poultry","egg","Onion","Garlic","turmeric","Ginger","Butter","ghee", "hard cheese" , "paneer" ,"cottage cheese" ,"sour cream", "greek yogurt", "hung curd", "almond" ,"pistachio" , "brazil nut" , "walnut" , "pine nut" , " hazelnut ","macadamia nut", "pecan" , " hemp seed " , "sunflower seed" ,"sesame seed ","flax seed", "Blueberry" , "blackberry" ,"strawberry"
 });

		driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();

		Thread.sleep(2000);
		int rowCounter = 1;
		// run in a loop for all recipe in a page
		List<String> pageBeginsWithList = Arrays.asList(new String[] { "0-9", "A" });

		for (int k = 0; k < pageBeginsWithList.size(); k++) {
			driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=" + pageBeginsWithList.get(k));
			int lastPage = 0;
			try {
				lastPage = Integer
						.parseInt(driver.findElement(By.xpath("//div/a[@class= 'respglink'][last()]")).getText());
			} catch (Exception e) {

			}
			if (0 != lastPage) {
				for (int j = 1; j <= 2; j++) {
					int pageindex = j;
					driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="
							+ pageBeginsWithList.get(k) + "&pageindex=" + j);
					List<WebElement> recipeCardElements = driver
							.findElements(By.xpath("//div[@class='rcc_recipecard']"));
					List<String> recipeUrls = new ArrayList<>();
					Map<String, String> recipeIdUrls = new HashMap<>();

					// Looping through all recipes Web elements and generating a navigation URL
					recipeCardElements.stream().forEach(recipeCardElement -> {
						recipeUrls.add("https://www.tarladalal.com/" + recipeCardElement
								.findElement(By.xpath("//span[@class='rcc_recipename']/a")).getDomAttribute("href"));

						recipeIdUrls.put(recipeCardElement.getDomAttribute("id").replace("rcp", ""),
								"https://www.tarladalal.com/"
										+ recipeCardElement.findElement(By.tagName("a")).getDomAttribute("href"));
					});

					for (Map.Entry<String, String> recipeIdUrlEntry : recipeIdUrls.entrySet()) {
						String recipeUrl = recipeIdUrlEntry.getValue();
						String recipeId = recipeIdUrlEntry.getKey();
						driver.navigate().to(recipeUrl);
						driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

						try {
							try {
								if (isAdd(additem)) 
									
//								{
//									// driver.navigate().to("//div/a[text()= 'Recipe A To Z']");
//								} else
								{
									ExcelWriter writeOutput = new ExcelWriter();
									// Recipe id
									try {
										writeOutput.setCellData("LCHFAddExcel", rowCounter, 0, recipeId);
									} catch (Exception e) {

									}

									// Recipe Name
									try {
										WebElement recipeTitle = driver.findElement(
												By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
										System.out.print(recipeTitle.getText());
										writeOutput.setCellData("LCHFAddExcel", rowCounter, 1,
												recipeTitle.getText());

									} catch (Exception e) {

									}
									try {
									WebElement recipeCategory = driver.findElement(By.xpath(
												"//span[@itemprop= 'description']/*[contains((text(),'lunch')]"));
                                       //  driver.findElement(null) 
										System.out.print(recipeCategory.getText());
										writeOutput.setCellData("LCHFAddExcel", rowCounter, 2,
												recipeCategory.getText());

									} catch (Exception e) {

									}
									try {
										WebElement foodCategory = driver
												.findElement(By.xpath("//a/span[text()= 'No Cooking Veg Indian']"));
										System.out.print(foodCategory.getText());
										writeOutput.setCellData("LCHFAddExcel", rowCounter, 3,
												foodCategory.getText());

									} catch (Exception e) {

									}

									try {
										WebElement nameOfIngredients = driver
												.findElement(By.xpath("//div[@id= 'rcpinglist']"));
										System.out.print(nameOfIngredients.getText());
										writeOutput.setCellData("LCHFAddExcel", rowCounter, 4,
												nameOfIngredients.getText());

									} catch (Exception e) {

									}

									try {
										WebElement preparationTime = driver
												.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
										System.out.print(preparationTime.getText());
										writeOutput.setCellData("LCHFAddExcel", rowCounter, 5,
												preparationTime.getText());

									} catch (Exception e) {

									}

									try {
										WebElement cookTime = driver
												.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
										System.out.print(cookTime.getText());
										writeOutput.setCellData("LCHFAddExcel", rowCounter, 6, cookTime.getText());

									} catch (Exception e) {

									}

									try {
										WebElement prepMethod = driver.findElement(
												By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
										System.out.print(prepMethod.getText());
										writeOutput.setCellData("LCHFAddExcel", rowCounter, 7, prepMethod.getText());

									} catch (Exception e) {

									}
									try {
										WebElement nutrients = driver
												.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
										System.out.print(nutrients.getText());
										writeOutput.setCellData("LCHFAddExcel", rowCounter, 8, nutrients.getText());

									} catch (Exception e) {

									}
									try {
										System.out.print(recipeUrl);
										writeOutput.setCellData("LCHFAddExcel", rowCounter, 9, recipeUrl);
									} catch (Exception e) {

									}

									rowCounter++;

								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}

			}
		}
	}

	private static boolean isAdd(List<String> additem) {
		
	//		 AtomicBoolean isAddPresent = new AtomicBoolean(false);
			 AtomicBoolean isAddPresent = new AtomicBoolean(false);
			 additem.parallelStream().forEach(addItem -> {
			        try {
			            WebElement ingredientWebElement = driver.findElement(By.xpath("//div[@id='rcpinglist']"));
			            String ingredients = ingredientWebElement.getText();
			            if (ingredients.toLowerCase().contains(addItem.toLowerCase())) {
			                isAddPresent.set(true);
			            }
			        } catch (Exception e) {
			            System.out.print("No Such Element: " + e.getLocalizedMessage());
			        }
			        try {
			            WebElement methodWebElement = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
			            String method = methodWebElement.getText();
			            if (method.toLowerCase().contains(addItem.toLowerCase())) {
			                isAddPresent.set(true);
			            }
			        } catch (Exception e) {
			            System.out.print("No Such Element: " + e.getLocalizedMessage());
			        }
			    });
			    return isAddPresent.get();
			}


	public static void main(String[] args) throws InterruptedException {

		setUpDriver();
		extractRecipe();

	}

}
