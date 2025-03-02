package com.essensbestellung.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.essensbestellung.entities.FoodOrder;

public interface IFoodOrderController {

    public FoodOrder getOrderbyId(Long id);

    public FoodOrder saveOrder(FoodOrder order);

    public List<FoodOrder> getAllOrders();

    public void deleteOrder(Long id);

    public FoodOrder updateOrder(Long id,FoodOrder updateOrder);

    public ResponseEntity<byte[]> generateInvoice(Long userId, String startDate, String endDate);
	

//    public FoodOrder updateOrder2(Long id,FoodOrder updateOrder);
}