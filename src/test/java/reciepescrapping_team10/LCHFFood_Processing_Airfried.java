package reciepescrapping_team10;
//[id='ctl00_cntrightpanel_lblrecipeNameH2']

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import reciepescrapping_team10_utility.ExcelWriter;


public class LCHFFood_Processing_Airfried {
	public static WebDriver driver;
	private static Connection connection;
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
			String password = "Apeksha@24";
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("connection  "+connection );
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	@AfterTest
	public static void tearDown() {

		driver.close();
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void saveRecipeToDatabase(InputVO inputvo) {
        String insertSQL = "INSERT INTO allergy(recipe_id, recipe_name, recipe_category, food_category, ingredients, preparation_time, cooking_time, tag, no_of_servings, cuisine_category, recipe_description, preparation_method, nutrient_values, recipe_url) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
	public static void extractRecipe() throws InterruptedException {

		List<String> add = Arrays.asList(new String[] { "Air Fried", "air fry" });

		driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();

		Thread.sleep(2000);
		int rowCounter = 1;
		// run in a loop for all recipe in a page
		List<String> pageBeginsWithList = Arrays.asList(new String[] { "0-9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" });

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
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
						try {
							try {
								if ((isAdded(add))) {
									ExcelWriter writeOutput = new ExcelWriter();

									// Recipe id
									try {
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 0, recipeId);
									} catch (Exception e) {
										e.printStackTrace();
									}

									// Recipe Name
									try {
										WebElement recipeTitle = driver.findElement(
												By.xpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']"));
										System.out.print("Recipe Name: " + recipeTitle.getText());
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 1,
												recipeTitle.getText());
										dbInputVO.setRecipeName(recipeTitle.getText());
									} catch (Exception e) {
										e.printStackTrace();
									}

									try {
										WebElement recipeCategory = driver
												.findElement(By.xpath("//div[@id='recipe_tags']"));
										String recipeCategoryText = recipeCategory.getText().toLowerCase();

										System.out.print("Recipe Category: " + recipeCategory.getText());

										if (recipeCategoryText.equalsIgnoreCase("breakfast")
												|| recipeCategoryText.contains("breakfast")) {
											writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 2,
													recipeCategory.getText().replace("Tags", " "));
											 dbInputVO.setRecipeCategory(recipeCategoryText.replace("tags", ""));
										} else if (recipeCategoryText.equalsIgnoreCase("lunch")
												|| recipeCategoryText.contains("lunch")) {
											writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 2,
													recipeCategory.getText().replace("Tags", " "));
											 dbInputVO.setRecipeCategory(recipeCategoryText.replace("tags", ""));
										} else if (recipeCategoryText.equalsIgnoreCase("snack")
												|| recipeCategoryText.contains("snack")) {
											writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 2,
													recipeCategory.getText().replace("Tags", " "));
											 dbInputVO.setRecipeCategory(recipeCategoryText.replace("tags", ""));
										} else if (recipeCategoryText.equalsIgnoreCase("dinner")
												|| recipeCategoryText.contains("dinner")) {
											writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 2,
													recipeCategory.getText().replace("Tags", " "));
											 dbInputVO.setRecipeCategory(recipeCategoryText.replace("tags", ""));
										}

									} catch (Exception e) {
										e.printStackTrace();
									}

//					
									try {
										// WebElement foodCategory =
										// driver.findElement(By.xpath("//div[@id='recipe_tags']"));
										WebElement foodCategory = driver
												.findElement(By.xpath("//a[@itemprop='recipeCategory']"));

										String foodCategoryText = foodCategory.getText().toLowerCase();

										System.out.print("Food Category: " + foodCategory.getText());

										if (foodCategoryText.equalsIgnoreCase("Vegan")
												|| foodCategoryText.contains("Vegan")) {
											writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 3,
													foodCategory.getText().replace("Tags", " "));
											 dbInputVO.setFoodCategory(foodCategoryText.replace("Tags", ""));
										} else if (foodCategoryText.equalsIgnoreCase("Vegeterian")
												|| foodCategoryText.contains("Vegeterian")) {
											writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 3,
													foodCategory.getText().replace("Tags", " "));
											 dbInputVO.setFoodCategory(foodCategoryText.replace("Tags", ""));
										} else if (foodCategoryText.equalsIgnoreCase("Jain")
												|| foodCategoryText.contains("Jain")) {
											writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 3,
													foodCategory.getText().replace("Tags", " "));
											 dbInputVO.setFoodCategory(foodCategoryText.replace("Tags", ""));
										} else if (foodCategoryText.equalsIgnoreCase("Eggitarian")
												|| foodCategoryText.contains("Eggitarian")) {
											writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 3,
													foodCategory.getText().replace("Tags", " "));
											 dbInputVO.setFoodCategory(foodCategoryText.replace("Tags", ""));
										} else if (foodCategoryText.equalsIgnoreCase("Non-veg")
												|| foodCategoryText.contains("Non-veg")) {
											writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 3,
													foodCategory.getText().replace("Tags", " "));
											 dbInputVO.setFoodCategory(foodCategoryText.replace("Tags", ""));
										}
									} catch (Exception e) {
										e.printStackTrace();
									}

									try {
										WebElement nameOfIngredients = driver
												.findElement(By.xpath("//div[@id= 'rcpinglist']"));
										System.out.print(nameOfIngredients.getText());
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 4,
												nameOfIngredients.getText());
										dbInputVO.setNameOfIngredients(nameOfIngredients.getText());
									} catch (Exception e) {

									}

									try {
										WebElement preparationTime = driver
												.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
										System.out.print(preparationTime.getText());
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 5,
												preparationTime.getText());
										dbInputVO.setPreparationTime(preparationTime.getText());
									} catch (Exception e) {

									}

									try {
										WebElement cookTime = driver
												.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
										System.out.print(cookTime.getText());
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 6, cookTime.getText());
										dbInputVO.setCookTime(cookTime.getText());
									} catch (Exception e) {

									}

									try {
										WebElement tags = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
										System.out.print(tags.getText());
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 7,
												tags.getText().replace("Tags", " "));
										dbInputVO.setTags(tags.getText().replace("Tags", ""));
									} catch (Exception e) {

									}

									try {
										WebElement No_of_servings = driver
												.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblServes']"));
										System.out.print(No_of_servings.getText());
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 8,
												No_of_servings.getText());
										dbInputVO.setNo_of_servings(No_of_servings.getText());
									} catch (Exception e) {

									}

									try {
										WebElement cuisineCategory = driver
												.findElement(By.xpath("//a[@itemprop='recipeCuisine' ]"));
										System.out.print(cuisineCategory.getText());
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 9,
												cuisineCategory.getText());
										dbInputVO.setCuisineCategory(cuisineCategory.getText());
									} catch (Exception e) {

									}

									try {
										WebElement recipeDescription = driver
												.findElement(By.xpath("//p[@id='recipe_description']"));
										System.out.print(recipeDescription.getText());
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 10,
												recipeDescription.getText());
										dbInputVO.setRecipeDescription(recipeDescription.getText());

									} catch (Exception e) {

									}

									try {
										WebElement prepMethod = driver.findElement(
												By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
										System.out.print(prepMethod.getText());
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 11,
												prepMethod.getText());
										dbInputVO.setPrepMethod(prepMethod.getText());
									} catch (Exception e) {

									}
									try {
										WebElement nutrients = driver
												.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
										System.out.print(nutrients.getText());
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 12, nutrients.getText());
										dbInputVO.setNutrients(nutrients.getText());
									} catch (Exception e) {

									}
									try {
										System.out.print(recipeUrl);
										writeOutput.setCellData("LCHFFoodAirFried", rowCounter, 13, recipeUrl);
									} catch (Exception e) {

									}System.out.println("dbInputVO "+dbInputVO.toString());
									saveRecipeToDatabase(dbInputVO);

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
				WebElement headingWebElement = driver.findElement(By.xpath("//[id='ctl00_cntrightpanel_lblrecipeNameH2']"));
				String heading = headingWebElement.getText();
				if (heading.toLowerCase().contains(addTag.toLowerCase())) {
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
		setUpDatabase();
		extractRecipe();
		tearDown();
	}
}
