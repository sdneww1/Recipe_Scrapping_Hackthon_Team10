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




	public class LFVDAdd {
		
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
			
			List<String> add = Arrays.asList(new String[] {
					"Lettuce","kale","chard","arugula","spinach","cabbage","pumpkin","sweet potatoes",
					"purple potatoes","yams","turnip","parsnip","karela","bittergourd","beet",
					"carrot","cucumber","red onion","white onion","broccoli","cauliflower","carrot","celery",
					"artichoke","bell pepper","mushroom","tomato","sweet and hot pepper",
					"banana","mango","papaya","plantain","apple","orange","pineapple","pear",
					"tangerine","all berry varieties","all melon varieties","peach","plum","nectarine",
					"Avocado","Amaranth","Rajgira","Ramdana Barnyard","Sanwa","Samvat ke chawal buckwheat","kuttu finger millet",
					"Ragi","Nachni foxtail millet","kangni","kakum kodu","kodon","little millet","moraiyo",
					"kutki","shavan","sama pearl millet","bajra","broom corn millet","chena sorghum","jowar",	"Lentil",
					"Pulse","Moong dhal","masoor dhal","toor dhal","urd dhal","lobia","rajma","matar",
					"all forms of chana","almond","cashew","pistachio","brazil nut","walnut","pine nut",
					"hazelnut","macadamia nut","pecan","peanut","hemp seed","sun flower seed","sesame seed","chia seed",
					"flax seed"});
			
			        driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();
			        
			        Thread.sleep(2000);
					int rowCounter = 1;
					// run in a loop for all recipe in a page
					List<String> pageBeginsWithList = Arrays.asList(new String[] { "0-9", "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P",
							"Q","R","S","T","U","V","W","X","Y","Z"});

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
											if ((isAdded(add))) 
											{
												ExcelWriter writeOutput = new ExcelWriter();
												// Recipe id
												try {
													writeOutput.setCellData("LFVAdd", rowCounter, 0, recipeId);
												} catch (Exception e) {

												}

												// Recipe Name
												try {
													WebElement recipeTitle = driver
															.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
													System.out.print(recipeTitle.getText());
													writeOutput.setCellData("LFVAdd", rowCounter, 1, recipeTitle.getText());

												} catch (Exception e) {

												}
												try {
												    WebElement recipeCategory = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
												    String recipeCategoryText = recipeCategory.getText().toLowerCase();

												    System.out.print("Recipe Category: " + recipeCategory.getText());

												    if (recipeCategoryText.equalsIgnoreCase("breakfast") || recipeCategoryText.contains("breakfast")) {
												        writeOutput.setCellData("LFVDAdd", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
												    } else if (recipeCategoryText.equalsIgnoreCase("lunch") || recipeCategoryText.contains("lunch")) {
												        writeOutput.setCellData("LFVDAdd", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
												    } else if (recipeCategoryText.equalsIgnoreCase("snack") || recipeCategoryText.contains("snack")) {
												        writeOutput.setCellData("LFVDAdd", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
												    } else if (recipeCategoryText.equalsIgnoreCase("dinner") || recipeCategoryText.contains("dinner")) {
												        writeOutput.setCellData("LFVDAdd", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
												    }

												} catch (Exception e) {
												    e.printStackTrace();
												}
												
//											
												try {
												   // WebElement foodCategory = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
													 WebElement foodCategory = driver.findElement(By.xpath("//a[@itemprop='recipeCategory']"));
													
												    
												    String foodCategoryText = foodCategory.getText().toLowerCase();

												    System.out.print("Food Category: " + foodCategory.getText());

												    if (foodCategoryText.equalsIgnoreCase("Vegan") || foodCategoryText.contains("Vegan")) {
												        writeOutput.setCellData("LFVDAdd", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
												    } else if (foodCategoryText.equalsIgnoreCase("Vegeterian") || foodCategoryText.contains("Vegeterian")) {
												        writeOutput.setCellData("LFVDAdd", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
												    } else if (foodCategoryText.equalsIgnoreCase("Jain") || foodCategoryText.contains("Jain")) {
												        writeOutput.setCellData("LFVDAdd", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
												    } else if (foodCategoryText.equalsIgnoreCase("Eggitarian") || foodCategoryText.contains("Eggitarian")) {
												        writeOutput.setCellData("LFVDAdd", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
												    }
												    else if (foodCategoryText.equalsIgnoreCase("Non-veg") || foodCategoryText.contains("Non-veg")) {
												        writeOutput.setCellData("LFVDAdd", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
												    }	
												} catch (Exception e) {
												    e.printStackTrace();
												}

												try {
													WebElement nameOfIngredients = driver
															.findElement(By.xpath("//div[@id= 'rcpinglist']"));
													System.out.print(nameOfIngredients.getText());
													writeOutput.setCellData("LFVDAdd", rowCounter, 4,
															nameOfIngredients.getText());

												} catch (Exception e) {

												}

												try {
													WebElement preparationTime = driver
															.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
													System.out.print(preparationTime.getText());
													writeOutput.setCellData("LFVDAdd", rowCounter, 5,
															preparationTime.getText());

												} catch (Exception e) {

												}

												try {
													WebElement cookTime = driver
															.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
													System.out.print(cookTime.getText());
													writeOutput.setCellData("LFVDAdd", rowCounter, 6, cookTime.getText());

												} catch (Exception e) {

												}

												try {
													WebElement tags = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
													System.out.print(tags.getText());
													writeOutput.setCellData("LFVDAdd", rowCounter, 7, tags.getText());

												} catch (Exception e) {

												}
												
												
												try {
													WebElement No_of_servings = driver.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblServes']"));
													System.out.print(No_of_servings.getText());
													writeOutput.setCellData("LFVDAdd", rowCounter, 8, No_of_servings.getText());

												} catch (Exception e) {

												}
												
												
												
												
												
												try {
													WebElement cuisineCategory = driver.findElement(By.xpath("//a[@itemprop='recipeCuisine' ]"));
													System.out.print(cuisineCategory.getText());
													writeOutput.setCellData("LFVDAdd", rowCounter, 9, cuisineCategory.getText());

												} catch (Exception e) {

												}
												
												
												
												try {
													WebElement recipeDescription = driver.findElement(By.xpath("//p[@id='recipe_description']"));
													System.out.print(recipeDescription.getText());
													writeOutput.setCellData("LFVDAdd", rowCounter, 10, recipeDescription.getText());

												} catch (Exception e) {

												}
												
												

												try {
													WebElement prepMethod = driver.findElement(
															By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
													System.out.print(prepMethod.getText());
													writeOutput.setCellData("LFVDAdd", rowCounter, 11,
															prepMethod.getText());

												} catch (Exception e) {

												}
												try {
													WebElement nutrients = driver
															.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
													System.out.print(nutrients.getText());
													writeOutput.setCellData("LFVDAdd", rowCounter, 12,
															nutrients.getText());

												} catch (Exception e) {

												}
												try {
													System.out.print(recipeUrl);
													writeOutput.setCellData("LFVDAdd", rowCounter, 13, recipeUrl);
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

				private static boolean isAdded(List<String> add) {
					 AtomicBoolean isAddPresent = new AtomicBoolean(false);
					    add.parallelStream().forEach(addItem -> {
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


	public static void main(String[] args) throws InterruptedException  {
		
		setUpDriver();
		extractRecipe();
			
	}	

	}
	
	

