package com.essensbestellung.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essensbestellung.entities.FoodOrder;



@Repository
public interface IFoodOrderRepository extends JpaRepository<FoodOrder, Long>{
@Query("""
    SELECT fo 
    FROM FoodOrder fo 
    JOIN fo.user u 
    JOIN GruppenMitglieder gm ON u.id = gm.id.userid 
    JOIN Gruppen g ON gm.id.groupid = g.id
    WHERE g.id = :groupid
    ORDER BY fo.delivery_date   
    """)
    List<FoodOrder> findOrdersByGroup(@Param("groupid") Long groupid);

List<FoodOrder> findByUserIdAndOrderdate(Long userId, LocalDate orderdate);

List<FoodOrder> findByUserId(Long userId);

@Query("SELECT fo FROM FoodOrder fo " +
"WHERE fo.user.id = :userId " +
"AND fo.orderdate BETWEEN :startDate AND :endDate") 
List<FoodOrder> findFoodOrdersByUserAndDateRange(
    @Param("userId") Long userId, 
    @Param("startDate") LocalDate startDate, 
    @Param("endDate") LocalDate endDate
);


}
