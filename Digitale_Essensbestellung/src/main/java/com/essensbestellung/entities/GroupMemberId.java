package com.essensbestellung.entities;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor  // Lombok will generate a no-args constructor
@EqualsAndHashCode  // Lombok will generate equals and hashCode based on groupID and memberID
public class GroupMemberId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;  // This is used for serialization purposes

    private Long groupid;
    private Long userid;

    // Parameterized constructor
    public GroupMemberId(Long groupid, Long userid) {
        this.groupid = groupid;
        this.userid = userid;
    }
}
