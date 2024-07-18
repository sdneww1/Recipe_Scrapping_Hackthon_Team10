	package reciepescrapping_team10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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


public class LFVRecipes_to_avoid {

	
	private static Connection connection;
	public static WebDriver driver;

	@BeforeTest
	public static void setUpDriver() {
		driver = new ChromeDriver();
		driver.get("https://www.tarladalal.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	
	
	
	@BeforeTest
	public static void setUpDatabase() {
		try {
			String url = "jdbc:postgresql://localhost:5432/postgres";
			String user = "postgres";
			String password = "Balajiswamy@12";
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("connection  "+connection );
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@AfterTest
	public static void tearDown() {
		driver.close();

	}

	@SuppressWarnings("deprecation")
	public static void extractRecipe() throws InterruptedException {

		
		List<String> LFVRecipes_To_Avoid = Arrays
				.asList(new String[] { "Deep-fried ", "microwave", "Chips", "crackers" });

		driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();

		Thread.sleep(2000);
		int rowCounter = 1;
		// run in a loop for all recipe in a page
		List<String> pageBeginsWithList = Arrays.asList(new String[] { "0-9", "V"});

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
						InputVO dbInputVO = new InputVO();
						String recipeUrl = recipeIdUrlEntry.getValue();
						dbInputVO.setRecipeUrl(recipeUrl);
						String recipeId = recipeIdUrlEntry.getKey();
						dbInputVO.setRecipeId(recipeId);
						driver.navigate().to(recipeUrl);
						driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

						try {
							try {
								if ((toAvoid(LFVRecipes_To_Avoid))) {
									
									
										// driver.navigate().to("//div/a[text()= 'Recipe A To Z']");
									} else {
									ExcelWriter writeOutput = new ExcelWriter();
									// Recipe id
									try {
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 0, recipeId);
									} catch (Exception e) {

									}

									// Recipe Name
									try {
										WebElement recipeTitle = driver.findElement(
												By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
										System.out.print(recipeTitle.getText());
										dbInputVO.setRecipeName(recipeTitle.getText());
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 1,
												recipeTitle.getText());

									} catch (Exception e) {

									}
							
									
									try {
									    WebElement recipeCategory = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
									    String recipeCategoryText = recipeCategory.getText().toLowerCase();

									    System.out.print("Recipe Category: " + recipeCategory.getText());

									    if (recipeCategoryText.equalsIgnoreCase("breakfast") || recipeCategoryText.contains("breakfast")) {
									        writeOutput.setCellData("LCHFFood_Processing", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
									        dbInputVO.setRecipeCategory(recipeCategoryText.replace("tags", ""));
									    } 
									    else if (recipeCategoryText.equalsIgnoreCase("lunch") || recipeCategoryText.contains("lunch")) {
									        writeOutput.setCellData("LCHFFood_Processing", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
									        dbInputVO.setRecipeCategory(recipeCategoryText.replace("tags", ""));
									    } else if (recipeCategoryText.equalsIgnoreCase("snack") || recipeCategoryText.contains("snack")) {
									        writeOutput.setCellData("LCHFFood_Processing", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
									        dbInputVO.setRecipeCategory(recipeCategoryText.replace("tags", ""));
									    } else if (recipeCategoryText.equalsIgnoreCase("dinner") || recipeCategoryText.contains("dinner")) {
									        writeOutput.setCellData("LCHFFood_Processing", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
									        dbInputVO.setRecipeCategory(recipeCategoryText.replace("tags", ""));
									    }
									    

									} catch (Exception e) {
									    e.printStackTrace();
									}
									
			
									
									try {
									   // WebElement foodCategory = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
										 WebElement foodCategory = driver.findElement(By.xpath("//a[@itemprop='recipeCategory']"));
										
									    
									    String foodCategoryText = foodCategory.getText().toLowerCase();

									    System.out.print("Food Category: " + foodCategory.getText());

									    if (foodCategoryText.equalsIgnoreCase("Vegan") || foodCategoryText.contains("Vegan")) {
									        writeOutput.setCellData("LCHFFood_Processing", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
									        dbInputVO.setFoodCategory(foodCategoryText.replace("Tags", ""));
									    } else if (foodCategoryText.equalsIgnoreCase("Vegeterian") || foodCategoryText.contains("Vegeterian")) {
									        writeOutput.setCellData("LCHFFood_Processing", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
									        dbInputVO.setFoodCategory(foodCategoryText.replace("Tags", ""));
									    } else if (foodCategoryText.equalsIgnoreCase("Jain") || foodCategoryText.contains("Jain")) {
									        writeOutput.setCellData("LCHFFood_Processing", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
									        dbInputVO.setFoodCategory(foodCategoryText.replace("Tags", ""));
									    } else if (foodCategoryText.equalsIgnoreCase("Eggitarian") || foodCategoryText.contains("Eggitarian")) {
									        writeOutput.setCellData("LCHFFood_Processing", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
									        dbInputVO.setFoodCategory(foodCategoryText.replace("Tags", ""));
									    }
									    else if (foodCategoryText.equalsIgnoreCase("Non-veg") || foodCategoryText.contains("Non-veg")) {
									        writeOutput.setCellData("LCHFFood_Processing", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
									        dbInputVO.setFoodCategory(foodCategoryText.replace("Tags", ""));
									    }	
									} catch (Exception e) {
									    e.printStackTrace();
									}

									try {
										WebElement nameOfIngredients = driver
												.findElement(By.xpath("//div[@id= 'rcpinglist']"));
										System.out.print(nameOfIngredients.getText());
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 4,
												nameOfIngredients.getText());
										dbInputVO.setNameOfIngredients(nameOfIngredients.getText());
										

									} catch (Exception e) {

									}

									try {
										WebElement preparationTime = driver
												.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
										System.out.print(preparationTime.getText());
										
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 5,
												preparationTime.getText());
										dbInputVO.setPreparationTime(preparationTime.getText());

									} catch (Exception e) {

									}

									try {
										WebElement cookTime = driver
												.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
										System.out.print(cookTime.getText());
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 6, cookTime.getText());
										dbInputVO.setCookTime(cookTime.getText());

									} catch (Exception e) {

									}

									try {
										WebElement tags = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
										System.out.print(tags.getText());
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 7, tags.getText());
										dbInputVO.setTags(tags.getText().replace("Tags", ""));

									} catch (Exception e) {

									}
									
									
									try {
										WebElement No_of_servings = driver.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblServes']"));
										System.out.print(No_of_servings.getText());
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 8, No_of_servings.getText());
										dbInputVO.setNo_of_servings(No_of_servings.getText());

									} catch (Exception e) {

									}
									
									
									
									
									
									try {
										WebElement cuisineCategory = driver.findElement(By.xpath("//a[@itemprop='recipeCuisine' ]"));
										System.out.print(cuisineCategory.getText());
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 9, cuisineCategory.getText());
										dbInputVO.setCuisineCategory(cuisineCategory.getText());
										

									} catch (Exception e) {

									}
									
									
									
									try {
										WebElement recipeDescription = driver.findElement(By.xpath("//p[@id='recipe_description']"));
										System.out.print(recipeDescription.getText());
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 10, recipeDescription.getText());
										dbInputVO.setRecipeDescription(recipeDescription.getText());
										

									} catch (Exception e) {

									}
									
									

									try {
										WebElement prepMethod = driver.findElement(
												By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
										System.out.print(prepMethod.getText());
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 11,
												prepMethod.getText());
										dbInputVO.setPrepMethod(prepMethod.getText());

									} catch (Exception e) {

									}
									try {
										WebElement nutrients = driver
												.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
										System.out.print(nutrients.getText());
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 12,
												nutrients.getText());
										dbInputVO.setNutrients(nutrients.getText());

									} catch (Exception e) {

									}
									try {
										System.out.print(recipeUrl);
										writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 13, recipeUrl);
									} catch (Exception e) {

									}
									System.out.println("dbInputVO "+dbInputVO.toString());
									saveRecipeToDatabase(dbInputVO);
									rowCounter++;

								}
							} catch (Exception e) {
								
								e.printStackTrace();
							}
						} catch (Exception e) {
							
							e.printStackTrace();
						}

					}
				}

			}
		}
	}

	

	private static boolean toAvoid(List<String> avoid) {
		AtomicBoolean isEleAvailable = new AtomicBoolean(false);
		avoid.parallelStream().forEach(avoidItem -> {
			try {
				WebElement tagWebElement = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
				String Tags = tagWebElement.getText();
				if (Tags.toLowerCase().contains(avoidItem.toLowerCase())) {
					isEleAvailable.set(true);
				}
			} catch (Exception e) {
				System.out.print("No Such Element: " + e.getLocalizedMessage());
			}

			try {
				WebElement methodWebElement = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
				String method = methodWebElement.getText();
				if (method.toLowerCase().contains(avoidItem.toLowerCase())) {
					isEleAvailable.set(true);
				}
			} catch (Exception e) {
				System.out.print("No Such Element: " + e.getLocalizedMessage());
			}
		});
		return isEleAvailable.get();
	}
	private static void saveRecipeToDatabase(InputVO inputvo) {
        String insertSQL = "INSERT INTO LFV_recipes_toAvoid (recipe_id, recipe_name, recipe_category, food_category, ingredients, preparation_time, cooking_time, tag, no_of_servings, cuisine_category, recipe_description, preparation_method, nutrient_values, recipe_url) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, inputvo.getRecipeId());
            System.out.println("recipeId" + inputvo.getRecipeId());
            preparedStatement.setString(2, inputvo.getRecipeName());
            preparedStatement.setString(3, inputvo.getRecipeCategory());
            preparedStatement.setString(4, inputvo.getFoodCategory());
            preparedStatement.setString(5, inputvo.getNameOfIngredients());
            preparedStatement.setString(6, inputvo.getPreparationTime());
            preparedStatement.setString(7, inputvo.getCookTime());
            preparedStatement.setString(8, inputvo.getTags());
            preparedStatement.setString(9, inputvo.getNo_of_servings());
            preparedStatement.setString(10, inputvo.getCuisineCategory());
            preparedStatement.setString(11, inputvo.getRecipeDescription());
            preparedStatement.setString(12, inputvo.getPrepMethod());
            preparedStatement.setString(13, inputvo.getNutrients());
            preparedStatement.setString(14, inputvo.getRecipeUrl());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace() ;
            }
    }

	public static void main(String[] args) throws InterruptedException {
		
		setUpDriver();
		setUpDatabase();
		extractRecipe();
		tearDown();
	}

}
