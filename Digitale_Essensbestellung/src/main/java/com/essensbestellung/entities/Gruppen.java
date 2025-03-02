package com.essensbestellung.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Table(name = "Groups")
@NoArgsConstructor
@AllArgsConstructor
public class Gruppen {

    @Id
    @Column(name = "groupID")
    private Long id;

    @Column(name = "group_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "locationID", nullable = false)
    private Location location;

    @OneToOne(optional = true)
    @JoinColumn(name = "group_leaderID")
    private User groupLeader;

    /*@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupMembers> members;*/
    
    @OneToMany(mappedBy = "gruppe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<GruppenMitglieder> members;
}
