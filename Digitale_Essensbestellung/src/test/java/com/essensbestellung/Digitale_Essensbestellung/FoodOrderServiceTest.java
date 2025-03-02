package com.essensbestellung.Digitale_Essensbestellung;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.essensbestellung.entities.FoodOrder;
import com.essensbestellung.service.impl.FoodOrderServiceImpl;
import com.essensbestellung.enums.MealType;
import com.essensbestellung.enums.OrderStatus;
import com.essensbestellung.repository.IFoodOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class FoodOrderServiceImplTest {

    @Mock
    private IFoodOrderRepository foodOrderRepository;

    @InjectMocks
    private FoodOrderServiceImpl foodOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnOrderWhenIdExists() {
        // Arrange
        Long orderId = 1L;
        FoodOrder mockOrder = new FoodOrder(orderId, null, LocalDate.now(), MealType.ROT, false, OrderStatus.BESTELLT, null, null);
        when(foodOrderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        // Act
        FoodOrder result = foodOrderService.getOrderbyId(orderId);

        // Assert
        assertNotNull(result, "Order sollte zur端ckgegeben werden.");
        assertEquals(orderId, result.getId(), "Die ID sollte 端bereinstimmen.");
    }

    @Test
    void shouldReturnNullWhenIdDoesNotExist() {
        // Arrange
        Long orderId = 99L;
        when(foodOrderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act
        FoodOrder result = foodOrderService.getOrderbyId(orderId);

        // Assert
        assertNull(result, "Wenn die Order nicht existiert, sollte null zur端ckgegeben werden.");
    }

    @Test
    void shouldSaveOrder() {
        // Arrange
        FoodOrder orderToSave = new FoodOrder(null, null, LocalDate.now(), MealType.ROT, false, OrderStatus.BESTELLT, null, null);
        FoodOrder savedOrder = new FoodOrder(1L, null, LocalDate.now(), MealType.ROT, false, OrderStatus.BESTELLT, null, null);
        when(foodOrderRepository.save(orderToSave)).thenReturn(savedOrder);

        // Act
        FoodOrder result = foodOrderService.saveOrder(orderToSave);

        // Assert
        assertNotNull(result, "Die gespeicherte Order sollte zur端ckgegeben werden.");
        assertEquals(1L, result.getId(), "Die gespeicherte Order sollte eine ID haben.");
    }

    @Test
    void shouldReturnAllOrders() {
        // Arrange
        List<FoodOrder> mockOrders = List.of(
            new FoodOrder(1L, null, LocalDate.now(), MealType.ROT, false, OrderStatus.BESTELLT, null, null),
            new FoodOrder(2L, null, LocalDate.now(), MealType.BLAU, true, OrderStatus.BESTELLT, null, null)
        );
        when(foodOrderRepository.findAll()).thenReturn(mockOrders);

        // Act
        List<FoodOrder> result = foodOrderService.getAllOrders();

        // Assert
        assertNotNull(result, "Die Liste der Orders sollte nicht null sein.");
        assertEquals(2, result.size(), "Die Liste sollte 2 Orders enthalten.");
    }

    @Test
    void shouldDeleteOrderWhenIdExists() {
        // Arrange
        Long orderId = 1L;
        FoodOrder mockOrder = new FoodOrder(orderId, null, LocalDate.now(), MealType.ROT, false, OrderStatus.BESTELLT, null, null);
        when(foodOrderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        // Act
        foodOrderService.deleteOrder(orderId);

        // Assert
        verify(foodOrderRepository, times(1)).delete(mockOrder);
    }

    @Test
    void shouldNotDeleteOrderWhenIdDoesNotExist() {
        // Arrange
        Long orderId = 99L;
        when(foodOrderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act
        foodOrderService.deleteOrder(orderId);

        // Assert
        verify(foodOrderRepository, never()).delete(any());
    }
}