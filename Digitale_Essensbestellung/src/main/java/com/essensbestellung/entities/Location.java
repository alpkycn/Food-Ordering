package com.essensbestellung.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


@Entity
@Data
@Table(name = "Locations")
@NoArgsConstructor
@AllArgsConstructor
//@SQLInsert(sql = "INSERT INTO locations (address, location_name) VALUES (?, ?) RETURNING locationid")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationID") 
    private Long id;

    @Setter
    @Column(name = "location_name", unique= true) 
    private String locationName;

    @Column(name = "address", nullable = true) 
    private String address;

    @OneToOne
    @JoinColumn(name = "siteManagerID", referencedColumnName = "userID", nullable = true) 
    private User siteManager;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Gruppen> groups; 
}
