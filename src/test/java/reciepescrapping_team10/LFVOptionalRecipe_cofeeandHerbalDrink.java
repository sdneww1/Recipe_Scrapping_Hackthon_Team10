
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




public class LFVOptionalRecipe_cofeeandHerbalDrink  {

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

    public static void extractRecipe_HerbalDrinks() throws InterruptedException {
        List<String> remove_bev = Arrays.asList("sugar");

        driver.findElement(By.id("ctl00_cntrightpanel_HyperLink27")).click(); // click on more button
        driver.findElement(By.xpath("//a[@id='ctl00_cntleftpanel_ingtree_tvIngn120']/img")).click();
        
        WebElement herbal_drinks= driver.findElement(By.id("ctl00_cntleftpanel_ingtree_tvIngt147"));
        herbal_drinks.click();

        extractRecipesHerbalDrink(remove_bev, "LFVOptionalRecipe");
    }

    public static void extractRecipe_CoffeeWithoutSugar() throws InterruptedException {
        List<String> remove_bev = Arrays.asList("sugar");

        driver.findElement(By.id("ctl00_cntrightpanel_HyperLink27")).click(); // click on more button
        driver.findElement(By.xpath("//a[@id='ctl00_cntleftpanel_ingtree_tvIngn120']/img")).click();
        
        WebElement coffee = driver.findElement(By.id("ctl00_cntleftpanel_ingtree_tvIngt134")); 
        coffee.click();// Navigate to coffee section

        extractRecipesCoffeDrink(remove_bev, "CoffeeWithoutSugar");
    }

    public static void extractRecipesHerbalDrink(List<String> remove_bev, String LFVOptionalRecipe_cofeeandHerbalDrink) throws InterruptedException {
        int rowCounter = 1;

        
        
        for (int i = 1; i <= 2; i++) {
            int pageindex = i;
            driver.navigate().to("https://www.tarladalal.com/recipes-using-lemonade-lemon-drink-1008?" + "&pageindex=" + i);
            List<WebElement> recipeCardElements = driver.findElements(By.xpath("//article[@class='rcc_recipecard']"));
            List<String> recipeUrls = new ArrayList<>();
            Map<String, String> recipeIdUrls = new HashMap<>();

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
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                try {
                    if (toExclude(remove_bev)) {
                        // driver.navigate().to("//div/a[text()= 'Recipe A To Z']");
                    } else {
                        ExcelWriter writeOutput = new   ExcelWriter();
                        // Recipe id
                        try {
                            writeOutput.setCellData(LFVOptionalRecipe_cofeeandHerbalDrink, rowCounter, 0, recipeId);
                        } catch (Exception e) {
                        }

                        // Recipe Name
                        try {
                            WebElement recipeTitle = driver
                                    .findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
                            System.out.print(recipeTitle.getText());
                            writeOutput.setCellData(LFVOptionalRecipe_cofeeandHerbalDrink, rowCounter, 1, recipeTitle.getText());
                        } catch (Exception e) {
                        }
                		
						try {
						    WebElement recipeCategory = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
						    String recipeCategoryText = recipeCategory.getText().toLowerCase();

						    System.out.print("Recipe Category: " + recipeCategory.getText());

						    if (recipeCategoryText.equalsIgnoreCase("breakfast") || recipeCategoryText.contains("breakfast")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
						    } else if (recipeCategoryText.equalsIgnoreCase("lunch") || recipeCategoryText.contains("lunch")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
						    } else if (recipeCategoryText.equalsIgnoreCase("snack") || recipeCategoryText.contains("snack")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
						    } else if (recipeCategoryText.equalsIgnoreCase("dinner") || recipeCategoryText.contains("dinner")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
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
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
						    } else if (foodCategoryText.equalsIgnoreCase("Vegeterian") || foodCategoryText.contains("Vegeterian")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
						    } else if (foodCategoryText.equalsIgnoreCase("Jain") || foodCategoryText.contains("Jain")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
						    } else if (foodCategoryText.equalsIgnoreCase("Eggitarian") || foodCategoryText.contains("Eggitarian")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
						    }
						    else if (foodCategoryText.equalsIgnoreCase("Non-veg") || foodCategoryText.contains("Non-veg")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
						    }	
						} catch (Exception e) {
						    e.printStackTrace();
						}

						try {
							WebElement nameOfIngredients = driver
									.findElement(By.xpath("//div[@id= 'rcpinglist']"));
							System.out.print(nameOfIngredients.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 4,
									nameOfIngredients.getText());

						} catch (Exception e) {

						}

						try {
							WebElement preparationTime = driver
									.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
							System.out.print(preparationTime.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 5,
									preparationTime.getText());

						} catch (Exception e) {

						}

						try {
							WebElement cookTime = driver
									.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
							System.out.print(cookTime.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 6, cookTime.getText());

						} catch (Exception e) {

						}

						try {
							WebElement tags = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
							System.out.print(tags.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 7, tags.getText());

						} catch (Exception e) {

						}
						
						
						try {
							WebElement No_of_servings = driver.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblServes']"));
							System.out.print(No_of_servings.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 8, No_of_servings.getText());

						} catch (Exception e) {

						}
						
						
						
						
						
						try {
							WebElement cuisineCategory = driver.findElement(By.xpath("//a[@itemprop='recipeCuisine' ]"));
							System.out.print(cuisineCategory.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 9, cuisineCategory.getText());

						} catch (Exception e) {

						}
						
						
						
						try {
							WebElement recipeDescription = driver.findElement(By.xpath("//p[@id='recipe_description']"));
							System.out.print(recipeDescription.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 10, recipeDescription.getText());

						} catch (Exception e) {

						}
						
						

						try {
							WebElement prepMethod = driver.findElement(
									By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
							System.out.print(prepMethod.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 11,
									prepMethod.getText());

						} catch (Exception e) {

						}
						try {
							WebElement nutrients = driver
									.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
							System.out.print(nutrients.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 12,
									nutrients.getText());

						} catch (Exception e) {

						}
						try {
							System.out.print(recipeUrl);
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 13, recipeUrl);
						} catch (Exception e) {

						}

						rowCounter++;

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			

		}
	}

}
}






