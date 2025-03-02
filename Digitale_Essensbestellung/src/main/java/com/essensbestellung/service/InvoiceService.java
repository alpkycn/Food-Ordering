package com.essensbestellung.service;

import com.essensbestellung.dto.UserInvoiceTable;
import com.essensbestellung.entities.FoodOrder;

import java.time.LocalDate;
import java.util.List;


public interface InvoiceService {

public List<UserInvoiceTable> generateInvoiceTable(List<FoodOrder> foodOrders);

public byte[] createInvoiceForUser(Long userId, LocalDate startDate, LocalDate endDate);


}
