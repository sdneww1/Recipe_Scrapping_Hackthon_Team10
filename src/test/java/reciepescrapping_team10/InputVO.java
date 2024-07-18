package reciepescrapping_team10;


	


public class InputVO {
	private String recipeId;
	private String recipeName;
	private String recipeCategory;
	private String foodCategory;
	private String nameOfIngredients;
	private String preparationTime;
	private String cookTime;
	private String tags;
	private String No_of_servings;
	private String cuisineCategory;
	private String recipeDescription;
	private String prepMethod;
	private String nutrients;
	private String recipeUrl;
	
	
	public String getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getRecipeCategory() {
		return recipeCategory;
	}
	public void setRecipeCategory(String recipeCategory) {
		this.recipeCategory = recipeCategory;
	}
	public String getFoodCategory() {
		return foodCategory;
	}
	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}
	public String getNameOfIngredients() {
		return nameOfIngredients;
	}
	public void setNameOfIngredients(String nameOfIngredients) {
		this.nameOfIngredients = nameOfIngredients;
	}
	public String getPreparationTime() {
		return preparationTime;
	}
	public void setPreparationTime(String preparationTime) {
		this.preparationTime = preparationTime;
	}
	public String getCookTime() {
		return cookTime;
	}
	public void setCookTime(String cookTime) {
		this.cookTime = cookTime;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getNo_of_servings() {
		return No_of_servings;
	}
	public void setNo_of_servings(String no_of_servings) {
		No_of_servings = no_of_servings;
	}
	public String getCuisineCategory() {
		return cuisineCategory;
	}
	public void setCuisineCategory(String cuisineCategory) {
		this.cuisineCategory = cuisineCategory;
	}
	public String getRecipeDescription() {
		return recipeDescription;
	}
	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}
	public String getPrepMethod() {
		return prepMethod;
	}
	public void setPrepMethod(String prepMethod) {
		this.prepMethod = prepMethod;
	}
	public String getNutrients() {
		return nutrients;
	}
	public void setNutrients(String nutrients) {
		this.nutrients = nutrients;
	}
	public String getRecipeUrl() {
		return recipeUrl;
	}
	public void setRecipeUrl(String recipeUrl) {
		this.recipeUrl = recipeUrl;
	}
	@Override
	public String toString() {
		return "InputVO [recipeId=" + recipeId + ", recipeName=" + recipeName + ", recipeCategory=" + recipeCategory
				+ ", foodCategory=" + foodCategory + ", nameOfIngredients=" + nameOfIngredients + ", preparationTime="
				+ preparationTime + ", cookTime=" + cookTime + ", tags=" + tags + ", No_of_servings=" + No_of_servings
				+ ", cuisineCategory=" + cuisineCategory + ", recipeDescription=" + recipeDescription + ", prepMethod="
				+ prepMethod + ", nutrients=" + nutrients + ", recipeUrl=" + recipeUrl + "]";
	}

}
