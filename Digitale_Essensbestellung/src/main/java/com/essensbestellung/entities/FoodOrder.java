package com.essensbestellung.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.essensbestellung.enums.MealType;
import com.essensbestellung.enums.OrderStatus;

@Entity
@Data
@Table(name = "foodorders")
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate orderdate;

    @Enumerated(EnumType.STRING)
    private MealType mealtype;

    @Column(name = "with_salad")
    private boolean with_salad;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime last_changed;

    @Column(nullable = false)
    private LocalDate delivery_date;

    @PrePersist
    public void setDefaultValues() {
        if (this.orderdate == null) {
            this.orderdate = LocalDate.now();
        }
        if (this.status == null) {
            this.status = OrderStatus.BESTELLT; 
        }
    }
}
