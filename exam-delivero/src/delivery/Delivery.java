package delivery;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class Delivery {
	// R1
	// categories for restaurants collections --> tree_set
	private SortedSet<String> categoriesSet = new TreeSet<>();
	// restaurants collection --> map: key = restaurant unique name, value Restaurant object
	private SortedMap<String,Restaurant> restaurantsmap = new TreeMap<>();
	// orders collection --> map: key = order unique id, value = order object
	private SortedMap<Integer,Order> ordersMap = new TreeMap<>();
	
    /**
     * adds one category to the list of categories managed by the service.
     * 
     * @param category name of the category
     * @throws DeliveryException if the category is already available.
     */
	public void addCategory (String category) throws DeliveryException {
		
		// check if the category has already been entered in the set of categories
		if (this.categoriesSet.contains(category))
			throw new DeliveryException();
		
		this.categoriesSet.add(category);
	}
	
	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		
		return this.categoriesSet.stream().collect(Collectors.toList());
	}
	
	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant (String name, String category) throws DeliveryException {
	
		// check if the category has been already defined
		if (! this.categoriesSet.contains(category))
			throw new DeliveryException();
		
		// creating the new restaurant object we're gonna add to our restaurants collection
		Restaurant r = new Restaurant(name,category);
		
		// adding the new restaurant to our collection
		this.restaurantsmap.put(name, r);
	}
	
	/**
	 * retrieves an ordered list by name of the restaurants of a given category. 
	 * It returns an empty list in there are no restaurants in the selected category 
	 * or the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {
        
		// checking if the category exists
		if (!this.categoriesSet.contains(category))
			return null;
		
		
		return this.restaurantsmap.values().stream()
				.filter(r->r.getCategory().equals(category)) //discard elements with others categories
				.map(Restaurant::getRestaurantName) // take only the names of the remaining restaurants
				.toList(); // returns a list of strings
				
	}
	
	// R2
	
	/**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
		
		// create the new dish obj
		Dish d = new Dish(name, price); 
		
		Restaurant r = this.restaurantsmap.get(restaurantName);
		// add the dish to the restaurant dishes list
		r.addDishToRestaurant(name, price);
		
	}
	
	/**
	 * returns a map associating the name of each restaurant with the 
	 * list of dish names whose price is in the provided range of price (limits included). 
	 * If the restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice  minimum price (included)
	 * @param maxPrice  maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {
        return this.restaurantsmap.values().stream()
        		.flatMap(r->r.getDishesList().stream()) // getting a stream of dish elements
        		.filter(d->d.isInRange(minPrice,maxPrice)) // discard the elements with no dishes in range price
        		.collect(Collectors.groupingBy( d->d.getRestaurant().getRestaurantName(),
        				Collectors.mapping(Dish::getDishName,Collectors.toList())));
	}
	
	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. 
	 * If the restaurant does not exist or does not sell any dishes 
	 * the method must return an empty list.
	 *  
	 * @param restaurantName   name of the restaurant
	 * @return alphabetically sorted list of dish names 
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {
        
		// check if the restaurant exists
		if (! this.restaurantsmap.containsKey(restaurantName))
			return null;
		
		Restaurant r = this.restaurantsmap.get(restaurantName);
		
		// check if the restaurant sell some dish
		if (r.getDishesList() == null) {
			return null;
		}
		
		
		return r.getDishesList().stream()
				.map(Dish::getDishName)
				.toList();
	}
	
	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the given category. 
	 * If the category is not defined or there are no dishes in the category 
	 * the method must return and empty list.
	 *  
	 * @param category     the category
	 * @return 
	 */
	public List<String> getDishesByCategory(String category) {
        
		// check if the category exists
		if (!this.categoriesSet.contains(category))
			return null;
		
		
		return this.restaurantsmap.values().stream()
				.filter(r->r.getCategory().equals(category))
				.flatMap(r->r.getDishesList().stream())
				.map(Dish::getDishName)
				.distinct()
				.collect(Collectors.toList());
				
	}
	
	//R3
	
	/**
	 * creates a delivery order. 
	 * Each order may contain more than one product with the related quantity. 
	 * The delivery time is indicated with a number in the range 8 to 23. 
	 * The delivery distance is expressed in kilometers. 
	 * Each order is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {
	    
		Restaurant resObj = this.restaurantsmap.get(restaurantName);
		
		// creating the new order object
		Order o = new Order(dishNames, quantities, customerName, resObj, deliveryTime, deliveryDistance);
		
		// get the current size of the orders collection
		int size = this.ordersMap.size();
		
		// assigning the id to the order
		o.setOrderID(size+1);
		
		// adding the new order to the orders collection 
		this.ordersMap.put(size+1, o);
		
		return size+1;
	}
	
	/**
	 * retrieves the IDs of the orders that satisfy the given constraints.
	 * Only the  first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} 
	 * whose {@code deliveryDistance} is lower or equal that {@code maxDistance}. 
	 * Once returned by the method the orders must be marked as assigned 
	 * so that they will not be considered if the method is called again. 
	 * The method returns an empty list if there are no orders (not yet assigned) 
	 * that meet the requirements.
	 * 
	 * @param deliveryTime required time of delivery 
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
        List<Order> scheduledOrders = this.ordersMap.values().stream()
        		.filter(o->o.isAssignFlag()==false)
        		.filter(o->o.getDeliveryTime()==deliveryTime)
        		.filter(o->o.getDeliveryDistance()<=maxDistance)
        		.limit(maxOrders)
        		.collect(Collectors.toList());
        
        if ( scheduledOrders != null)
        	scheduledOrders.stream().forEach(o->o.setAssignFlag(true)); // updating assigned flag to true
        
        return scheduledOrders.stream().map(Order::getOrderID).toList();
        
	}
	
	/**
	 * retrieves the number of orders that still need to be assigned
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
        return (int) this.ordersMap.values().stream()
        		.filter(o->o.isAssignFlag()==false)
        		.count();
	}
	
	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant.
	 * Ratings outside the valid range are discarded.
	 * 
	 * @param restaurantName   name of the restaurant
	 * @param rating           rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {
		if (rating < 0 || rating > 5) {
			System.out.println("Rating discarded");
			return;
		}
		
		// add the new rating
		Restaurant r = this.restaurantsmap.get(restaurantName);
		r.addRating(rating);
	}
	
	/**
	 * retrieves the ordered list of restaurant. 
	 * 
	 * The restaurant must be ordered by decreasing average rating. 
	 * The average rating of a restaurant is the sum of all rating divided by the number of ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
        return this.restaurantsmap.values().stream()
        		.filter(r->r.getRatingsList().size()>0)
        		.sorted(Comparator.comparing(Restaurant::getAverageRating).reversed())
        		.map(Restaurant::getRestaurantName)
        		.collect(Collectors.toList());
	}
	
	//R5
	/**
	 * returns a map associating each category to the number of orders placed to any restaurant in that category. 
	 * Also categories whose restaurants have not received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String,Long> ordersPerCategory() {
        return this.ordersMap.values().stream()
        		.collect(Collectors.groupingBy(o->o.getRestaurant().getCategory(),
        				Collectors.counting()));

	}
	
	/**
	 * retrieves the name of the restaurant that has received the higher average rating.
	 * 
	 * @return restaurant name
	 */
	public String bestRestaurant() {
        Optional<String> res = this.restaurantsmap.values().stream()
        		.filter(r->r.getRatingsList().size()>0)
        		.max(Comparator.comparing(r->r.getAverageRating()))
        		.map(Restaurant::getRestaurantName);
        
        if (res.isEmpty())
        	return null;
        return res.toString();
	}
}
