package delivery;

public class Order {
	
	private String[] dishesNames;
	private int[] quantities;
	private String customerName;
	private Restaurant restaurant;
	private int deliveryTime;
	private int deliveryDistance;
	private int orderID;
	private boolean assignFlag=false;
	
	public boolean isAssignFlag() {
		return assignFlag;
	}

	public void setAssignFlag(boolean assignFlag) {
		this.assignFlag = assignFlag;
	}

	// constructor
	public Order(String[] dishesNames, int[] quantities, String customerName, Restaurant restaurant, int deliveryTime,
			int deliveryDistance) {
		super();
		this.dishesNames = dishesNames;
		this.quantities = quantities;
		this.customerName = customerName;
		this.restaurant = restaurant;
		this.deliveryTime = deliveryTime;
		this.deliveryDistance = deliveryDistance;
	}
	
	// getters and setters
	public String[] getDishesNames() {
		return dishesNames;
	}
	
	public void setDishesNames(String[] dishesNames) {
		this.dishesNames = dishesNames;
	}
	public int[] getQuantities() {
		return quantities;
	}
	public void setQuantities(int[] quantities) {
		this.quantities = quantities;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public int getDeliveryDistance() {
		return deliveryDistance;
	}
	public void setDeliveryDistance(int deliveryDistance) {
		this.deliveryDistance = deliveryDistance;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	
}
