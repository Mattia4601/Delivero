package delivery;

import java.util.ArrayList;
import java.util.LinkedList;

public class Restaurant {
	
	private String restaurantName;
	private String category;
	// dishes collection for a restaurant --> List
	private ArrayList<Dish> dishesList = new ArrayList<>();
	// ratings collection --> List of integer
	private LinkedList<Integer> ratingsList = new LinkedList<>();
	

	// getters and setters
	public LinkedList<Integer> getRatingsList() {
		return ratingsList;
	}
	public void setRatingsList(LinkedList<Integer> ratingsList) {
		this.ratingsList = ratingsList;
	}
	public ArrayList<Dish> getDishesList() {
		return dishesList;
	}
	public void setDishesList(ArrayList<Dish> dishesList) {
		this.dishesList = dishesList;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	// constructor
	public Restaurant(String restaurantName, String category) {
		super();
		this.restaurantName = restaurantName;
		this.category = category;
	}
	
	// this method adds a dish to the list of restaurant dishes
	public void addDishToRestaurant(String name, float price) throws DeliveryException {
		if (this.dishesList.stream().filter(d->d.getDishName().equals(name)).findAny().isPresent() ) 
			throw new DeliveryException ("Dish already present");
		
		Dish d = new Dish(name,price);
		
		this.dishesList.add(d);
	}
	
	// this method adds a rating to the collection
	public void addRating(int rating) {
		this.ratingsList.add(rating);
	}
	
	// this method returns the average rating of a restaurant
	public float getAverageRating() {
		int receivedRatings = this.ratingsList.size();
		int ratingsSum = this.ratingsList.stream()
				.mapToInt(Integer::intValue)
				.sum();
		return (float) ratingsSum/receivedRatings;
	}
}
