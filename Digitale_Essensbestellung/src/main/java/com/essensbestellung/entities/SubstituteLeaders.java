package com.essensbestellung.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "SubstituteLeaders") // Match the table name in the database
@NoArgsConstructor
@AllArgsConstructor
public class SubstituteLeaders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "substitutionID") // Match the primary key column name
    private Long id;

    @ManyToOne
    @JoinColumn(name = "groupID", referencedColumnName = "groupID", nullable = false) // Match the foreign key column name for Gruppen
    private Gruppen group;

    @ManyToOne
    @JoinColumn(name = "substituteLeaderID", referencedColumnName = "userID", nullable = false) // Match the foreign key column name for User
    private User stellvertreter;

    @Column(name = "substitutionDate", nullable = false) // Match the substitutionDate column
    private LocalDate substitutionDate;
}
