package com.essensbestellung.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import com.essensbestellung.entities.FoodOrder;

public interface IFoodOrderService {

	public FoodOrder getOrderbyId(Long id);
	
	public FoodOrder saveOrder(FoodOrder order);
	
	public List<FoodOrder> getAllOrders();
	
	public void deleteOrder(Long id);
	
	public List<FoodOrder> getOrdersByGroup(Long groupId);
	
	public List<FoodOrder> getOrdersByUserIdAnddate(Long userId, LocalDate orderdate);
	
	public FoodOrder updateOrder(Long id, FoodOrder updateOrder);
	
	public FoodOrder updateOrder2(Long id, FoodOrder updateOrder);

	public void saveMultipleOrders(Map<LocalDate, List<FoodOrder>> ordersMap);

	List<FoodOrder> getFoodOrdersByUserAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);

		
}
