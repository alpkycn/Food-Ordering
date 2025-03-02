package com.essensbestellung.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@Table(name = "Group_Members")
@NoArgsConstructor
@AllArgsConstructor

public class GruppenMitglieder {

    @EmbeddedId
    private GroupMemberId id;

    @ManyToOne
    @MapsId("groupid")
    @JoinColumn(name = "groupid", referencedColumnName = "groupid", nullable = false)
    private Gruppen gruppe;

    @ManyToOne
    @MapsId("userid")
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false)
    private User user;
}
