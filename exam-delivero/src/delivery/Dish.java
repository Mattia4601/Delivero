package delivery;

public class Dish {
	
	private String dishName;
	private float price;
	private  Restaurant restaurant;
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	// getters and setters
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	// constructor
	public Dish(String dishName, float price) {
		super();
		this.dishName = dishName;
		this.price = price;
	}
	
	// this method tells me if the dish price is between the range given by argument
	public boolean isInRange(float minPrice, float maxPrice) {
		
		if ( this.price < minPrice || this.price > maxPrice )
			return false;
			
		return true;
	}
	
}
