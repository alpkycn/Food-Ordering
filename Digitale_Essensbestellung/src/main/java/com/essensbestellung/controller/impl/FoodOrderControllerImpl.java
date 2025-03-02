package com.essensbestellung.controller.impl;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essensbestellung.controller.IFoodOrderController;
import com.essensbestellung.entities.FoodOrder;
import com.essensbestellung.service.IFoodOrderService;
import com.essensbestellung.service.InvoiceService;


@RestController
@RequestMapping("/rest/api/order")
public class FoodOrderControllerImpl implements IFoodOrderController{
	 
	 @Autowired
	 private IFoodOrderService orderService;

	 @Autowired
     private InvoiceService invoiceService;


	 @GetMapping(path = "/list/{id}")
	 public FoodOrder getOrderbyId(@PathVariable(name = "id") Long id)
	 {
		 return orderService.getOrderbyId(id);
	 }
	 
	 @GetMapping("/group/{groupId}")
	    public ResponseEntity<List<FoodOrder>> getOrdersByGroup(@PathVariable Long groupId) {
	        List<FoodOrder> orders = orderService.getOrdersByGroup(groupId);
	        if (orders.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(orders);
	    }
	 
	 @PostMapping("/save-multiple")
	 public ResponseEntity<?> saveMultipleOrders(@RequestBody Map<LocalDate, List<FoodOrder>> ordersMap) {
	     try {
	         orderService.saveMultipleOrders(ordersMap);
	         return ResponseEntity.ok("Orders saved successfully");
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save orders");
	     }
	 }
	 
	 @GetMapping("/list/user/{userId}")
	    public List<FoodOrder> getOrdersByUserIdAnddate(
	            @PathVariable Long userId,
	            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate orderdate) {
	        return orderService.getOrdersByUserIdAnddate(userId, orderdate);
	    }

	 
	 @PostMapping(path = "/save")
	 public FoodOrder saveOrder(@RequestBody FoodOrder order)
	 {
		 return orderService.saveOrder(order);
	 }
	 
	 @GetMapping(path = "/list")
	 public List<FoodOrder> getAllOrders()
	 {
		 return orderService.getAllOrders();
	 }
	 
	 @DeleteMapping(path = "/delete/{id}")
	 public void deleteOrder(@PathVariable(name = "id") Long id)
	 {
		 orderService.deleteOrder(id);
	 }
	 
	 @PutMapping(path = "/update/{id}")
	 public FoodOrder updateOrder(@PathVariable(name = "id") Long id,@RequestBody FoodOrder updateOrder)
	 {
		 return orderService.updateOrder(id, updateOrder);
	 }
	 
/*	 @PutMapping(path = "/kuchenpersonal/update/{id}")
	 public FoodOrder updateOrder2(@PathVariable(name = "id") Long id,@RequestBody FoodOrder updateOrder)
	 {
		 return orderService.updateOrder(id, updateOrder);
	 }
*/

	@GetMapping("/user-invoice")
	public ResponseEntity<byte[]> generateInvoice(
        @RequestParam Long userId,
        @RequestParam String startDate,
        @RequestParam String endDate) {

    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);

    byte[] pdfBytes = invoiceService.createInvoiceForUser(userId, start, end);

    if (pdfBytes == null || pdfBytes.length == 0) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf");
    headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");

    return ResponseEntity.ok()
            .headers(headers)
            .body(pdfBytes);
	}

}