    public static void extractRecipesCoffeDrink(List<String> remove_bev, String LFVOptionalRecipe_cofeeandHerbalDrink) throws InterruptedException {
        int rowCounter = 1;

        
        
        for (int i = 1; i <= 2; i++) {
            int pageindex = i;
            driver.navigate().to("https://www.tarladalal.com/recipes-using-coffee-353?" + "&pageindex=" + i);
            List<WebElement> recipeCardElements = driver.findElements(By.xpath("//article[@class='rcc_recipecard']"));
            List<String> recipeUrls = new ArrayList<>();
            Map<String, String> recipeIdUrls = new HashMap<>();

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
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                try {
                    if (toExclude(remove_bev)) {
                        // driver.navigate().to("//div/a[text()= 'Recipe A To Z']");
                    } else {
                        ExcelWriter writeOutput = new  ExcelWriter();
                        // Recipe id
                        try {
                            writeOutput.setCellData(LFVOptionalRecipe_cofeeandHerbalDrink, rowCounter, 0, recipeId);
                        } catch (Exception e) {
                        }

                        // Recipe Name
                        try {
                            WebElement recipeTitle = driver
                                    .findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
                            System.out.print(recipeTitle.getText());
                            writeOutput.setCellData(LFVOptionalRecipe_cofeeandHerbalDrink, rowCounter, 1, recipeTitle.getText());
                        } catch (Exception e) {
                        }
                		
						try {
						    WebElement recipeCategory = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
						    String recipeCategoryText = recipeCategory.getText().toLowerCase();

						    System.out.print("Recipe Category: " + recipeCategory.getText());

						    if (recipeCategoryText.equalsIgnoreCase("breakfast") || recipeCategoryText.contains("breakfast")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
						    } else if (recipeCategoryText.equalsIgnoreCase("lunch") || recipeCategoryText.contains("lunch")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
						    } else if (recipeCategoryText.equalsIgnoreCase("snack") || recipeCategoryText.contains("snack")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
						    } else if (recipeCategoryText.equalsIgnoreCase("dinner") || recipeCategoryText.contains("dinner")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 2, recipeCategory.getText().replace("Tags", " "));
						    }

						} catch (Exception e) {
						    e.printStackTrace();
						}
						
//						try {
//							WebElement foodCategory = driver
//									.findElement(By.xpath("//a/span[text()= 'No Cooking Veg Indian']"));
//							System.out.print(foodCategory.getText());
//							writeOutput.setCellData("LFVRecipes_To_Avoid", rowCounter, 6,
//									foodCategory.getText());
//
//						} catch (Exception e) {
//
//						}
						
						try {
						   // WebElement foodCategory = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
							 WebElement foodCategory = driver.findElement(By.xpath("//a[@itemprop='recipeCategory']"));
							
						    
						    String foodCategoryText = foodCategory.getText().toLowerCase();

						    System.out.print("Food Category: " + foodCategory.getText());

						    if (foodCategoryText.equalsIgnoreCase("Vegan") || foodCategoryText.contains("Vegan")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
						    } else if (foodCategoryText.equalsIgnoreCase("Vegeterian") || foodCategoryText.contains("Vegeterian")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
						    } else if (foodCategoryText.equalsIgnoreCase("Jain") || foodCategoryText.contains("Jain")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
						    } else if (foodCategoryText.equalsIgnoreCase("Eggitarian") || foodCategoryText.contains("Eggitarian")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
						    }
						    else if (foodCategoryText.equalsIgnoreCase("Non-veg") || foodCategoryText.contains("Non-veg")) {
						        writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 3, foodCategory.getText().replace("Tags", " "));
						    }	
						} catch (Exception e) {
						    e.printStackTrace();
						}

						try {
							WebElement nameOfIngredients = driver
									.findElement(By.xpath("//div[@id= 'rcpinglist']"));
							System.out.print(nameOfIngredients.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 4,
									nameOfIngredients.getText());

						} catch (Exception e) {

						}

						try {
							WebElement preparationTime = driver
									.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
							System.out.print(preparationTime.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 5,
									preparationTime.getText());

						} catch (Exception e) {

						}

						try {
							WebElement cookTime = driver
									.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
							System.out.print(cookTime.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 6, cookTime.getText());

						} catch (Exception e) {

						}

						try {
							WebElement tags = driver.findElement(By.xpath("//div[@id='recipe_tags']"));
							System.out.print(tags.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 7, tags.getText());

						} catch (Exception e) {

						}
						
						
						try {
							WebElement No_of_servings = driver.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblServes']"));
							System.out.print(No_of_servings.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 8, No_of_servings.getText());

						} catch (Exception e) {

						}
						
						
						
						
						
						try {
							WebElement cuisineCategory = driver.findElement(By.xpath("//a[@itemprop='recipeCuisine' ]"));
							System.out.print(cuisineCategory.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 9, cuisineCategory.getText());

						} catch (Exception e) {

						}
						
						
						
						try {
							WebElement recipeDescription = driver.findElement(By.xpath("//p[@id='recipe_description']"));
							System.out.print(recipeDescription.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 10, recipeDescription.getText());

						} catch (Exception e) {

						}
						
						

						try {
							WebElement prepMethod = driver.findElement(
									By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
							System.out.print(prepMethod.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 11,
									prepMethod.getText());

						} catch (Exception e) {

						}
						try {
							WebElement nutrients = driver
									.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
							System.out.print(nutrients.getText());
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 12,
									nutrients.getText());

						} catch (Exception e) {

						}
						try {
							System.out.print(recipeUrl);
							writeOutput.setCellData("LFVOptionalRecipe_cofeeandHerbalDrink", rowCounter, 13, recipeUrl);
						} catch (Exception e) {

						}

						rowCounter++;

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
	}

}




    private static boolean toExclude(List<String> eliminators) {
        AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);

        eliminators.parallelStream().forEach(eliminator -> {
            try {
                WebElement ingredientWebElement = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
                String ingredients = ingredientWebElement.getText();
                if (null != ingredients && null != eliminator
                        && ingredients.toLowerCase().contains(eliminator.toLowerCase())) {
                    isEliminatorPresent.set(true);
                }
            } catch (Exception e) {
                System.out.print("No Such Element " + e.getLocalizedMessage());
            }
            try {
                WebElement methodWebElement = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
                String method = methodWebElement.getText();
                if (null != method && null != eliminator && method.toLowerCase().contains(eliminator.toLowerCase())) {
                    isEliminatorPresent.set(true);
                }
            } catch (Exception e) {
                System.out.print("No Such Element " + e.getLocalizedMessage());
            }
        });
        return isEliminatorPresent.get();
    }

    public static void main(String[] args) throws InterruptedException {
        setUpDriver();
        extractRecipe_HerbalDrinks();
        extractRecipe_CoffeeWithoutSugar();
        tearDown();
    }
}
	
