package com.essensbestellung.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essensbestellung.entities.FoodOrder;
import com.essensbestellung.entities.User;
import com.essensbestellung.enums.OrderStatus;
import com.essensbestellung.repository.IFoodOrderRepository;
import com.essensbestellung.repository.IUserRepository;
import com.essensbestellung.service.IFoodOrderService;

@Service
public class FoodOrderServiceImpl implements IFoodOrderService{
	
	@Autowired
	private IFoodOrderRepository orderRepository;
	
	@Autowired
	private IUserRepository userRepository;

    @Override
	public FoodOrder getOrderbyId(Long id)
	{
		Optional<FoodOrder> optional = orderRepository.findById(id);
		
		if(optional.isEmpty())
		{
			return null;
		}
		
		return optional.get();	
	}
	
	@Override
	public List<FoodOrder> getOrdersByGroup(Long groupId) {
        return orderRepository.findOrdersByGroup(groupId);
    }
	
	@Override
	public List<FoodOrder> getOrdersByUserIdAnddate(Long userId, LocalDate orderdate) {
        return orderRepository.findByUserIdAndOrderdate(userId, orderdate);
    }
	
	@Override
	public FoodOrder saveOrder(FoodOrder order)
	{
		return orderRepository.save(order);
	}

    @Override
	public List<FoodOrder> getAllOrders()
	{
		List<FoodOrder> orderList = orderRepository.findAll();
		
		return orderList;
	}

	@Override
	public void deleteOrder(Long id)
	{
		FoodOrder order = getOrderbyId(id);
		
		if(order != null)
		{
			orderRepository.delete(order);
		}
	}

    @Override
	public void saveMultipleOrders(Map<LocalDate, List<FoodOrder>> ordersMap) {
	    for (Map.Entry<LocalDate, List<FoodOrder>> entry : ordersMap.entrySet()) {
	        LocalDate deliveryDate = entry.getKey();
	        List<FoodOrder> orders = entry.getValue();
	        for (FoodOrder order : orders) {
	            Long userId = order.getUser().getId();
	            if (userId == null) {
	                throw new RuntimeException("User ID is missing in the request");
	            }
	            User user = userRepository.findById(userId)
	                    .orElseThrow(() -> new RuntimeException("User not found for ID: " + userId));
	            
	            order.setUser(user);
	            order.setDelivery_date(deliveryDate);
	            orderRepository.save(order);
	        }
	    }
	}
	
	
	@Override
	public FoodOrder updateOrder(Long id, FoodOrder updateOrder) 
	{
        FoodOrder existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        LocalDateTime now = LocalDateTime.now();
  /*      LocalTime cutoffTime = LocalTime.of(8, 0);

        if (now.toLocalTime().isAfter(cutoffTime)) 
        {
            throw new RuntimeException("Bestellaktualisierungen können nach 08:00 Uhr nicht mehr vorgenommen werden");
        }
 */
        existingOrder.setMealtype(updateOrder.getMealtype());
		existingOrder.setWith_salad(updateOrder.isWith_salad());
        existingOrder.setStatus(OrderStatus.GEAENDERT);
        existingOrder.setLast_changed(LocalDateTime.now());

        return orderRepository.save(existingOrder);
    }
	
	@Override
	public FoodOrder updateOrder2(Long id, FoodOrder updateOrder)
	{
		FoodOrder dbOrder = getOrderbyId(id);
		
		if(dbOrder != null)
		{
			dbOrder.setMealtype(updateOrder.getMealtype());
		}
		
		return dbOrder;
	}

	@Override
    public List<FoodOrder> getFoodOrdersByUserAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return orderRepository.findFoodOrdersByUserAndDateRange(userId, startDate, endDate);
    }



}

